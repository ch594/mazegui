package maze;

import javafx.scene.Group;

/**
 * Created by cheyenne on 10/6/16.
 */
public class MDraw {

    public void drawMaze(Group group, int width, int height, double start_x, double start_y, int step){

        MGraph g = new MGraph(width, height);
        addEdges(g,width, height);
        g.createMaze();

        Controller.drawGridOuter(group, start_x, start_y, width*step, height*step);
        for(int i = 0; i < g.e_size; i++){

            if(g.hasWall(i)){
                int x = g.getEdgeStart(i);
                int y = g.getEdgeEnd(i);
                if(Math.abs(x - y) == 1){
                    //draw left side
                    Controller.drawLine(group, start_x + (step * ((x % width) + 1)), start_x + (step * ((x % width) + 1)),
                            start_y + (step *(y/width)), start_y + (step *((y/width) + 1)));

                }
                else {
                    //draw bottom line
                    Controller.drawLine(group, start_x + (step *(x % width)), start_x + (step *((x % width)+1)),
                            start_y + (step *((y/width))), start_y + (step *((y/width))));

                }
            }

        }


    }

    private void addEdges(MGraph g, int width, int height){
        for(int i = 0; i < width - 1; i++){
            for(int k = 0; k < height; k++){
                int m = i + (k * width);
                g.addEdge(m, m + 1);
            }

        }

        for(int i = 0; i < width; i++){
            for(int k = 0; k < height - 1; k++){
                int m = i + (k*height);
                g.addEdge(m, m + width);
            }
        }
    }
}
