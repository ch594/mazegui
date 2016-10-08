package maze;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.Stack;

public class Main extends Application {

    private boolean mRight, mLeft, mDown, mUp, collisionDetected;

    private enum Direction{
        UP, DOWN, RIGHT, LEFT, NONE
    }

    private Direction dir;

    @Override
    public void start(Stage primaryStage) throws Exception{

        final double screenWidth = 800;
        final double screenHeight = 600;
        Group root = new Group();

        Group grid = new Group();

        final int grid_width = 20;
        final int grid_height = 20;
        final int step = 25;
        final double s_pos_x = screenWidth/2 - (grid_width * step)/2.0;
        final double s_pos_y = screenHeight/15;


        Rectangle rect = new Rectangle(10,10, Color.DARKGREEN);
        root.getChildren().add(rect);
        rect.setLayoutX(s_pos_x + 3);
        rect.setLayoutY(s_pos_y + 3);


        Button gen = new Button("Generate");
        System.out.println("Button width: " + gen.widthProperty());
        root.getChildren().add(gen);
        gen.setMinWidth(75);
        gen.setLayoutX(screenWidth/2 - gen.getMinWidth()/2);
        gen.setLayoutY(screenHeight - 50);

        MDraw maze = new MDraw();
        maze.drawMaze(grid, grid_width, grid_height, s_pos_x, s_pos_y, step);

        gen.setOnAction(event -> {
            if(!grid.getChildren().isEmpty()){
                grid.getChildren().clear();

            }
            rect.setLayoutX(s_pos_x + 3);
            rect.setLayoutY(s_pos_y + 3);
            maze.drawMaze(grid, grid_width, grid_height, s_pos_x, s_pos_y, step);

        });
        root.getChildren().add(grid);
        primaryStage.setTitle("Maze");
        Scene scene = new Scene(root, screenWidth, screenHeight, Color.BISQUE);

        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.RIGHT){
                mRight = true;
            }
            else if (event.getCode() == KeyCode.LEFT){
                mLeft = true;
            }
            else if(event.getCode() == KeyCode.UP){
                mUp = true;
            }
            else if(event.getCode() == KeyCode.DOWN){
                mDown = true;
            }
        });

        scene.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.RIGHT){
                mRight = false;
            }
            else if(event.getCode() == KeyCode.LEFT){
                mLeft = false;
            }
            else if(event.getCode() == KeyCode.UP){
                mUp = false;
            }
            else if(event.getCode() == KeyCode.DOWN){
                mDown = false;
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double x = rect.getLayoutX();
                double y = rect.getLayoutY();
                double dx = 2;
                double dy = 2;

                if(collisionDetected){
                    if(mRight && dir != Direction.RIGHT){
                        collisionDetected = false;
                    }
                    else if(mLeft && dir != Direction.LEFT){
                        collisionDetected = false;
                    }
                    else if(mDown && dir != Direction.DOWN){
                        collisionDetected = false;
                    }
                    else if(mUp && dir != Direction.UP){
                        collisionDetected = false;
                    }
                }
                else {

                    for(Node l : grid.getChildren()){
                        if(rect.getBoundsInParent().intersects(l.getBoundsInLocal())){
                            collisionDetected = true;
                            if(mRight){
                                mRight = false;
                                dir = Direction.RIGHT;
                                rect.relocate(x - 3, y);
                            }
                            else if(mLeft){
                                mLeft = false;
                                dir = Direction.LEFT;
                                rect.relocate(x + 3, y);
                            }
                            else if(mDown){
                                mDown = false;
                                dir = Direction.DOWN;
                                rect.relocate(x, y - 3);
                            }
                            else if(mUp){
                                mUp = false;
                                dir = Direction.UP;
                                rect.relocate(x, y + 3);
                            }
                        }
                    }
                }

                if(!collisionDetected){

                    if(mRight) {
                        rect.relocate(x + dx, y);
                    }
                    else if(mLeft){
                        rect.relocate(x - dx, y);
                    }
                    else if(mDown){
                        rect.relocate(x, y + dy);
                    }
                    else if(mUp){
                        rect.relocate(x, y - dy);
                    }
                }
            }
        };

        timer.start();

        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("primary stage resized: " + newValue);
            gen.setLayoutX((double) newValue/2.0 - gen.getMinWidth()/2);
        });

        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {
            gen.setLayoutY((double)newValue - 100);
            System.out.println("height changed " + newValue);
            System.out.println(gen.boundsInParentProperty());
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
