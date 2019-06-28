import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static javafx.scene.input.KeyCode.*;

public class Sprite extends Rectangle {

    boolean dead;
    final String type;
    boolean front;
    boolean leftmost;
    boolean rightmost;
    int row;
    int col;
    int hitpoints = 6;
    int lives = 3;

    Sprite(int x, int y, int w, int h, String type, Color color) {
        super(w, h, color);
        this.front = false;
        this.dead = false;
        this.rightmost = false;
        this.leftmost = false;
        this.row = 0;
        this.col = 0;
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }

    public void setRightmost() {
        this.rightmost = true;
    }

    public void setLeftmost() {
        this.leftmost = true;
    }


    public void moveLeft() {
        if (this.getTranslateX() >= 10) {
            setTranslateX(getTranslateX() - 5);
        }
    }

    public void moveRight() {
        if (this.getTranslateX() <= 460) {
            setTranslateX(getTranslateX() + 5);
        }
    }

    public void moveUp() {
        setTranslateY(getTranslateY() - 7);
    }

    public void moveDown() {
        setTranslateY(getTranslateY() + 8);
    }

}
