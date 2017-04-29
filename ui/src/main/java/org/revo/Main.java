package org.revo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ashraf on 29/04/17.
 */
@Controller
public class Main {
    private final String p = "{path:[^.]*}";

    @GetMapping(value = {p, p + "/" + p, p + "/" + p + "/" + p})
    public ModelAndView index() {
        return new ModelAndView("forward:/index.html");
    }
}
