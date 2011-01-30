package example.spring;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ServletRelativeRedirectView implements View {

    private final String url;

    public ServletRelativeRedirectView(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String path = request.getServletPath() + url;
        RedirectView view = new RedirectView(path, true);
        view.setExposeModelAttributes(false);
        view.render(model, request, response);
    }
}
