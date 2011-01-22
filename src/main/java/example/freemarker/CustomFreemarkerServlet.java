package example.freemarker;

import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomFreemarkerServlet extends FreemarkerServlet {

    @Override
    protected boolean preTemplateProcess(HttpServletRequest request, HttpServletResponse response,
                                         Template template, TemplateModel data) {

        ((SimpleHash) data).put("base", request.getContextPath());
        return true;
    }
}
