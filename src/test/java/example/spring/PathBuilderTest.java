package example.spring;

import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PathBuilderTest {

    @Test
    public void shouldCreateGetLinkForGetHandler() throws Exception {
        String path = PathBuilder.pathTo(GetHandler.class).withVar("documentId", "new").build();
        assertThat(path, is("/new/success.go"));
    }

    @Test
    public void shouldCreateLinkWithQueryString() {
        String path = PathBuilder.pathTo(GetHandler.class)
                .withVar("documentId", "new")
                .withQueryParam("key", "val")
                .build();

        assertThat(path, is("/new/success.go?key=val"));
    }

    @Test
    public void shouldCreateRedirectToGetHandler() {
        View view = PathBuilder.pathTo(GetHandler.class).withVar("documentId", "new").redirect();
        ServletRelativeRedirectView redirect = (ServletRelativeRedirectView) view;
        assertThat(redirect.getUrl(), is("/new/success.go"));
    }

    @Test
    public void shouldCreatePostLinkToPostHandler() throws Exception {
        String path = PathBuilder.pathTo(PostHandler.class).POST().withVar("documentId", "old").build();
        assertThat(path, is("/old/error"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToCreatePostLinkForGetHandler() throws Exception {
        PathBuilder.pathTo(GetHandler.class).POST().withVar("documentId", "new").build();
    }

    @Test
    public void shouldCreateLinkToNamedMethod() throws Exception {
        String path = PathBuilder.pathTo(PostHandler.class)
                .withMethod("handlePostRequest")
                .withVar("documentId", "old")
                .build();

        assertThat(path, is("/old/error"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailCreateLinkToAnUnAnnotatedNamedMethod() throws Exception {
        PathBuilder.pathTo(PostHandler.class)
                .withMethod("test")
                .withVar("documentId", "old")
                .build();
    }

    @RequestMapping("/{documentId}/success.go")
    private class GetHandler {

        @RequestMapping(method = RequestMethod.GET)
        public String handleGetRequest() {
            throw new UnsupportedOperationException();
        }
    }

    private class PostHandler {

        @RequestMapping(value = "/{documentId}/error", method = RequestMethod.POST)
        public String handlePostRequest() {
            throw new UnsupportedOperationException();
        }

        public String test() {
            throw new UnsupportedOperationException();
        }
    }
}
