package ibf2021.assessment.csf.server.controllers;
/* Write your request hander in this file */

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import ibf2021.assessment.csf.server.models.Recipe;
import ibf2021.assessment.csf.server.services.RecipeService;

@RestController
@RequestMapping(path = "/api/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipesRestController {

    private static final Logger logger = LoggerFactory.getLogger(RecipesRestController.class);

    @Autowired
    RecipeService recipeSvc;

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
        logger.info("Retrieved all recipes: " + recipes.size());
        return ResponseEntity
                .ok()
                .body(recipeArray.build().toString());
    }

}