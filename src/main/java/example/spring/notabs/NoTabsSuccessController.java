package example.spring.notabs;

import example.DomainObject;
import example.DomainObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.Map;

import static example.spring.PathBuilder.pathTo;

@Controller
@RequestMapping("/success/{id}")
public class NoTabsSuccessController {

    private final DomainObjectRepository repository;

    @Autowired
    public NoTabsSuccessController(DomainObjectRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView display(@PathVariable String id) {

        DomainObject object = repository.get(id);

        ModelAndView mv = new ModelAndView("success");

        mv.addObject("formLinks", formLinks());

        mv.addObject("object", object);

        return mv;
    }

    public Map<String, String> formLinks() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("New Form", pathTo(NoTabsFormController.class).build());
        return map;
    }
}
