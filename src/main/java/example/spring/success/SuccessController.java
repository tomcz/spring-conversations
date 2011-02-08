package example.spring.success;

import example.DomainObject;
import example.DomainObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/success/{id}")
public class SuccessController {

    private final DomainObjectRepository repository;

    @Autowired
    public SuccessController(DomainObjectRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView display(@PathVariable String id) {

        DomainObject object = repository.get(id);

        ModelAndView mv = new ModelAndView("success");

        mv.addObject("object", object);

        return mv;
    }
}
