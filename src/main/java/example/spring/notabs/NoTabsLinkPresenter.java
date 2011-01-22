package example.spring.notabs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import static example.spring.PathBuilder.pathTo;

@Controller
public class NoTabsLinkPresenter {

    @RequestMapping(value = "/link", method = RequestMethod.GET)
    public ModelAndView display() {
        ModelAndView mv = new ModelAndView("link");
        mv.addObject("formLink", pathTo(NoTabsFormController.class).build());
        return mv;
    }
}
