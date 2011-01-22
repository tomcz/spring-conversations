package example.repository;

import example.Conversation;
import example.ConversationRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SimpleConversationRepository implements ConversationRepository {

    private final Map<String, Conversation> map = new ConcurrentHashMap<String, Conversation>();

    @Override
    public Conversation getOrCreate(String id) {
        Conversation conversation = map.get(id);
        return (conversation != null) ? conversation : new Conversation();
    }

    @Override
    public void set(Conversation conversation) {
        map.put(conversation.getId(), conversation);
    }

    @Override
    public void remove(Conversation conversation) {
        map.remove(conversation.getId());
    }
}
