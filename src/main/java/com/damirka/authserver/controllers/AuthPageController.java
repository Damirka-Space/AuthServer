package com.damirka.authserver.controllers;

import com.damirka.authserver.dtos.UserRegistrationDto;
import com.damirka.authserver.exceptions.user.UserException;
import com.damirka.authserver.exceptions.user.UserExceptionId;
import com.damirka.authserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.text.ParseException;
import java.util.Objects;

@Controller()
public class AuthPageController {

    private UserService userService;

    @Autowired
    public AuthPageController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String loginPage(@RequestParam(name = "error", required = false) String error, Model model) {
        if(Objects.nonNull(error))
            model.addAttribute("param.error", error);
        return "login/login";
    }

    @GetMapping("/logout")
    @ResponseBody
    public String logout(Principal principal) {
        return principal.toString();
    }

    @PostMapping("/logout")
    public String logoutPost() {
        return "login/login";
    }


    @GetMapping("/registration")
    public String registationPage(@RequestParam(name = "error", required = false) Integer error, Model model) {
        if(Objects.nonNull(error)) {
            if(error >= 0 && error < UserExceptionId.values().length)
                model.addAttribute("message", UserExceptionId.values()[error].toString());
            else
                model.addAttribute("message", "Unknown error");
        }
        return "login/registration";
    }

    @PostMapping("/registration")
    public String registerUser(UserRegistrationDto user) {
        try {
            userService.registerUser(user);
        } catch (UserException e) {
            return "redirect:/registration?error=" + e.getId();
        } catch (ParseException pe) {
            // TODO: redirect to 500 page
        }

        return "login/login";
    }


}
