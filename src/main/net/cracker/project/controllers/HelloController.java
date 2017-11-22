package main.net.cracker.project.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Артём on 20.11.2017.
 */
@Controller
@Secured(value = {"ROLE_ADMIN"})
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String hello() {
        return "hello";
    }
}
