package example.spring;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Collections;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ServletRelativeRedirectViewTest {

    @Test
    public void shouldRedirectToUrlWithContextPathAndServletPath() throws Exception {
        Map<String, ?> model = Collections.emptyMap();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setContextPath("/context");
        request.setServletPath("/servlet");

        ServletRelativeRedirectView view = new ServletRelativeRedirectView("/foo");
        view.render(model, request, response);

        assertThat(response.getRedirectedUrl(), equalTo("/context/servlet/foo"));
    }
}