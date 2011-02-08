package example.spring.oktabs.nosession;

import example.Conversation;
import example.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.SessionAttributeStore;
import org.springframework.web.context.request.WebRequest;

@Component
public class ConversationRepoStore implements SessionAttributeStore {

    private final ConversationRepository repository;

    @Autowired
    public ConversationRepoStore(ConversationRepository repository) {
        this.repository = repository;
    }

    public void storeAttribute(WebRequest request, String name, Object value) {
        repository.set((Conversation) value);
    }

    public Object retrieveAttribute(WebRequest request, String name) {
        return repository.get(request.getParameter(Conversation.CONVERSATION_ID));
    }

    public void cleanupAttribute(WebRequest request, String name) {
        repository.remove(request.getParameter(Conversation.CONVERSATION_ID));
    }
}
