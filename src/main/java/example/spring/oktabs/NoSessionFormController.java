package example.spring.oktabs;

import example.Conversation;
import example.ConversationRepository;
import example.DomainObject;
import example.DomainObjectRepository;
import example.spring.PathBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.ServletRequest;

import static example.spring.PathBuilder.pathTo;

@Controller
@RequestMapping("/alternate/form/{id}")
public class NoSessionFormController {

    private final ConversationRepository conversationRepository;
    private final DomainObjectRepository domainRepository;

    @Autowired
    public NoSessionFormController(ConversationRepository conversationRepository,
                                   DomainObjectRepository domainRepository) {

        this.conversationRepository = conversationRepository;
        this.domainRepository = domainRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView display(@PathVariable String id) {

        Conversation conversation = conversationRepository.getOrCreate(id);

        ModelAndView mv = new ModelAndView("form");
        mv.addObject("title", "OK TABS (no session)");
        mv.addObject("formAction", pathToSelf(conversation).build());
        mv.addAllObjects(conversation.createModel("conversation"));

        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public View process(@PathVariable String id, WebRequest request) {

        Conversation conversation = conversationRepository.getOrCreate(id);

        update(conversation, request);

        if (conversation.validate()) {

            DomainObject object = conversation.createDomainObject();
            domainRepository.set(object);

            conversationRepository.remove(conversation);

            return pathTo(OkTabsSuccessController.class).withVar("id", object.getId()).redirect();
        }

        conversationRepository.set(conversation);
        return pathToSelf(conversation).redirect();
    }

    private PathBuilder pathToSelf(Conversation conversation) {
        return pathTo(getClass()).withVar("id", conversation.getId());
    }

    private void update(Conversation conversation, WebRequest request) {
        DataBinder binder = new ServletRequestDataBinder(conversation);
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        ServletRequest servletRequest = (ServletRequest) request.resolveReference(WebRequest.REFERENCE_REQUEST);
        binder.bind(new ServletRequestParameterPropertyValues(servletRequest));
    }
}
