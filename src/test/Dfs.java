//package test;
//
//public class Dfs {
//    int[] visited;
//
//    void dfs(int start, int[] visited) {
//
//        // Print the current node
//        cout << start << " ";
//
//        // Set current node as visited
//        visited[start] = true;
//
//        // For every node of the graph
//        for (int i = 0; i < adj[start].size(); i++) {
//
//            // If some node is adjacent to the current node
//            // and it has not already been visited
//            if (adj[start][i] == 1 && (!visited[i])) {
//                dfs(i, visited);
//            }
//        }
//    }