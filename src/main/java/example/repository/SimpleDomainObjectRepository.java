package example.repository;

import example.DomainObject;
import example.DomainObjectRepository;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SimpleDomainObjectRepository implements DomainObjectRepository {

    private final Map<String, DomainObject> map = new ConcurrentHashMap<String, DomainObject>();

    @Override
    public void set(DomainObject obj) {
        map.put(obj.getId(), obj);
    }

    @Override
    public DomainObject get(String id) {
        DomainObject obj = map.get(id);
        Validate.isTrue(obj != null, "Cannot find DomainObject for ID: ", id);
        return obj;
    }
}
