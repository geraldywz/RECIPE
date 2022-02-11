package ibf2021.assessment.csf.server.controllers;

import java.util.List;

/* Write your request hander in this file */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import ibf2021.assessment.csf.server.models.Recipe;
import ibf2021.assessment.csf.server.services.RecipeService;

// import static csf.mdr.util.Constants.*;

@RestController
@RequestMapping(path = "/api/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipesRestController {

    @Autowired
    RecipeService recipeSvc;

    // private static final Logger logger =
    // LoggerFactory.getLogger(WeatherController.class);

    // @GetMapping
    // public ResponseEntity<String> getBookByRequestParam(String title) {
    // return getBook(title);
    // }

    // @GetMapping(value = "/{title}")
    // public ResponseEntity<String> getBookByPathVariable(@PathVariable String
    // title) {
    // return getBook(title);
    // }

    @GetMapping
    public ResponseEntity<String> getRecipes() {
        List<Recipe> recipes = recipeSvc.getAllRecipes();
        JsonArrayBuilder recipeArray = Json.createArrayBuilder();
        for (Recipe recipe : recipes) {
            JsonObjectBuilder recipeBuilder = Json.createObjectBuilder();
            recipeBuilder.add("id", recipe.getId());
            recipeBuilder.add("title", recipe.getTitle());
            recipeArray.add(recipeBuilder.build());
        }

        return ResponseEntity
                .ok()
                .body(recipeArray.build().toString());
    }

    // private ResponseEntity<String> getBook(String title) {
    // if (title.equals(null) || title.length() == 0) {
    // return ResponseEntity
    // .status(HttpStatus.BAD_REQUEST)
    // .body(
    // Json.createObjectBuilder()
    // .add("Error", "Title Required.")
    // .build()
    // .toString());
    // } else {
    // return ResponseEntity
    // .ok()
    // .body(recipeSvc.getAllRecipes().toString());
    // }
    // }

    // @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    // public void saveTodo(@RequestBody Recipe recipe) {
    // recipeSvc.addRecipe(recipe);
    // }

}