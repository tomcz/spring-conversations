package example;

public interface DomainObjectRepository {

    void set(DomainObject obj);

    DomainObject get(String id);
}
