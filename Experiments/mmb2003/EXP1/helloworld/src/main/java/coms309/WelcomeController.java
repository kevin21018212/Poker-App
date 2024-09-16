package coms309;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name, Model model) {
        model.addAttribute("name", name);
        return "welcome";
    }
}
