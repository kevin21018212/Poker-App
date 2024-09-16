package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@RestController
class WelcomeController {

    private final String externalApiBaseUrl = "https://pokeapi.co/api/v2/pokemon/";

    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }

    @GetMapping("/{pokemonName}")
    public String welcome(@PathVariable String pokemonName) {
        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Build the URL for the specific Pokémon using the provided name
        String apiUrl = externalApiBaseUrl + pokemonName;

        // Make an HTTP GET request to the PokeAPI
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            // If the request is successful, return the response from the PokeAPI
            String apiResponse = response.getBody();
            return "Hello and welcome to COMS 309!\n" + "Pokémon Data for " + pokemonName + ":\n" + apiResponse;
        } else {
            // If the request to the PokeAPI fails, return an error message
            return "Hello and welcome to COMS 309!\n" + "Failed to fetch Pokémon data for " + pokemonName;
        }
    }
}
