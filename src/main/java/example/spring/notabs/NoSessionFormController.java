package example.spring.notabs;

import example.Conversation;
import example.DomainObject;
import example.DomainObjectRepository;
import example.spring.success.SuccessController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import static example.spring.PathBuilder.pathTo;

@Controller
@RequestMapping("/nosession")
public class NoSessionFormController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final DomainObjectRepository repository;

    @Autowired
    public NoSessionFormController(DomainObjectRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView display(Conversation conversation) {
        log.info("Displaying " + conversation);

        ModelAndView mv = new ModelAndView("form");

        mv.addObject("title", "OLD SCHOOL");

        mv.addObject("formAction", pathTo(getClass()).POST().build());

        mv.addAllObjects(conversation.createModel("conversation"));

        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Object process(Conversation conversation) {

        if (conversation.isCancelled()) {
            log.info("Cancelled " + conversation);
            return new RedirectView("/", true);
        }

        log.info("Processing " + conversation);
        if (conversation.validate()) {

            DomainObject object = conversation.createDomainObject();
            repository.set(object);

            return pathTo(SuccessController.class)
                    .withVar("id", object.getId())
                    .redirect();
        }

        return display(conversation);
    }
}
