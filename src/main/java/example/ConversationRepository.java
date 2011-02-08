package example;

public interface ConversationRepository {

    void set(Conversation conversation);

    Conversation get(String id);

    Conversation getOrCreate(String id);

    void remove(Conversation conversation);

    void remove(String id);
}
