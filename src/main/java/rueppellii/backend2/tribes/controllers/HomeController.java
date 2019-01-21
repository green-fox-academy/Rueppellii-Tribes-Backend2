package rueppellii.backend2.tribes.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @GetMapping("/")
  public String helloWorld() {
    return "Hello";
  }
}
