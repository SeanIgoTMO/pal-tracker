package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    private final String welcome_message;

    public WelcomeController(
        @Value("${WELCOME_MESSAGE:NOT SET}")
        String message
    ) {
        welcome_message = message;
    }

    @GetMapping("/")
    public String sayHello() {
        return welcome_message;
    }
}
