package com.yash;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class TriangleCountingT {
    private int triangleCount;  // number of triangles
    private boolean[] marked;    // marked[v] = has vertex v been visited?
    private int[] count;         // count[v] = number of triangles containing v

    public TriangleCountingT(int[][] edges, int V) {
        triangleCount = 0;
        marked = new boolean[V];
        count = new int[V];

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        for (int v = 0; v < V; v++) {
            dfs(adj, v, v, 0, new ArrayList<>());
            marked[v] = true; // Mark the current vertex as visited
        }
    }

    private void dfs(List<List<Integer>> adj, int source, int v, int depth, List<Integer> path) {
        marked[v] = true;
        path.add(v);

        if (depth == 2) {
            for (int w : adj.get(v)) {
                if (w == source) {
                    triangleCount++;
                    for (int vertex : path) {
                        count[vertex]++;
                    }
                    break;
                }
            }
        } else {
            for (int w : adj.get(v)) {
                if (!marked[w]) {
                    dfs(adj, source, w, depth + 1, path);
                }
            }
        }

        marked[v] = false;
        path.remove(path.size() - 1);
    }

    public int getTriangleCount() {
        return triangleCount / 6;  // Each triangle is counted 6 times (3! permutations)
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int l = sc.nextInt();
        String input;
        if (l == 1) {
            for (int i = 0; i < 3; i++) {
                if (i == 0) {
                    System.out.println("Graph 1:");
                    input = "C:\\Users\\DELL\\IdeaProjects\\Graph_Project\\src\\com\\yash\\midsem1.txt";
                } else if (i == 1) {
                    System.out.println("Graph 2:");
                    input = "C:\\Users\\DELL\\IdeaProjects\\Graph_Project\\src\\com\\yash\\midsem2.txt";
                } else if (i == 2) {
                    System.out.println("Graph 3:");
                    input = "C:\\Users\\DELL\\IdeaProjects\\Graph_Project\\src\\com\\yash\\midsem3.txt";
                } else {
                    continue;
                }

                In in = new In(input);
                int V = in.readInt();
                int E = in.readInt();
                int[][] edges = new int[E][2];
                for (int j = 0; j < E; j++) {
                    edges[j][0] = in.readInt();
                    edges[j][1] = in.readInt();
                }

                TriangleCountingT tc = new TriangleCountingT(edges, V);
                StdOut.println("Number of triangles in Graph " + (i+1) + ": " + tc.getTriangleCount());
            }
        } else if (l == 2) {
            for (int i = 0; i < 3; i++) {
                if (i == 0) {
                    System.out.println("Graph 1:");
                    input = "C:\\Users\\DELL\\IdeaProjects\\Graph_Project\\src\\com\\yash\\deezereurope.txt";
                } else if (i == 1) {
                    System.out.println("Graph 2:");
                    input = "C:\\Users\\DELL\\IdeaProjects\\Graph_Project\\src\\com\\yash\\facebook.txt";
                } else if (i == 2) {
                    System.out.println("Graph 3:");
                    input = "C:\\Users\\DELL\\IdeaProjects\\Graph_Project\\src\\com\\yash\\resistance.txt";
                } else {
                    continue;
                }

                In in = new In(input);
                int V = in.readInt();
                int E = in.readInt();
                int[][] edges = new int[E][2];
                for (int j = 0; j < E; j++) {
                    edges[j][0] = in.readInt();
                    edges[j][1] = in.readInt();
                }

                TriangleCountingT tc = new TriangleCountingT(edges, V);
                StdOut.println("Number of triangles in Graph " + (i+1) + ": " + tc.getTriangleCount());
            }
        }
    }
}
