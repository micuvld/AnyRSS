package base.website.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class MainController {
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView dashboard() {
        return new ModelAndView("index");
    }
}
