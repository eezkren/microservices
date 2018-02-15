package eu.dreamix.ms.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

public class CustomHttpServletRequest extends HttpServletRequestWrapper {
    private Map<String, String[]> additionalParams;
    private HttpServletRequest request;

    public CustomHttpServletRequest(HttpServletRequest request, Map<String, String[]> additionalParams) {
        super(request);
        this.request = request;
        this.additionalParams = additionalParams;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        final Map<String, String[]> map = request.getParameterMap();
        final Map<String, String[]> param = new HashMap<String, String[]>();
        param.putAll(map);
        param.putAll(additionalParams);
        return param;
    }

}
