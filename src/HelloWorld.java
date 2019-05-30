import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
    private VBox vbox;
    private Text title;

    private Parent createContent() {
        layout = new StackPane();
        layout.setPrefSize(900, 600);

        Rectangle bg = new Rectangle(2000, 2000);

        Button itemExit = new Button("EXIT");
        itemExit.
        Button play = new Button("PLAY");
        Button controls = new Button("CONTROLS");
        title = new Text(50,100, "SPACE INVADERS");
        title.setFill(Color.LIGHTGREEN);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 60));


        vbox = new VBox(8); // spacing = 8
        vbox.getChildren().addAll(new Button("PLAY"), new Button("CONTROLS"), new Button("EXIT"));
        vbox.setAlignment(Pos.CENTER);
        vbox.setTranslateY(50);


        layout.getChildren().addAll(bg,title, vbox);
        layout.setAlignment(title, Pos.TOP_CENTER);
        layout.setAlignment(vbox, Pos.CENTER);
        return layout;
    }

    public void start(Stage spaceStage) {
        window = spaceStage;
        window.setTitle("Space Invaders");
        Scene scene = new Scene(createContent());
        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
