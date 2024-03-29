package org.revo.Controller;

import org.revo.Domain.User;
import org.revo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by ashraf on 10/04/17.
 */
@Controller
@SessionAttributes(types = AuthorizationRequest.class)
public class MainController {
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private UserService userService;

    @RequestMapping("/oauth/confirm_access")
    public ModelAndView getAccessConfirmation(@ModelAttribute AuthorizationRequest clientAuth,Principal user) {
        return new ModelAndView("access_confirmation").addObject("auth_request", clientAuth)
                .addObject("client", clientDetailsService.loadClientByClientId(clientAuth.getClientId()));
    }

    @GetMapping("signup")
    public String modelAndView(User user) {
        return "signup";
    }

    @PostMapping("signup")
    public String modelAndView(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "signup";
        userService.encodeThenSave(user);
        return "redirect:/done?message=please+check+you+email+to+activate+your+account";
    }

    @GetMapping("activate/{id}")
    public String modelAndView(@PathVariable Long id) {
        userService.activate(id);
        return "redirect:/done?message=successfully+activate+your+account+you+are+welcome";
    }

    @ResponseBody
    @GetMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}
