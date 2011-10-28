package example.freemarker;

import freemarker.cache.TemplateLoader;
import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomFreemarkerServlet extends FreemarkerServlet {

    @Override
    public void init() throws ServletException {
        super.init(); // allow freemarker servlet to setup its configuration
        getConfiguration().setTemplateExceptionHandler(new HtmlExceptionHandler());
    }

    @Override
    protected TemplateLoader createTemplateLoader(String templatePath) throws IOException {
        return new HtmlTemplateLoader(super.createTemplateLoader(templatePath));
    }

    @Override
    protected boolean preTemplateProcess(HttpServletRequest request, HttpServletResponse response,
                                         Template template, TemplateModel data) {

        ((SimpleHash) data).put("base", request.getContextPath());
        return true;
    }
}
