package test;

import java.lang.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class MST {

    private static final int V = 5;
    int[][] tree = new int[V][V];


    // A utility function to find the vertex with minimum key
    // value, from the set of vertices not yet included in MST
    int minKey(int[] key, Boolean[] mstSet) {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < V; v++)
            if (!mstSet[v] && key[v] < min) {
                min = key[v];
                min_index = v;
            }

        return min_index;
    }

    // A utility function to print the constructed MST stored in
    // pi[]
    void builderMst(int[][] tree, int[] pi, int[][] graph) {
        for (int[] row : tree) {
            Arrays.fill(row, -1);
        }
        tree[0][0] = 0;
        for (int i = 1; i < V; i++) {
            tree[i][i] = 0;
            tree[i][pi[i]] = graph[i][pi[i]];
            tree[pi[i]][i] = graph[pi[i]][i];
        }
    }

    void printMST(int[] pi, int[][] graph, int[][] tree) {
        System.out.println("Edge \tWeight");
        for (int i = 1; i < V; i++)
            System.out.println(pi[i] + " - " + i + "\t" + graph[i][pi[i]]);
        System.out.println("\nAnd in the adjacency matrix form:\n");
        for (int[] ints : tree) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }

    // Array to store constructed MST
    int[] pi = new int[V];

    // Function to construct and print MST for a graph represented
    // using adjacency matrix representation
    void primMST(int[][] graph) {

        // Key values used to pick minimum weight edge in cut
        int[] key = new int[V];

        // To represent set of vertices included in MST
        Boolean[] mstSet = new Boolean[V];

        // Initialize all keys as INFINITE
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        for (int[] ints : graph) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
        // Always include first 1st vertex in MST.
        key[0] = 0; // Make key 0 so that this vertex is
        // picked as first vertex
        pi[0] = -1; // First node is always root of MST

        // The MST will have V vertices
        for (int count = 0; count < V - 1; count++) {
            // Pick thd minimum key vertex from the set of vertices
            // not yet included in MST
            int u = minKey(key, mstSet);

            // Add the picked vertex to the MST Set
            mstSet[u] = true;

            // Update key value and pi index of the adjacent
            // vertices of the picked vertex. Consider only those
            // vertices which are not yet included in MST
            for (int v = 0; v < V; v++)

                // graph[u][v] is non-zero only for adjacent vertices of m
                // mstSet[v] is false for vertices not yet included in MST
                // Update the key only if graph[u][v] is smaller than key[v]
                if (graph[u][v] != (-1) && !mstSet[v] && graph[u][v] < key[v]) {
                    pi[v] = u;
                    key[v] = graph[u][v];
                }
        }

        builderMst(tree, pi, graph);
        // print the constructed MST
        printMST(pi, graph, tree);
    }

    public static void addEdge(int[][] tree, int v1, int v2, int weight, int[] pi) {
        tree[v1][v2] = weight;
        tree[v2][v1] = weight;
        pi[v1] = v2;
        pi[v2] = v1;
    }

    int[] count = new int[V];

    class edge{
        int v1, v2, weight;

        public edge(int v1, int v2) {
            this.v1 = v1;
            this.v2 = v2;
        }
    }

    List<edge> theCycle = new ArrayList<>();

    void getCycle(int[] pi, Boolean[] visited, int start) {
        while(start<pi.length) {
            if (pi[start] != -1) {
                visited[start] = true;
            }
            if (pi[start]!=-1 && theCycle.contains(new edge(start,pi[start]))){
                break;
            }
            if (visited[start]){
                theCycle.add(new edge(start, pi[start]));
            }
            start = (start++)%5;

        }
    }


    Boolean[] visited = new Boolean[V];
    List<Integer> cycle= new ArrayList<>();

    int[] parent = new int[V];

    void dfs(Boolean[] arr, int start, int[][] tree){


        int[][] adjMat =new int[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (tree[i][j]==-1 || i==j)
                    adjMat[i][j]=0;
                else
                    adjMat[i][j]=1;
            }
        }



        Arrays.fill(parent, -1);
        falseInit(arr);
        dfsCycle(start, adjMat, parent);
        falseInit(arr);
        getCycle(parent, arr, start);
        System.out.println("the cycle is:");
        for (edge e:
             theCycle) {
            System.out.println(e.v1 + " "+e.v2);
        }

//        getCycle(parent);

    }

    void falseInit(Boolean[] arr){
        Arrays.fill(arr, false);
    }

    void dfsCycle(int start, int[][] tree, int[] parent) {

        visited[start] = true;
//        parent[start]=-1;
        for (int i = 0; i < tree[start].length; i++) {

            if (tree[start][i] == 1 && (!visited[i])) {
                parent[i] = start;
                tree[start][i] = 2;
                tree[i][start] = 2;
                dfsCycle(i, tree, parent);
            }
            else if(tree[start][i] == 1 && (visited[i])){
                parent[i] = start;
                tree[start][i] = 2;
                tree[i][start] = 2;
            }

        }

    }


    public static void main(String[] args) {
		/* Let us create the following graph
		2 3
		(0)--(1)--(2)
		| / \ |
		6| 8/ \5 |7
		| /	 \ |
		(3)-------(4)
			9		 */
        MST t = new MST();
        int[][] graph = new int[][]
                {
//                        {0, 2, 0, 6, 0},
//                        {2, 0, 3, 8, 5},
//                        {0, 3, 0, 0, 7},
//                        {6, 8, 0, 0, 9},
//                        {0, 5, 7, 9, 0}};

//                        {0,6,-1,-1,-1},
//                        {6,0,7,-1,40},
//                        {-1,7,0,8,-1},
//                        {-1,-1,8,0,9},
//                        {-1,40,-1,9,0}
                        {0,6,-1,-1,-1},
                        {6,0,40,-1,7},
                        {-1,40,0,8,-1},
                        {-1,-1,8,0,9},
                        {-1,7,-1,9,0}
                };


        // Print the solution
        t.primMST(graph);
        addEdge(t.tree, 1, 4,5,t.pi);
        t.dfs(t.visited,0,graph);
    }

}



