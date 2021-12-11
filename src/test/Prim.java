//Shani Yamin - 318014925
//Matan Gabso - 208233403

package test;

import java.lang.*;
import java.util.Arrays;
import java.util.Stack;

class Prim {

    private static final int V = 20;
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
        System.out.println("The MST is:");
        System.out.println("In a list form:");
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

        System.out.println("The Graph Is:");
        for (int[] ints : graph) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }

        System.out.println("\n**************************************\n");


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

    public static void addEdge(int[][] tree, int v1, int v2, int weight) {
        tree[v1][v2] = weight;
        tree[v2][v1] = weight;
    }

    Stack<Integer> cycleStack= new Stack<>();
    Boolean flag = false;
    Boolean findCycle(int[][] tree,int v1, int v2, Boolean flag) {
        if (v1 == v2) {
            return false;
        }
        int count = 0;
        for (int i = 0; i < tree[v1].length; i++) {
            if (flag){
                break;
            }
            if (tree[v1][i] != (-1) && v1 != i) {
                if (!cycleStack.isEmpty() && i == cycleStack.peek()) {
                    continue;
                }
                if (cycleStack.isEmpty() || v1 != cycleStack.peek()) { //לבדוק האם מצב שבו צריך להוציא קודרוד מהמחסנית כי הוא לא חלק מהמעגל
                    cycleStack.push(v1);
                }
                flag = findCycle(tree, i, v2, flag);
                if (i == v2) {
                    cycleStack.push(v2);
                    System.out.println("\n\n*******The Cycle is Found!!!*******");
                    flag = true;
                    break;
                }
            } else {
                count++;
            }
            if (count == (tree[v1].length - 1)){
                cycleStack.pop();
            }
        }
        return flag;
    }

    void removeMax(int v1,int v2, int weight){
        int maxX = v1;
        int maxY = v2;
        int maxWeight = weight;
        int tempX, tempY;

        int stackSize = cycleStack.size();
        for (int i = 0; i < stackSize-1; i++) {
            tempX = cycleStack.pop();
            tempY = cycleStack.peek();
            if(tree[tempX][tempY]>maxWeight){
                maxX = tempX;
                maxY=tempY;
                maxWeight = tree[tempX][tempY];
            }
        }
        System.out.println("The edge being removed is: "+ maxX +" - "+maxY+" and it weight is: "+maxWeight);
        tree[maxX][maxY] = -1;
        tree[maxY][maxX] = -1;
    }

    void q2(int[][] tree ,int v1, int v2, int weight, Boolean flag) {
        addEdge(tree, v1, v2, weight);
        System.out.println("\n\nThe added edge is:" + v1 + " - " + v2 + ", and it weight is: " + weight);
        //בדיקה
        System.out.println("\nThe Updated MST is:\n");
        for (int[] ints : tree) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }

        findCycle(tree, v1, v2, flag);
        removeMax(v1, v2, weight);
        //בדיקה
        System.out.println("\nThe final MST is:\n");
        for (int[] ints : tree) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }


    public static void main(String[] args) {
        System.out.println("\n************************\nSTUDENTS\n************************");
        System.out.println("Shani Yamin - 318014925");
        System.out.println("Matan Gabso - 208233403");
        System.out.println("************************\n");


		/* Let us create the following graph
		2 3
		(0)--(1)--(2)
		| / \ |
		6| 8/ \5 |7
		| /	 \ |
		(3)-------(4)
			9		 */
        Prim t = new Prim();
        int[][] graph = new int[V][V];
//                {
////                        {0, 2, 0, 6, 0},
////                        {2, 0, 3, 8, 5},
////                        {0, 3, 0, 0, 7},
////                        {6, 8, 0, 0, 9},
////                        {0, 5, 7, 9, 0}};
//
////                        {0,6,-1,-1,-1},
////                        {6,0,7,-1,40},
////                        {-1,7,0,8,-1},
////                        {-1,-1,8,0,9},
////                        {-1,40,-1,9,0}
//                        {0,6,-1,-1,-1},
//                        {6,0,5,-1,7},
//                        {-1,5,0,8,-1},
//                        {-1,-1,8,0,40},
//                        {-1,7,-1,40,0}
//                };

        for (int i = 0; i < V; i++) {
            Arrays.fill(graph[i], -1);
            graph[i][i]=0;
        }

        addEdge(graph,1,3,4);
        addEdge(graph,1,2,1);
        addEdge(graph,1,4,5);
        addEdge(graph,2,5,2);
        addEdge(graph,2,6,8);
        addEdge(graph,6,12,9);
        addEdge(graph,11,12,6);
        addEdge(graph,12,18,8);
        addEdge(graph,6,7,7);
        addEdge(graph,7,13,9);
        addEdge(graph,3,7,10);
        addEdge(graph,13,14,1);
        addEdge(graph,3,8,6);
        addEdge(graph,4,9,9);
        addEdge(graph,8,9,10);
        addEdge(graph,8,14,5);
        addEdge(graph,13,18,2);
        addEdge(graph,4,10,4);
        addEdge(graph,9,15,8);
        addEdge(graph,14,19,5);
        addEdge(graph,15,19,6);
        addEdge(graph,15,16,7);
        addEdge(graph,16,17,8);
        addEdge(graph,10,16,9);
        addEdge(graph,5,10,3);
        addEdge(graph,5,11,11);
        addEdge(graph,11,17,10);
        addEdge(graph,18,0,3);
        addEdge(graph,17,0,7);
        addEdge(graph,19,0,4);
        addEdge(graph,3,13,10);
        addEdge(graph,6,13,8);
        addEdge(graph,3,6,4);
        addEdge(graph,1,6,2);
        addEdge(graph,1,9,9);
        addEdge(graph,1,8,6);
        addEdge(graph,3,14,7);
        addEdge(graph,9,14,3);
        addEdge(graph,9,19,4);
        addEdge(graph,9,16,7);
        addEdge(graph,10,17,10);
        addEdge(graph,5,17,1);
        addEdge(graph,11,16,3);
        addEdge(graph,15,0,6);
        addEdge(graph,16,0,16);
        addEdge(graph,11,0,11);
        addEdge(graph,12,0,4);
        addEdge(graph,4,18,9);
        addEdge(graph,6,11,12);
        addEdge(graph,2,12,14);

        // Print the solution
        t.primMST(graph);
        System.out.println("\n******************\nAdding an edge that changes the MST:\n******************\n");
        t.q2(t.tree,3,4,40000000, t.flag);
        System.out.println("\n******************\nAdding an edge that doesn't change the MST:\n******************\n");
        t.q2(t.tree,3,4,1, t.flag);
    }

}



