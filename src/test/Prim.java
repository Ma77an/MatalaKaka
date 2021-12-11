//Shani Yamin - 318014925
//Matan Gabso - 208233403

package test;

import java.lang.*;
import java.util.Arrays;
import java.util.Stack;

class Prim {

    private static final int V = 20;
    int[][] tree = new int[V][V];

    int minKey(int[] key, Boolean[] mstSet) {
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < V; v++)
            if (!mstSet[v] && key[v] < min) {
                min = key[v];
                min_index = v;
            }

        return min_index;
    }

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

    int[] pi = new int[V];

    void primMST(int[][] graph) {


        int[] key = new int[V];

        Boolean[] mstSet = new Boolean[V];

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

        key[0] = 0;
        pi[0] = -1;

        for (int count = 0; count < V - 1; count++) {
            int u = minKey(key, mstSet);
            mstSet[u] = true;
            for (int v = 0; v < V; v++) {
                if (graph[u][v] != (-1) && !mstSet[v] && graph[u][v] < key[v]) {
                    pi[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }
        builderMst(tree, pi, graph);
        printMST(pi, graph, tree);
    }

    public static void addEdge(int[][] tree, int v1, int v2, int weight) {
        tree[v1][v2] = weight;
        tree[v2][v1] = weight;
    }

    Stack<Integer> cycleStack = new Stack<>();
    Boolean flag = false;

    Boolean findCycle(int[][] tree, int v1, int v2, Boolean flag) {
        if (v1 == v2) {
            return false;
        }
        int count = 0;
        for (int i = 0; i < tree[v1].length; i++) {
            if (flag) {
                break;
            }
            if (tree[v1][i] != (-1) && v1 != i) {
                if (!cycleStack.isEmpty() && i == cycleStack.peek()) {
                    continue;
                }
                if (cycleStack.isEmpty() || v1 != cycleStack.peek()) {
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
            if (count == (tree[v1].length - 1)) {
                cycleStack.pop();
            }
        }
        return flag;
    }

    void removeMax(int v1, int v2, int weight) {
        int maxX = v1;
        int maxY = v2;
        int maxWeight = weight;
        int tempX, tempY;

        int stackSize = cycleStack.size();
        for (int i = 0; i < stackSize - 1; i++) {
            tempX = cycleStack.pop();
            tempY = cycleStack.peek();
            if (tree[tempX][tempY] > maxWeight) {
                maxX = tempX;
                maxY = tempY;
                maxWeight = tree[tempX][tempY];
            }
        }
        System.out.println("The edge being removed is: " + maxX + " - " + maxY + " and it weight is: " + maxWeight);
        tree[maxX][maxY] = -1;
        tree[maxY][maxX] = -1;
    }

    void q2(int[][] tree, int v1, int v2, int weight, Boolean flag) {
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

        Prim t = new Prim();
        int[][] graph = new int[V][V];
        for (int i = 0; i < V; i++) {
            Arrays.fill(graph[i], -1);
            graph[i][i] = 0;
        }

        addEdge(graph, 1, 3, 4);
        addEdge(graph, 1, 2, 1);
        addEdge(graph, 1, 4, 5);
        addEdge(graph, 2, 5, 2);
        addEdge(graph, 2, 6, 8);
        addEdge(graph, 6, 12, 9);
        addEdge(graph, 11, 12, 6);
        addEdge(graph, 12, 18, 8);
        addEdge(graph, 6, 7, 7);
        addEdge(graph, 7, 13, 9);
        addEdge(graph, 3, 7, 10);
        addEdge(graph, 13, 14, 1);
        addEdge(graph, 3, 8, 6);
        addEdge(graph, 4, 9, 9);
        addEdge(graph, 8, 9, 10);
        addEdge(graph, 8, 14, 5);
        addEdge(graph, 13, 18, 2);
        addEdge(graph, 4, 10, 4);
        addEdge(graph, 9, 15, 8);
        addEdge(graph, 14, 19, 5);
        addEdge(graph, 15, 19, 6);
        addEdge(graph, 15, 16, 7);
        addEdge(graph, 16, 17, 8);
        addEdge(graph, 10, 16, 9);
        addEdge(graph, 5, 10, 3);
        addEdge(graph, 5, 11, 11);
        addEdge(graph, 11, 17, 10);
        addEdge(graph, 18, 0, 3);
        addEdge(graph, 17, 0, 7);
        addEdge(graph, 19, 0, 4);
        addEdge(graph, 3, 13, 10);
        addEdge(graph, 6, 13, 8);
        addEdge(graph, 3, 6, 4);
        addEdge(graph, 1, 6, 2);
        addEdge(graph, 1, 9, 9);
        addEdge(graph, 1, 8, 6);
        addEdge(graph, 3, 14, 7);
        addEdge(graph, 9, 14, 3);
        addEdge(graph, 9, 19, 4);
        addEdge(graph, 9, 16, 7);
        addEdge(graph, 10, 17, 10);
        addEdge(graph, 5, 17, 1);
        addEdge(graph, 11, 16, 3);
        addEdge(graph, 15, 0, 6);
        addEdge(graph, 16, 0, 16);
        addEdge(graph, 11, 0, 11);
        addEdge(graph, 12, 0, 4);
        addEdge(graph, 4, 18, 9);
        addEdge(graph, 6, 11, 12);
        addEdge(graph, 2, 12, 14);

        t.primMST(graph);
        System.out.println("\n******************\nAdding an edge that changes the MST:\n******************\n");
        t.q2(t.tree, 3, 4, 40000000, t.flag);
        System.out.println("\n******************\nAdding an edge that doesn't change the MST:\n******************\n");
        t.q2(t.tree, 3, 4, 1, t.flag);
    }

}



