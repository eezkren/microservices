package eu.dreamix.ms.request;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Component
public class RelayTokenFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${oauth.token-path}")
    private String tokenPath;

    @Value("${oauth.refresh-token.cookie-name}")
    private String refreshTokenCookieName;

    @Value("${oauth.access-token.cookie-name}")
    private String accessTokenCookieName;

    @Value("${oauth.client-secret}")
    private String clientSecret;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return -2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        final RequestContext ctx = RequestContext.getCurrentContext();

        logger.info("in zuul filter " + ctx.getRequest().getRequestURI());
        if (ctx.getRequest().getRequestURI().equals(tokenPath)) {

            byte[] encoded;

            try {
                encoded = Base64.encode(clientSecret.getBytes("UTF-8"));
                ctx.addZuulRequestHeader("Authorization", "Basic " + new String(encoded));

                final HttpServletRequest req = ctx.getRequest();
                String grantType = ctx.getRequest().getParameter("grant_type");


                if ("refresh_token".equals(grantType)) {
                    logger.info("getting refresh_token");

                    final String refreshToken = extractRefreshToken(req);
                    final Map<String, String[]> param = new HashMap<String, String[]>();
                    param.put("refresh_token", new String[]{refreshToken});

                    ctx.setRequest(new CustomHttpServletRequest(req, param));
                }
                if ("password".equals(grantType)) {
                    logger.info("getting password");
                }

            } catch (UnsupportedEncodingException e1) {
                logger.error("Error occured in pre filter", e1);
            }

        } else {

            logger.info("getting " + ctx.getRequest().getRequestURI());
            final HttpServletRequest req = ctx.getRequest();
            final String accessToken = extractAccessToken(req);

            if (accessToken != null) {
                ctx.addZuulRequestHeader("Authorization", "Bearer " + new String(accessToken));
            }

        }
        return null;

    }

    private String extractRefreshToken(HttpServletRequest req) {
        final Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equalsIgnoreCase(refreshTokenCookieName)) {
                    return cookies[i].getValue();
                }
            }
        }
        return null;
    }

    private String extractAccessToken(HttpServletRequest req) {
        final Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equalsIgnoreCase(accessTokenCookieName)) {
                    return cookies[i].getValue();
                }
            }
        }
        return null;
    }

}
