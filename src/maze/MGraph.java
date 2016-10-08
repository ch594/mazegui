package maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by John on 9/20/2016.
 */
public class MGraph {


    private int m, n;
    public  int e_size;
    private ArrayList<Edge> edges;


    public MGraph(int m, int n){
        this.m = m;
        this.n = n;
        e_size = (m-1)*n + n*(m-1);
        edges = new ArrayList<>();
    }


    public boolean addEdge(int c1, int c2){
        Vector v = new Vector(c1);
        Vector w = new Vector(c2);

        edges.add(new Edge(v,w));
        return true;
    }

    public List<Edge> getEdges(){
        return Collections.unmodifiableList(edges);
    }

    private class Vector{
        int cell;

        private Vector(int cell){
            this.cell = cell;
        }
    }

    private class Edge{
        int weight;
        boolean wall;
        Vector v;
        Vector w;

        public Edge(Vector v, Vector w){
            this.v = v;
            this.w = w;
            wall = true;
        }
    }

    private class Subset{
        int rank;
        int parent;

        private Subset(int parent){
            this.rank = 0;
            this.parent = parent;
        }
    }

    private int unionFind(Subset subset[], int i){
        if(subset[i].parent == i){
            return i;
        }
        else{
            return unionFind(subset, subset[i].parent);
        }
    }

    private void Union(Subset subset[], int x, int y){

        if(x == y){
            return;
        }
        int root_y = unionFind(subset, y);
        int root_x = unionFind(subset, x);

        if(subset[root_y].rank > subset[root_x].rank){
            subset[root_x].parent = root_y;
        }
        else if(subset[root_x].rank > subset[root_y].rank){
            subset[root_y].parent = root_x;
        }
        else{
            subset[root_x].parent = root_y;
            subset[root_y].rank++;
        }
    }

    public void createMaze(){
        ArrayList<Integer> rand = new ArrayList<>(e_size);
        for(int i = 0; i < e_size; i++){
            rand.add(i);
        }
        Collections.shuffle(rand);

        Subset subsets[] = new Subset[m*n];
        for(int i = 0; i < m*n; i++){
            subsets[i] = new Subset(i);
        }

        int walls_down = 0, index = 0;

        while (index < e_size){
            Edge cur_edge = edges.get(rand.get(index));
            if(cur_edge.wall){
                int source_cell_set = unionFind(subsets, cur_edge.v.cell);
                int dest_cell_set = unionFind(subsets, cur_edge.w.cell);

                if(source_cell_set != dest_cell_set){
                    Union(subsets, cur_edge.v.cell, cur_edge.w.cell);
                    cur_edge.wall = false;
                    walls_down++;
                }
            }
            index++;
        }
    }

    public int getEdgeStart(int index){
        assert(index >= 0 && index < e_size);

        return edges.get(index).v.cell;

    }
    public int getEdgeEnd(int index){
        assert(index >= 0 && index < e_size);

        return edges.get(index).w.cell;
    }

    public boolean hasWall(int index){
        assert(index >= 0 && index < e_size);

        return edges.get(index).wall;
    }

    public static void main(String[] args) {

        int width = 3, height = 3;

        MGraph g = new MGraph(3,3);

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
        g.createMaze();

    }

}
