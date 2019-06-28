import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import javafx.scene.layout.Pane;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import java.io.FileInputStream;
import javafx.scene.layout.StackPane;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.image.Image;
import static javafx.scene.input.KeyCode.*;


public class InGameObject{
    private Pane root = new Pane();
    private Image image;
    private int counter = 17;
    private boolean direction = true;
    private boolean left,right;
    private double t = 0;
    private int score;
    private int roundScore;
    private double prob = .1;
    private Text playerScore;
    private double timer = 1;
    private Text playerLives;
    private int numBullets = 0;
    public Sprite player = new Sprite(200, 550, 30, 30, "player", Color.WHITE);

    ArrayList<ArrayList<Sprite>> enemies = nextLevel();

    ArrayList<Barrier> barriers = placeBarriers();

    /*
     * createContent creates and initializes our whole scene into the window, then returns the Scene
     *
     * sets our window size, background, draws the players, enemies, score, lives, and sets the controls for the game
     * after that, it uses a timer to begin the game
     *
     * @params oldScene: scene that represents menu, window: current application window
     * @returns Scene with game drawn
     */
    public Scene createContent(Scene oldScene, Stage window) {
        root.setPrefSize(500, 600);
        root.setStyle("-fx-background-color: black;");
        ImagePattern allyImage = loadImagePattern("./images/ally-ship-large.png");
        player.setFill(allyImage);
        root.getChildren().add(player);
        loadEnemies();
        playerLives = createLives();
        playerScore = createScore(0);
        createLivesText();
        createScoreText();
        root.getChildren().add(playerScore);
        root.getChildren().add(playerLives);
        for (Barrier barrier : barriers){
            root.getChildren().add(barrier);
        }
        nextLevel();
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case A: left = true; break;
                    case D: right = true; break;
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case A: left = false; break;
                    case D: right = false; break;
                    case SPACE: playerShoot(player); break;
                }
            }
        });

        //stage.setScene(scene);
        AnimationTimer timer = new AnimationTimer() {
            public void handle(long now) {
                update();
                if (right) player.moveRight();
                if (left) player.moveLeft();
                if (player.dead) {
                    this.stop();
                    Parent gameOverRoot = gameOver(oldScene, window);
                    Scene gameOverScene = new Scene(gameOverRoot);
                    window.setScene(gameOverScene);
                }
            }
        };
        timer.start();

        return scene;
    }

    /*
     * creates a screen for our game over screen and draws it, then returns that scene
     *
     * @params oldScene: takes the old scene and draws on that; window: where to draw the new content
     * @returns scene of game over screen
     */
    private Parent gameOver(Scene oldScene, Stage window) {
        StackPane parent = new StackPane();
        parent.setPrefSize(500, 600);

        Rectangle background = new Rectangle();
        background.setFill(Color.BLACK);
        background.widthProperty().bind(parent.widthProperty());
        background.heightProperty().bind(parent.heightProperty());


        Text title = new Text(50, 100, "Game Over");
        title.setFill(Color.RED);
        title.setTranslateY(30);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 40));

        try {
            image = new Image(new FileInputStream("./images/alien.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ImageView picture = new ImageView(image);
        picture.setTranslateY(100);
        picture.setFitHeight(150);
        picture.setFitWidth(100);

        Text scoreTitle = new Text(50, 100, "Score: " + score);
        scoreTitle.setFill(Color.LIGHTGREEN);
        scoreTitle.setTranslateY(-20);
        scoreTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 40));

        Text home = new Text(50, 100, "Back to Home");
        home.setFill(Color.LIGHTGREEN);
        home.setTranslateY(150);
        home.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        home.setOnMouseClicked(event -> {
            window.setScene(oldScene);
        });
        parent.setOnMouseEntered(event -> {
            home.setFill(Color.WHITE);
        });

        parent.setOnMouseExited(event -> {
            home.setFill(Color.LIGHTGREEN);
        });

        parent.setOnMousePressed(event -> {
            home.setFill(Color.RED);
        });

        parent.getChildren().addAll(background, title, picture,scoreTitle, home);
        StackPane.setAlignment(picture, Pos.TOP_CENTER);
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setAlignment(scoreTitle, Pos.CENTER);
        StackPane.setAlignment(home, Pos.CENTER);

        return parent;
    }

    /*
     * Controls the interactions for our game - including sprite interactions, moving enemies, and new rounds
     */
    private void update() {
        t += 0.05 * timer;
        if (t > 2) {
            // enemy movement - on a counter
            if (direction) {
                if (counter <= 17) {
                    enemies.forEach(row -> row.forEach(enemy -> enemy.moveRight()));
                    counter++;
                } else if (counter == 18) {
                    enemies.forEach(row -> row.forEach(enemy -> enemy.moveDown()));
                    counter = 1;
                    direction = false;
                }
            }   else {
                if (counter <= 17) {
                    enemies.forEach(row -> row.forEach(enemy -> enemy.moveLeft()));
                    counter++;
                } else if (counter == 18) {
                    enemies.forEach(row -> row.forEach(enemy -> enemy.moveDown()));
                    counter = 1;
                    direction = true;
                }
            }

        }
        //sprite interactions between bullets, barriers, and enemies
        for(Sprite s : sprites()) {
            switch (s.type) {
                case "enemybullet":
                    s.moveDown();
                    if (s.getBoundsInParent().intersects(player.getBoundsInParent())) {
                        playerHit(player, s);
                    }
                    sprites().stream().filter(b -> b.type.equals("barrier")).forEach(barrier -> {
                        if (s.getBoundsInParent().intersects(barrier.getBoundsInParent())) {
                            hitBarrier(barrier, s);
                        }
                    });
                    break;

                case "playerbullet":
                    s.moveUp();
                    sprites().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            playerKillEnemy(enemy, s);
                            numBullets--;
                        }
                    });
                    sprites().stream().filter(b -> b.type.equals("barrier")).forEach(barrier -> {
                        if (s.getBoundsInParent().intersects(barrier.getBoundsInParent())) {
                            hitBarrier(barrier, s);
                            numBullets--;
                        }
                    });
                    if (s.getTranslateY() < 20) {
                        s.dead = true;
                        root.getChildren().remove(s);
                        numBullets --;
                    }
                    break;

                case "enemy":
                    if (t>2) {
                        if ((Math.random() < prob) && s.front){
                            shoot(s);
                        }
                    }
                    if (s.getTranslateY() >= 450){
                        player.dead = true;
                    }
                    break;
            }
        }
        if (t > 2) {
            t = 0;
        }
        // sets new rounds
        if (roundScore > 0 && score % 350 == 0) {
            startNextRound();
        }

        // sets the enemy in each column which will shoot
        enemies.forEach(row -> row.forEach(enemy -> {
            if (enemy.front && enemy.dead && enemy.row != 0 ) {
                enemies.get(enemy.row -1).get(enemy.col).front = true;
            }
        }));
    }

    /*
    * creates and returns an ArrayList of barrier objects
    * @return ArrayList of barrier sprites
     */
    private ArrayList<Barrier> placeBarriers() {
        ArrayList<Barrier> barriers = new ArrayList<>();

        for (int i = 0; i < 4;i++){
            for (int j = 0; j < 3; j++) {
                barriers.add(new Barrier(25 + 130 * i + 20 * j, 480, 20, 20, Color.GREEN));
                if(j != 1){
                    barriers.add(new Barrier(25 + 130 * i + 20 * j, 500, 20, 20, Color.GREEN));
                }
            }
        }
        return barriers;
    }

    /*
     * loads the enemy sprites and adds them to an ArrayList and returns that
     *
     * @returns ArrayList of enemy sprite objects
     */
    private ArrayList<ArrayList<Sprite>> nextLevel() {
        ArrayList<ArrayList<Sprite>> enemies = new ArrayList<>();
        ImagePattern enemyImage = loadImagePattern("./images/white-enemy.png");
        for(int j = 0; j <5; j++) {
            enemies.add(new ArrayList<Sprite>());
            for (int i = 0; i < 7; i++) {
                Sprite enemy = new Sprite(90 + i * 60, 75+50*j, 40, 40, "enemy", Color.WHITE);
                enemy.setFill(enemyImage);
                enemy.row = j;
                enemy.col = i;
                if (i == 6){
                    enemy.setRightmost();
                }
                if (i ==0){
                    enemy.setLeftmost();
                }
                if (j == 4){
                    enemy.front = true;
                }
                enemies.get(j).add(enemy);
            }
        }
        return enemies;
    }

    /*
     * adds the enemies to the current window
     */
    private void loadEnemies() {
        for (int i = 0; i < 5; i++) {
            for (Sprite s : enemies.get(i)) {
                root.getChildren().add(s);
            }
        }
    }

    /*
     * filter among the current window to help find the sprite (enemy, player, bullet) and barrier objects for use in update
     */
    private List<Sprite> sprites() {
        return root.getChildren().stream().filter(x-> x.getClass().equals(Sprite.class)|| x.getClass().equals(Barrier.class)).map(n -> (Sprite)n).collect(Collectors.toList());
    }

    /*
     * creates a
     */
    public void playerShoot(Sprite shooter) {
        if (!player.dead && numBullets < 2) {
            Sprite s = new Sprite((int) shooter.getTranslateX() + 13, (int) shooter.getTranslateY(), 5, 20, shooter.type + "bullet", Color.RED);
            root.getChildren().add(s);
            numBullets++;
        }
    }

    public void shoot(Sprite shooter) {
        Sprite s = new Sprite((int) shooter.getTranslateX() + 13, (int) shooter.getTranslateY(), 5, 20, shooter.type + "bullet", Color.RED);
        root.getChildren().add(s);
    }

    private void playerKillEnemy(Sprite enemy, Sprite bullet){
        enemy.dead = true;
        score = score + 10;
        roundScore = roundScore + 10;
        root.getChildren().remove(playerScore);
        playerScore = createScore(score);
        root.getChildren().add(playerScore);
        bullet.dead = true;
        timer = timer*1.09;
        root.getChildren().remove(bullet);
        root.getChildren().remove(enemy);
    }

    private void hitBarrier(Sprite barrier, Sprite bullet) {
        barrier.hitpoints--;
        bullet.dead = true;
        root.getChildren().remove(bullet);
        if (barrier.hitpoints == 0) {
            barrier.dead = true;
            bullet.dead = true;
            root.getChildren().remove(barrier);
            root.getChildren().remove(bullet);
        }
    }

    private void playerHit(Sprite player, Sprite bullet) {
        player.lives -= 1;
        if (player.lives == 0) {
            player.dead = true;
        }
        root.getChildren().remove(playerLives);
        playerLives = createLives();
        root.getChildren().add(playerLives);
        bullet.dead = true;
        root.getChildren().remove(bullet);
        root.getChildren().remove(player);
        root.getChildren().add(player);
    }

    private void startNextRound() {
        ArrayList<Barrier> barriers = placeBarriers();
        roundScore = 0;
        counter = 17;
        direction = true;
        enemies = nextLevel();
        prob += .05;
        timer += 1;
        for (Barrier barrier : barriers){
            root.getChildren().add(barrier);
        }
        loadEnemies();
    }

    private ImagePattern loadImagePattern(String dir) {
        try {
            image = new Image(new FileInputStream(dir));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ImagePattern picture = new ImagePattern(image);
        return picture;
    }

    private void createLivesText() {
        Text livesText = new Text("LIVES: ");
        livesText.setFill(Color.WHITE);
        livesText.setFont(Font.font("Verdana", 25));
        livesText.setTranslateX(370);
        livesText.setTranslateY(30);
        root.getChildren().add(livesText);

    }

    private Text createLives() {
        Text text1 = new Text();
        text1.setFill(Color.RED);
        text1.setTranslateX(470);
        text1.setTranslateY(30);
        text1.setText(Integer.toString(player.lives));
        text1.setFont(Font.font("Verdana", 25));
        return text1;
    }

    private Text createScore(int score) {
        Text text1 = new Text();
        text1.setFill(Color.LIGHTGREEN);
        text1.setTranslateX(110);
        text1.setTranslateY(30);
        text1.setText(Integer.toString(score));
        text1.setFont(Font.font("Verdana", 25));
        return text1;
    }

    private void createScoreText() {
        Text textScore = new Text("SCORE:");
        textScore.setFill(Color.WHITE);
        textScore.setTranslateY(30);
        textScore.setTranslateX(5);
        textScore.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        root.getChildren().add(textScore);
    }

}


