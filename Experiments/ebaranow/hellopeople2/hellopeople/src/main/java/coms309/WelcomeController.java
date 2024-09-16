package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple Hello World Controller to display the string returned
 *
 * @author Vivek Bengre
 */

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }

    //mimicing an actual controller, with words but could easily swithch with movement with the creation of classes
    @GetMapping("w")
    public String jump() { return "Jump!"}

    @GetMapping("a")
    public String jump() { return "Move Left"}
    @GetMapping("s")
    public String jump() { return "Duck!"}

    @GetMapping("d")
    public String jump() { return "Move Right"}
}
