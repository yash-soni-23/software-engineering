package com.yash;
import edu.princeton.cs.algs4.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Scanner;

public class DijkstraSP {
    private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private DirectedEdge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices


    public DijkstraSP(EdgeWeightedDigraph G, int s) {
        for (DirectedEdge e : G.edges()) {
            if (e.weight() < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight");
        }

        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];

        validateVertex(s);

        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;


        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge e : G.adj(v))
                relax(e);
        }


        assert check(G, s);
    }


    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else                pq.insert(w, distTo[w]);
        }
    }


    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }


    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }


    public Iterable<DirectedEdge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }



    private boolean check(EdgeWeightedDigraph G, int s) {


        for (DirectedEdge e : G.edges()) {
            if (e.weight() < 0) {
                System.err.println("negative edge weight detected");
                return false;
            }
        }


        if (distTo[s] != 0.0 || edgeTo[s] != null) {
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (int v = 0; v < G.V(); v++) {
            if (v == s) continue;
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                System.err.println("distTo[] and edgeTo[] inconsistent");
                return false;
            }
        }


        for (int v = 0; v < G.V(); v++) {
            for (DirectedEdge e : G.adj(v)) {
                int w = e.to();
                if (distTo[v] + e.weight() < distTo[w]) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }


        for (int w = 0; w < G.V(); w++) {
            if (edgeTo[w] == null) continue;
            DirectedEdge e = edgeTo[w];
            int v = e.from();
            if (w != e.to()) return false;
            if (distTo[v] + e.weight() != distTo[w]) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
    }


    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }


    public static void main(String[] args) {
        int i,s=0;
        int []a = new int [3] ;
        String input = null;
        Scanner sc = new Scanner(System.in) ;
        int l = sc.nextInt();
        if(l==0) {
            for (i = 0; i < 3; i++) {
                if (i == 0) {
                    System.out.println("graph 1 ");
                    input = "C:\\Users\\DELL\\IdeaProjects\\Graph_Project\\src\\com\\yash\\midsem1.txt";
                    s = 5 ;
                } else if (i == 1) {
                    System.out.println("graph 2 ");
                    input = "C:\\Users\\DELL\\IdeaProjects\\Graph_Project\\src\\com\\yash\\midsem2.txt";
                    s = 1 ;
                } else if (i == 2) {
                    System.out.println("graph 3 ");
                    input = "C:\\Users\\DELL\\IdeaProjects\\Graph_Project\\src\\com\\yash\\midsem3.txt";
                    s = 1 ;
                }

                In in = new In(input);

                EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

                DijkstraSP sp = new DijkstraSP(G, s);


                for (int t = 0; t < G.V(); t++) {
                    if (sp.hasPathTo(t)) {
                        StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
                        for (DirectedEdge e : sp.pathTo(t)) {
                            StdOut.print(e + "   ");
                        }
                        StdOut.println();
                    } else {
                      //  StdOut.printf("%d to %d         no path\n", s, t);
                    }
                }
            }
        }
        if(l==1) {
            for (i = 0; i < 3; i++) {
                if (i == 0) {
                    System.out.println("graph 1 ");
                    input = "C:\\Users\\DELL\\IdeaProjects\\Graph_Project\\src\\com\\yash\\deezereurope.txt";
                    s = 0 ;
                } else if (i == 1) {
                    System.out.println("graph 2 ");
                    input = "C:\\Users\\DELL\\IdeaProjects\\Graph_Project\\src\\com\\yash\\facebook.txt";
                    s = 0 ;
                } else if (i == 2) {
                    System.out.println("graph 3 ");
                    input = "C:\\Users\\DELL\\IdeaProjects\\Graph_Project\\src\\com\\yash\\resistance.txt";
                    s = 0 ;
                }

                In in = new In(input);

                EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

                DijkstraSP sp = new DijkstraSP(G, s);


                for (int t = 0; t < G.V(); t++) {
                    if (sp.hasPathTo(t)) {
                        StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
                        for (DirectedEdge e : sp.pathTo(t)) {
                            StdOut.print(e + "   ");
                        }
                        StdOut.println();
                    } else {
                        //StdOut.printf("%d to %d   no path\n", s, t);
                    }
                }
            }
        }
    }
}
