package eu.dreamix.ms.response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
public class CustomPostZuulFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${oauth.refresh-token.max-age}")
    private int refreshTokenMaxAge;

    @Value("${oauth.access-token.max-age}")
    private int accessTokenMaxAge;

    @Value("${oauth.token-path}")
    private String tokenPath;

    @Value("${oauth.refresh-token.cookie-name}")
    private String refreshTokenCookieName;

    @Value("${oauth.access-token.cookie-name}")
    private String accessTokenCookieName;

    @Override
    public Object run() {
        final RequestContext ctx = RequestContext.getCurrentContext();
        logger.info("in zuul filter " + ctx.getRequest().getRequestURI());

        try {
            final InputStream is = ctx.getResponseDataStream();
            String responseBody = IOUtils.toString(is, "UTF-8");
            if (responseBody.contains(refreshTokenCookieName) && responseBody.contains(accessTokenCookieName)) {
                final Map<String, Object> responseMap = mapper.readValue(responseBody,
                        new TypeReference<Map<String, Object>>() {
                        });
                final String refreshToken = responseMap.get(refreshTokenCookieName).toString();
                final String accessToken = responseMap.get(accessTokenCookieName).toString();

                final Cookie refreshTokenCookie = new Cookie(refreshTokenCookieName, refreshToken);
                refreshTokenCookie.setPath(ctx.getRequest().getContextPath() + tokenPath);
                refreshTokenCookie.setMaxAge(refreshTokenMaxAge);
                ctx.getResponse().addCookie(refreshTokenCookie);
                logger.info("refresh token = " + refreshToken);

                final Cookie accessTokenCookie = new Cookie(accessTokenCookieName, accessToken);
                accessTokenCookie.setPath(ctx.getRequest().getContextPath() + "/");
                accessTokenCookie.setMaxAge(accessTokenMaxAge);
                ctx.getResponse().addCookie(accessTokenCookie);
                logger.info("access token = " + accessToken);

            }

            ctx.setResponseBody(responseBody);

        } catch (final IOException e) {
            logger.error("Error occured in zuul post filter", e);
        }
        return null;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public String filterType() {
        return "post";
    }

}
