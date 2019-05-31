import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.util.Random;

import static java.lang.Boolean.TRUE;

/**
 * The Hello World application from the JavaFX tutorial.
 * Source: http://docs.oracle.com/javase/8/javafx/get-started-tutorial/hello_world.htm#CHDIFJHE
 * Created by adalal on 2/23/16.
 */
public class HelloWorld extends Application {

    private Stage window;
    private StackPane layout;
    private Text title;
    private MenuBox startMenu;

    private Parent createContent() {
        layout = new StackPane();
        layout.setPrefSize(500, 600);
        // background for the game
        Rectangle bg = new Rectangle();
        bg.widthProperty().bind(layout.widthProperty());
        bg.heightProperty().bind(layout.heightProperty());

        // creating menu
        MenuItem exitButton = new MenuItem("EXIT");
        exitButton.setOnMousePressed( event -> {
            System.exit(0);
                }
        );
        MenuItem play = new MenuItem("PLAY");
        MenuItem controls = new MenuItem("CONTROLS");
        startMenu = new MenuBox(play, controls, exitButton);
        startMenu.setTranslateY(300);

        //making the title
        title = new Text(50,100, "SPACE INVADERS");
        title.setFill(Color.LIGHTGREEN);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 50));

        layout.getChildren().addAll(bg,title, startMenu);
        layout.setAlignment(title, Pos.TOP_CENTER);
        layout.setAlignment(startMenu, Pos.CENTER);
        return layout;
    }

    public void start(Stage spaceStage) {
        window = spaceStage;
        window.setTitle("Space Invaders");
        Scene scene = new Scene(createContent());
        window.setScene(scene);
        window.show();
    }

    // Credit to https://github.com/Siderim for the inspiration for the menu

    private static class MenuBox extends VBox {
        private MenuBox(MenuItem... items) {
            getChildren().add(createSeparator());

            for (MenuItem item : items) {
                getChildren().addAll(item, createSeparator());
            }

        }

        private Line createSeparator() {
            Line sep = new Line();
            sep.setEndX(210);
            sep.setStroke(Color.DARKGREY);
            return sep;
        }
    }

        private static class MenuItem extends StackPane{
            private MenuItem(String name) {

                Rectangle background = new Rectangle(300,40);
                background.setOpacity(.4);

                // LIGHT GREEN MONOSPACE MENU TEXT
                Text text = new Text(name);
                text.setFill(Color.LIGHTGREEN);
                text.setFont(Font.font("Monospace", FontWeight.SEMI_BOLD,30));

                setAlignment(Pos.CENTER);
                getChildren().addAll(background, text);

                setOnMouseEntered(event -> {
                    background.setFill(Color.DARKGREEN);
                    text.setFill(Color.WHITE);
                });

                setOnMouseExited(event -> {
                    background.setFill(Color.BLACK);
                    text.setFill(Color.LIGHTGREEN);
                });
                setOnMousePressed(event -> {
                    background.setFill(Color.LIGHTSKYBLUE);
                    text.setFill(Color.RED);
                });

                setOnMouseReleased(event -> {
                    background.setFill(Color.BLACK);
                    text.setFill(Color.WHITE);
                });
            }
        }

    public static void main(String[] args) {
        launch(args);
    }
}
