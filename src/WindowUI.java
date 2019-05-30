import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * An even more complex GUI, with menus and buttons and a text area.
 * Created by adalal on 2/23/16.
 */
public class WindowUI extends Application {
    private static final double SCENE_WIDTH = 500;
    private static final double SCENE_HEIGHT = 300;
    TextArea status = new TextArea("");

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        HBox menuPane = addMenus();
        VBox checkboxPane = addCheckboxes();
        Node statusPane = addStatus();

        root.setTop(menuPane);
        root.setCenter(statusPane);
        root.setLeft(checkboxPane);

        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setTitle("A more complex GUI");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private VBox addCheckboxes() {
        VBox checkboxes = new VBox();
        checkboxes.setAlignment(Pos.TOP_LEFT);
        checkboxes.setPadding(new Insets(5));
        checkboxes.setSpacing(5);

        Text directions = new Text("What is your favorite color? Check all that apply");
        directions.setFont(Font.font(14));

        CheckBox red = new CheckBox("Red");
        red.setAllowIndeterminate(false);
        red.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (red.isSelected()) {
                    status.setText("Mine too!");
                }
            }
        });

        CheckBox orange = new CheckBox("Orange");
        orange.setAllowIndeterminate(false);
        orange.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (orange.isSelected()) {
                    status.setText("Mine too!");
                }
            }
        });

        CheckBox yellow = new CheckBox("Yellow");
        yellow.setAllowIndeterminate(false);
        yellow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (yellow.isSelected()) {
                    status.setText("Mine too!");
                }
            }
        });

        CheckBox green = new CheckBox("Green");
        green.setAllowIndeterminate(false);
        green.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (green.isSelected()) {
                    status.setText("Mine too!");
                }
            }
        });

        CheckBox blue = new CheckBox("Blue");
        blue.setAllowIndeterminate(false);
        blue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (blue.isSelected()) {
                    status.setText("Mine too!");
                }
            }
        });

        CheckBox purple = new CheckBox("Purple");
        purple.setAllowIndeterminate(false);
        purple.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (purple.isSelected()) {
                    status.setText("Mine too!");
                }
            }
        });

        CheckBox none = new CheckBox("None of the above");
        none.setAllowIndeterminate(false);
        none.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (none.isSelected()) {
                    status.setText("Really? That is sad.");
                }
            }
        });

        checkboxes.getChildren().addAll(red, orange, yellow, green, blue, purple, none);
        return checkboxes;
    }

    private Node addStatus() {
        FlowPane pane = new FlowPane();
        status.setText("Status messages will appear here.");
        status.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 14));
        status.setWrapText(true);
        status.setPrefColumnCount(20);
        pane.getChildren().add(status);
        return pane;
    }

    private HBox addMenus() {
        HBox pane = new HBox();
        pane.setAlignment(Pos.TOP_LEFT);

        MenuBar menuBar = new MenuBar();
        menuBar.setMinWidth(SCENE_WIDTH);

        Menu fileMenu = new Menu("File");

        MenuItem open = new MenuItem("Open");
        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                status.setText("Time for some fun!");
            }
        });

        MenuItem close = new MenuItem("Close");
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                status.setText("Nooooo, we're not finished yet!");
            }
        });

        fileMenu.getItems().addAll(open, close);

        Menu exitMenu = new Menu("Exit");
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        exitMenu.getItems().add(exit);

        menuBar.getMenus().addAll(fileMenu, exitMenu);
        pane.getChildren().add(menuBar);

        return pane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
