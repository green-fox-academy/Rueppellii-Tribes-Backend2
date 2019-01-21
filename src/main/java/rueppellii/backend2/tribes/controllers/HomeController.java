package rueppellii.backend2.tribes.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String helloWorld(@RequestParam(required = false) String name) {
        if (name == null) {
            return "Jó munkát! :)";
        }
        return "Hello " + name;
    }
}
