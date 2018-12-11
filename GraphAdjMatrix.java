import java.lang.Integer;

public class GraphAdjMatrix implements Graph{

    private int[][] matrix;
    private final int numVerts;

    public GraphAdjMatrix(int vertices){
        this.matrix = new int[vertices][vertices];
        this.numVerts = vertices;

        for(int i = 0; i < vertices; i++){
            for (int j = 0; j < vertices; j++){
                matrix[i][j] = 0;
            }
        }

    }

    @Override
    public void addEdge(int v1, int v2, int weight) {
        matrix[v1][v2] = weight;
        matrix[v2][v1] = weight;
    }

    @Override
    public int getEdge(int v1, int v2) {
        return matrix[v1][v2];
    }

    @Override
    public int createSpanningTree() {

        //creates min spanning tree
        // removes any edges that are unnecessary
        //returns weight of tree

        int[] parent = new int[numVerts]; //this will store the minimum spanning tree
        int[] keys = new int[numVerts]; //this holds the key values needed to determine the minimum edge- ie holds the min weights
        boolean[] addedYN = new boolean[numVerts]; //shows whether each number is included in the mst yet or no

        //initialize all the keys to infinity- or the highest possible number
        for (int i=0; i < numVerts; i++){
            keys[i] = Integer.MAX_VALUE;
            addedYN[i] = false;
        }

        keys[0] = 0; // set first key to 0 so it starts here
        parent[0]= -1; //make first number the root of the tree

        //go through all the vertices
        for (int j = 0; j < numVerts-1; j++){
            int min = minKey(keys, addedYN);  //find the minimum key the set of vertices not yet in the tree
            addedYN[min] = true; //show a vertex has been chosen to be added to the tree

            //update keyvalue and parent index of the vertex to be added
            for (int k=0; k < numVerts; k++){
                //nonzero for neighbors of most recently considered number in the graph
                //false for vertices not yet included
                //update key only if matrix[min][k] is less than key[v]
                if(matrix[min][k] != 0 && !addedYN[k] && matrix[min][k] < keys[k]){
                    parent[k] = min;
                    keys[k] = matrix[min][k];
                }
            }

        }

        //delete edges that are not in the minimum spanning tree
        //go through matrix, and if value is not one of the (index, parent)
        for(int s = 0; s < numVerts; s++){
            for (int t = s; t < numVerts; t++){
                boolean inIt = isInMST(s, t, parent);
                if(!inIt){
                    matrix[s][t] = 0; //change edge to 0- ie delete edge
                    matrix[t][s] = 0;
                }
            }
        }


        //calculate the total weight of the mst- add up weights of each edg
        int total = 0;
        for (int q = 1; q < parent.length; q++){
            total += matrix[q][parent[q]];
        }

        return total;
    }

    private boolean isInMST(int index, int value, int[] parent ){
        for (int i = 0; i < parent.length; i++){
            if(index != i && value != parent[i]){
                return true;
            }
        }
        return false;
    }

    private int minKey(int[] keys, boolean[] addedYN){
        //finds the smallest key (ie weight)
        int min = Integer.MAX_VALUE;
        int min_index = -1;

        for (int v = 0; v < numVerts; v++)
            if (!addedYN[v] && keys[v] < min) {
                min = keys[v];
                min_index = v;
            }

        return min_index;
    }

    //Functions from Graph interface that will not be implemented
    @Override
    public void addEdge(int v1, int v2) {}

    @Override
    public void topologicalSort() {}

}
