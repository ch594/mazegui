package maze;


import javafx.scene.Group;
import javafx.scene.shape.Line;

import java.util.Stack;

public class Controller {

    static void drawLine(Group group, double start_x, double end_x, double start_y, double end_y){
        Line t_line = new Line();
        t_line.setStartX(start_x);
        t_line.setEndX(end_x);
        t_line.setStartY(start_y);
        t_line.setEndY(end_y);
        group.getChildren().add(t_line);
    }

    static void drawGridOuter(Group group, double start_x, double start_y, int w, int h){

        drawLine(group, start_x, start_x + w, start_y, start_y);
        drawLine(group, start_x + w, start_x + w, start_y, start_y + h);
        drawLine(group, start_x + w, start_x, start_y + h, start_y + h);
        drawLine(group, start_x, start_x, start_y + h, start_y);

    }
}
