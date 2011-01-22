package example;

public interface ConversationRepository {

    Conversation getOrCreate(String id);

    void set(Conversation conversation);

    void remove(Conversation conversation);
}
