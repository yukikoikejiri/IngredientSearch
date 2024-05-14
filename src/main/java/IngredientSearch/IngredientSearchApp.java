package IngredientSearch;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class IngredientSearchApp extends Application {
    private List<Ingredient> ingredients;

    @Override
    public void start(Stage stage) {
        ingredients = new IngredientLoader().loadIngredients();
        if (ingredients == null || ingredients.isEmpty()) {
            System.out.println("No ingredients loaded.");
        } else {
            System.out.println("Ingredients loaded successfully.");
        }

        Label titleLabel = new Label("Cosmetics Ingredient Search");
        styleTitleLabel(titleLabel);

        TextArea searchField = new TextArea();
        styleTextArea(searchField);
        searchField.setPrefRowCount(10);
        searchField.setWrapText(true);
        searchField.setPromptText("全成分を入力（・や、を含めたままでOK）");

        TextArea resultArea = new TextArea();
        styleTextArea(resultArea);
        resultArea.setWrapText(true);

        Button searchButton = new Button("Search");
        styleButton(searchButton);

        searchButton.setOnAction(e -> searchIngredients(searchField.getText(), resultArea));

        HBox buttonBox = new HBox(searchButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10, titleLabel, searchField, buttonBox, resultArea);
        styleVBox(layout);
        VBox.setVgrow(resultArea, Priority.ALWAYS);

        Scene scene = new Scene(layout, 1000, 800);
        stage.setScene(scene);
        stage.setTitle("Cosmetics Ingredient Search");
        stage.show();
    }

    private void styleTitleLabel(Label label) {
        label.setStyle("-fx-font-size: 24px; -fx-text-fill: #ffffff; -fx-font-weight: bold;");
        label.setAlignment(Pos.CENTER);
        VBox.setMargin(label, new Insets(0, 0, 10, 0));
    }

    private void styleTextField(TextField textField) {
        textField.setStyle("-fx-background-color: #ffffff; " +
                "-fx-border-color: #d0d0d0; " +
                "-fx-border-radius: 10px; " +
                "-fx-background-radius: 10px; " +
                "-fx-padding: 10px;");
    }

    private void styleTextArea(TextArea textArea) {
        textArea.setStyle("-fx-background-color: #ffffff; " +
                "-fx-border-color: #d0d0d0; " +
                "-fx-border-radius: 10px; " +
                "-fx-background-radius: 10px; " +
                "-fx-padding: 10px;");
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #ff9999; " +
                "-fx-text-fill: #ffffff; " +
                "-fx-background-radius: 20px; " +
                "-fx-border-radius: 20px; " +
                "-fx-padding: 10px 20px;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #ff6666; " +
                "-fx-text-fill: #ffffff; " +
                "-fx-background-radius: 20px; " +
                "-fx-border-radius: 20px; " +
                "-fx-padding: 10px 20px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #ff9999; " +
                "-fx-text-fill: #ffffff; " +
                "-fx-background-radius: 20px; " +
                "-fx-border-radius: 20px; " +
                "-fx-padding: 10px 20px;"));
    }

    private void styleVBox(VBox vbox) {
        vbox.setStyle("-fx-background-color: #b0d8d8; -fx-padding: 20px; -fx-spacing: 20px;");
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(20);
    }

    private void searchIngredients(String searchText, TextArea resultArea) {
        System.out.println("Search Text: " + searchText);
        StringBuilder results = new StringBuilder();
        List<String> searchIngredients = new ArrayList<>(Arrays.asList(searchText.split("[・,、/]")));
        for (int i = 0; i < searchIngredients.size(); i++) {
            searchIngredients.set(i, searchIngredients.get(i).trim());
        }

        for (String searchIngredient : searchIngredients) {
            System.out.println("Searching for ingredient: " + searchIngredient);
            for (Ingredient ingredient : ingredients) {
                if (ingredient.getName().equalsIgnoreCase(searchIngredient) || ingredient.getAliases().contains(searchIngredient)) {
                    results.append("成分: ").append(ingredient.getName()).append("\n");
                    results.append("別名: ").append(String.join(", ", ingredient.getAliases())).append("\n");
                    results.append("説明: ").append(ingredient.getDescription()).append("\n");
                    results.append("危険度: ").append(ingredient.getHazardRating()).append("\n\n");
                }
            }
        }

        if (results.length() == 0) {
            resultArea.setText("一致する成分が見つかりませんでした。");
        } else {
            resultArea.setText(results.toString());
        }
    }

    public static void startApp(String[] args) {
        launch(args);
    }
}
