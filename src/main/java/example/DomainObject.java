package example;

import java.util.UUID;

public class DomainObject {

    private final String id = UUID.randomUUID().toString();

    private String email;

    public DomainObject(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
