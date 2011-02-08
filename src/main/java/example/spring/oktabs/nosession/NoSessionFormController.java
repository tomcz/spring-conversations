package example.spring.oktabs.nosession;

import example.DomainObjectRepository;
import example.spring.oktabs.OkTabsFormController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class NoSessionFormController extends OkTabsFormController {

    @Autowired
    public NoSessionFormController(DomainObjectRepository repository) {
        super(repository, "OK TABS (without session)");
    }
}
