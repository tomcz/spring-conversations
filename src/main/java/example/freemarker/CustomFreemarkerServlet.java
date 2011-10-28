package example.freemarker;

import freemarker.cache.TemplateLoader;
import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomFreemarkerServlet extends FreemarkerServlet {

    @Override
    protected boolean preTemplateProcess(HttpServletRequest request, HttpServletResponse response,
                                         Template template, TemplateModel data) {

        ((SimpleHash) data).put("base", request.getContextPath());
        return true;
    }

    @Override
    protected Configuration createConfiguration() {
        Configuration configuration = super.createConfiguration();
        configuration.setTemplateExceptionHandler(new HtmlExceptionHandler());
        return configuration;
    }

    @Override
    protected TemplateLoader createTemplateLoader(String templatePath) throws IOException {
        return new HtmlTemplateLoader(super.createTemplateLoader(templatePath));
    }
}
