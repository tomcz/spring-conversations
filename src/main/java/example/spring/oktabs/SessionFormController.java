package example.spring.oktabs;

import example.Conversation;
import example.DomainObject;
import example.DomainObjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import static example.spring.PathBuilder.pathTo;

@Controller
@RequestMapping("/form")
@SessionAttributes("oktabs")
public class SessionFormController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final DomainObjectRepository repository;

    @Autowired
    public SessionFormController(DomainObjectRepository repository) {
        this.repository = repository;
    }

    @ModelAttribute("oktabs")
    public Conversation getConversation() {
        Conversation conversation = new Conversation();
        log.info("Created " + conversation);
        return conversation;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView display(@ModelAttribute("oktabs") Conversation conversation) {
        log.info("Displaying " + conversation);

        ModelAndView mv = new ModelAndView("form");

        mv.addObject("title", "OK TABS (with session)");

        mv.addObject("formAction", pathTo(getClass()).POST().build());

        mv.addAllObjects(conversation.createModel("conversation"));

        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Object process(@ModelAttribute("oktabs") Conversation conversation, SessionStatus status) {
        log.info("Processed " + conversation);

        if (conversation.validate()) {

            DomainObject object = conversation.createDomainObject();
            repository.set(object);

            status.setComplete();

            return pathTo(OkTabsSuccessController.class).withVar("id", object.getId()).redirect();
        }

        return new ModelAndView(pathTo(getClass()).redirect(), Conversation.CONVERSATION_ID, conversation.getId());
    }
}
