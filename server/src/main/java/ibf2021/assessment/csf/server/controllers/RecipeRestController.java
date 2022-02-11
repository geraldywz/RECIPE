package ibf2021.assessment.csf.server.controllers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/* Write your request hander in this file */

import java.util.Optional;

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
import jakarta.json.JsonValue;
import ibf2021.assessment.csf.server.models.Recipe;
import ibf2021.assessment.csf.server.services.RecipeService;

// import static csf.mdr.util.Constants.*;

@RestController
@RequestMapping(path = "/api/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipeRestController {

    private static final Logger logger = LoggerFactory.getLogger(RecipeRestController.class);

    @Autowired
    RecipeService recipeSvc;

    @GetMapping(value = "/{id}")
    public ResponseEntity<String> getRecipeById(@PathVariable String id) {
        Optional<Recipe> optRecipe = recipeSvc.getRecipeById(id);
        logger.info("Retrieving Recipe(id = " + id + ")");

        if (optRecipe.isPresent()) {
            Recipe recipe = optRecipe.get();
            JsonArrayBuilder ingredientBuilder = Json.createArrayBuilder();
            for (String i : recipe.getIngredients()) {
                ingredientBuilder.add(i);
            }
            logger.info("Retrieval of Recipe(" + id + ") >>>>> Success.");
            return ResponseEntity.ok().body(
                    Json.createObjectBuilder()
                            .add("title", recipe.getTitle())
                            .add("id", recipe.getId())
                            .add("image", recipe.getImage())
                            .add("instruction", recipe.getInstruction())
                            .add("ingredients", ingredientBuilder.build().toString())
                            .build()
                            .toString());
        } else {
            logger.info("Retrieval of Recipe(" + id + ") >>>>> Failure.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Json.createObjectBuilder()
                            .add("error", "Recipe does not exist.")
                            .build()
                            .toString());
        }
    }

    public ResponseEntity<String> saveRecipe(@RequestBody Recipe recipe) {

        String id = recipe.getId();
        logger.info("ID from POST(JSON) >>>>> %s".formatted(id));

        recipeSvc.addRecipe(recipe);
        logger.info(recipe.toString());

        final JsonObject resp = Json.createObjectBuilder()
                .add("message", "inserted to Java Map")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(resp.toString());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addRecipe(@RequestBody String newRecipe) {
        logger.info("New Recipe received, commencing ingest");
        try (InputStream is = new ByteArrayInputStream(newRecipe.getBytes())) {

            JsonObject data = Json.createReader(is).readObject();
            Recipe recipe = new Recipe();

            JsonArray ingredients = Json.createReader(
                    new ByteArrayInputStream(
                            data.getString("ingredients")
                                    .getBytes()))
                    .readArray();

            for (JsonValue ingredient : ingredients) {
                recipe.addIngredient(
                        ingredient.toString()
                                .replaceAll("^\"|\"$", ""));
            }

            recipe.setTitle(data.getString("title"));
            recipe.setImage(data.getString("image"));
            recipe.setInstruction(data.getString("instruction"));

            recipeSvc.addRecipe(recipe);
            logger.info("New recipe successfully added.");
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Json.createObjectBuilder()
                            .add("message", "created!")
                            .build()
                            .toString());
        } catch (Exception e) {
            logger.info("Adding new recipe unsuccessful.");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Json.createObjectBuilder()
                    .add("error", e.toString())
                    .build()
                    .toString());
        }
    }
}