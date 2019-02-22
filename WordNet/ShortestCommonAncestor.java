import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
// An immutable data type for computing shortest common ancestors.
public class ShortestCommonAncestor {
    private Digraph G;
    // Construct a ShortestCommonAncestor object given a rooted DAG.
    public ShortestCommonAncestor(Digraph G) {
        this.G = new Digraph(G);
    }
    // Length of the shortest ancestral path between v and w.
    public int length(int v, int w) {
        int ancester = ancestor(v, w);
        return distFrom(v).get(ancester) + distFrom(w).get(ancester);
    }
    // Shortest common ancestor of vertices v and w.
    public int ancestor(int v, int w) {
        SeparateChainingHashST<Integer, Integer> vDist = distFrom(v);
        SeparateChainingHashST<Integer, Integer> wDist = distFrom(w);
        int minDistance = Integer.MAX_VALUE;
        int minAncestor = -1;
        for (int x : wDist.keys()) {
            if (vDist.contains(x)) {
                int distance = vDist.get(x) + wDist.get(x);
                if (distance < minDistance) {
                    minDistance = distance;
                    minAncestor = x;
                }
            }
        }
        return minAncestor;
    }
    // Length of the shortest ancestral path of vertex subsets A and B.
    public int length(Iterable<Integer> A, Iterable<Integer> B) {
       int[] thing = triad(A, B);
       int v = thing[1];
       int w = thing[2];
       SeparateChainingHashST<Integer, Integer> vDist = distFrom(v);
       SeparateChainingHashST<Integer, Integer> wDist = distFrom(w);
       int total = vDist.get(thing[0]) + wDist.get(thing[0]);
       return total;
    }
    // A shortest common ancestor of vertex subsets A and B.
    public int ancestor(Iterable<Integer> A, Iterable<Integer> B) {
        int[] x = triad(A, B);
        return x[0];
    }
    // Helper: Return a map of vertices reachable from v and their 
    // respective shortest distances from v.
    private SeparateChainingHashST<Integer, Integer> distFrom(int v) {
        SeparateChainingHashST<Integer, Integer> st = 
            new SeparateChainingHashST<Integer, Integer>();
        LinkedQueue<Integer> queue = new LinkedQueue<Integer>();
        st.put(v, 0);
        queue.enqueue(v);
        while (!queue.isEmpty()) {
            int num = queue.dequeue();
            for (int x : G.adj(num)) {
                if (!st.contains(x)) {
                    st.put(x, st.get(num) + 1);
                    queue.enqueue(x);
                }
            }
        }
        return st;
    }
    // Helper: Return an array consisting of a shortest common ancestor a 
    // of vertex subsets A and B, and vertex v from A and vertex w from B 
    // such that the path v-a-w is the shortest ancestral path of A and B.
    private int[] triad(Iterable<Integer> A, Iterable<Integer> B) {
        int minDistance = Integer.MAX_VALUE;
        int minAncestor = -1;
        int v = -1;
        int w = -1;
        for (int a : A) {
            for (int b: B) {
                int distance = length(a, b);
                if (distance < minDistance) {
                    minDistance = distance;
                    minAncestor = ancestor(a, b);
                    v = a; 
                    w = b;
                }
            }
        }
        return new int[]{minAncestor, v, w};
    }
    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
