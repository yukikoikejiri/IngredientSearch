package IngredientSearch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class IngredientLoader {
    public List<Ingredient> loadIngredients() {
        Gson gson = new Gson();
        try (Reader reader = new InputStreamReader(
                getClass().getResourceAsStream("/ingredients.json"), "UTF-8")) {
            List<Ingredient> ingredients = gson.fromJson(reader, new TypeToken<List<Ingredient>>(){}.getType());
            System.out.println("Loaded " + ingredients.size() + " ingredients.");
            return ingredients;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
