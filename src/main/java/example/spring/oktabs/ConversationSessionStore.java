package example.spring.oktabs;

import example.Conversation;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.SessionAttributeStore;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

@Component
public class ConversationSessionStore implements SessionAttributeStore {

    public void storeAttribute(WebRequest request, String name, Object value) {
        if (value instanceof Conversation) {
            Conversation conversation = (Conversation) value;
            String attributeKey = attributeKey(name, conversation.getId());
            request.setAttribute(attributeKey, conversation, RequestAttributes.SCOPE_SESSION);
        } else {
            request.setAttribute(name, value, RequestAttributes.SCOPE_SESSION);
        }
    }

    public Object retrieveAttribute(WebRequest request, String name) {
        String conversationId = request.getParameter(Conversation.CONVERSATION_ID);
        if (StringUtils.isNotEmpty(conversationId)) {
            String attributeKey = attributeKey(name, conversationId);
            return request.getAttribute(attributeKey, RequestAttributes.SCOPE_SESSION);
        } else {
            return request.getAttribute(name, RequestAttributes.SCOPE_SESSION);
        }
    }

    public void cleanupAttribute(WebRequest request, String name) {
        String conversationId = request.getParameter(Conversation.CONVERSATION_ID);
        if (StringUtils.isNotEmpty(conversationId)) {
            String attributeKey = attributeKey(name, conversationId);
            request.removeAttribute(attributeKey, RequestAttributes.SCOPE_SESSION);
        } else {
            request.removeAttribute(name, RequestAttributes.SCOPE_SESSION);
        }
    }

    private String attributeKey(String name, String id) {
        return name + "-" + id;
    }
}
