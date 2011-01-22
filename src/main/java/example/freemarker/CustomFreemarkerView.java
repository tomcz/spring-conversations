package example.freemarker;

import freemarker.template.SimpleHash;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class CustomFreemarkerView extends FreeMarkerView {

    @Override
    protected SimpleHash buildTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        SimpleHash hash = super.buildTemplateModel(model, request, response);
        hash.put("servletPath", request.getServletPath());
        hash.put("contextPath", request.getContextPath());
        return hash;
    }
}
