import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * A slightly more complex GUI, with buttons and text and multiple layouts.
 * Created by adalal on 2/23/16.
 */
public class ButtonSample extends Application {
    public static final double MIN_BUTTON_WIDTH = 60;
    private Text directions = new Text("Don't Press The Left Buttons");
    private Text feedback = new Text("");
    private int ohNo;

    @Override
    public void start(Stage primaryStage) {
        // BORDER PANE DIVIDES THE THING INTO DIFFERENT AREAS
        BorderPane root = new BorderPane();
        Node buttonPane = addButtons();
        Node directionsPane = addText(directions, FontWeight.BOLD, 20);
        Node feedbackPane = addText(feedback, FontWeight.NORMAL, 14);

        // BORDER PANE SETTING TOP AND CENTER AND BOTTOM
        root.setTop(directionsPane);
        root.setCenter(buttonPane);
        root.setBottom(feedbackPane);

        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Buttons, buttons everywhere");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private Node addButtons() {
        ohNo = 0;
        GridPane buttonPane = new GridPane();
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setHgap(5);
        buttonPane.setVgap(5);
        buttonPane.setPadding(new Insets(0, 10, 0, 10));

        // Create the four buttons and add them to the grid
        Button eeny = new Button("eeny");
        eeny.setMinWidth(MIN_BUTTON_WIDTH);
        buttonPane.add(eeny, 0, 0);
        Button meeny = new Button("meeny");
        meeny.setMinWidth(MIN_BUTTON_WIDTH);
        buttonPane.add(meeny, 1, 0);
        Button miney = new Button("miney");
        miney.setMinWidth(MIN_BUTTON_WIDTH);
        buttonPane.add(miney, 0, 1);
        Button moe = new Button("moe");
        moe.setMinWidth(MIN_BUTTON_WIDTH);
        buttonPane.add(moe, 1, 1);

        // Associate actions with each button
        eeny.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (ohNo % 2 == 0) {
                    feedback.setText("ASDJKNSKJADNKJ");
                    ohNo++;
                } else {
                    feedback.setText("Pressed " + meeny.getText());
                    ohNo++;
                }
            }
        });

        meeny.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                feedback.setText("Pressed " + meeny.getText());
            }
        });

        miney.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                feedback.setText("NONONONONONONONONO NOT THAT FORREST");
            }
        });

        moe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                feedback.setText("Pressed " + moe.getText());
            }
        });

        return buttonPane;
    }

    private Node addText(Text text, FontWeight fontWeight, int fontSize) {
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER);
        text.setFont(Font.font("Times New Roman", fontWeight, fontSize));
        flowPane.getChildren().add(text);
        return flowPane;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
