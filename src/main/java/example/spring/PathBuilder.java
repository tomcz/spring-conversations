package example.spring;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.util.UriTemplate;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class PathBuilder {

    private Class handler;
    private String methodName;
    private RequestMethod method;
    private MultiValueMap<String, String> queryParams;
    private Map<String, String> pathVariables = new HashMap<String, String>();

    private PathBuilder(Class handler) {
        this.method = RequestMethod.GET;
        this.handler = handler;
    }

    public static PathBuilder pathTo(Class handler) {
        return new PathBuilder(handler);
    }

    public PathBuilder POST() {
        return withMethod(RequestMethod.POST);
    }

    public PathBuilder withMethod(RequestMethod method) {
        this.method = method;
        return this;
    }

    public PathBuilder withMethod(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public PathBuilder withVar(String key, Object value) {
        pathVariables.put(key, ObjectUtils.toString(value));
        return this;
    }

    public PathBuilder withQueryParam(String key, Object value) {
        if (queryParams == null) {
            queryParams = new LinkedMultiValueMap<String, String>();
        }
        queryParams.add(key, ObjectUtils.toString(value));
        return this;
    }

    public String build() {
        String path = expandPathVariables(findHandlerClassMapping() + findHandlerMethodMapping());
        if (queryParams != null) {
            StringBuilder buf = new StringBuilder(path).append('?');
            appendQueryParams(buf);
            return buf.toString();
        }
        return path;
    }

    public View redirect() {
        return new ServletRelativeRedirectView(build());
    }

    private String findHandlerClassMapping() {
        RequestMapping mapping = AnnotationUtils.findAnnotation(handler, RequestMapping.class);
        return (mapping != null) ? getFirstPath(mapping) : "";
    }

    private String findHandlerMethodMapping() {
        return (methodName != null) ? findMappingForMethodName() : findMappingForRequestMethod();
    }

    private String findMappingForMethodName() {
        for (Method classMethod : handler.getMethods()) {
            if (classMethod.getName().equals(methodName)) {
                RequestMapping mapping = AnnotationUtils.findAnnotation(classMethod, RequestMapping.class);
                if (mapping != null) {
                    return getFirstPath(mapping);
                }
            }
        }
        throw new IllegalArgumentException(handler.getName() +
                " does not contain an annotated method named '" + methodName + "'");
    }

    private String findMappingForRequestMethod() {
        for (Method classMethod : handler.getMethods()) {
            RequestMapping mapping = AnnotationUtils.findAnnotation(classMethod, RequestMapping.class);
            if (mapping != null && ArrayUtils.contains(mapping.method(), method)) {
                return getFirstPath(mapping);
            }
        }
        throw new IllegalArgumentException(handler.getName() + " cannot handle " + method + " requests");
    }

    private String getFirstPath(RequestMapping mapping) {
        String[] paths = mapping.value();
        return (paths.length > 0) ? paths[0] : "";
    }

    private String expandPathVariables(String url) {
        UriTemplate template = new UriTemplate(url);
        return template.expand(pathVariables).toString();
    }

    private void appendQueryParams(StringBuilder buf) {
        boolean first = true;
        for (String key : queryParams.keySet()) {
            for (String value : queryParams.get(key)) {
                if (first) {
                    first = false;
                } else {
                    buf.append('&');
                }
                buf.append(encode(key));
                buf.append('=');
                buf.append(encode(value));
            }
        }
    }

    private Object encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
