import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;

// An immutable WordNet data type.
public class WordNet {
    private RedBlackBST<String, SET<Integer>> st;
    private RedBlackBST<Integer, String> rst;  //ID index
    private RedBlackBST<String, Integer> rbtree;
    private ShortestCommonAncestor sca;
    private Digraph G;
    
    // Construct a WordNet object given the names of the input (synset and
    // hypernym) files.
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new NullPointerException();
        }
        
        st = new RedBlackBST<String, SET<Integer>>();
        rst = new RedBlackBST<Integer, String>();
        rbtree = new RedBlackBST<String, Integer>();
        In in = new In(synsets);
        int temp = 0;
        while (!in.isEmpty()) {
            String[] a = in.readLine().split(",");
            int x = Integer.parseInt(a[0]);
            String[] nouns = a[1].split(" ");
            for (String noun : nouns) {
                if (in == null) {
                    break;
                }
                if(!st.contains(noun)) {
                    //SET<Integer> set = st.get(noun);
                    st.put(noun, new SET<Integer>());
                }
                st.get(noun).add(x);
            }
            //x++;
            rst.put(x, a[1]);
            temp = x;
        }
        G = new Digraph(temp + 1);
        In hyper = new In(hypernyms);
        while (!hyper.isEmpty()) {
            String[] a = hyper.readLine().split(",");
            int v = Integer.parseInt(a[0]);
            int[] tem = new int[a.length];
            for (int i = 1; i < a.length; i++) {
                tem[i] = Integer.parseInt(a[i]);
                G.addEdge(v, tem[i]);
            }
        }
        sca = new ShortestCommonAncestor(G);
    }
    // All WordNet nouns.
    public Iterable<String> nouns() {
        return st.keys();
    }
    // Is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new NullPointerException();
        }
        return st.contains(word);
    }
    // A synset that is a shortest common ancestor of noun1 and noun2.
    public String sca(String noun1, String noun2) {
       if (noun1 == null || noun2 == null) {
           throw new NullPointerException();
       }
       SET<Integer> x = st.get(noun1);
       SET<Integer> y = st.get(noun2);
       int a = sca.ancestor(x, y);
       return rst.get(a);
    }
    // Distance between noun1 and noun2.
    public int distance(String noun1, String noun2) {
        if (noun1 == null || noun2 == null) {
            throw new IllegalArgumentException();
        }
        if (!isNoun(noun1) || !isNoun(noun2)) {
            throw new IllegalArgumentException();
        }
        SET<Integer> x = st.get(noun1);
        SET<Integer> y = st.get(noun2);
        if (x.size() == 1 && y.size() == 1) {
            return sca.length(x.max(), y.max());
        } else {
            return sca.length(x, y);
        }
    }
    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        String word1 = args[2];
        String word2 = args[3];        
        int nouns = 0;
        for (String noun : wordnet.nouns()) {
            nouns++;
        }
        StdOut.println("# of nouns = " + nouns);
        StdOut.println("isNoun(" + word1 + ") = " + wordnet.isNoun(word1));
        StdOut.println("isNoun(" + word2 + ") = " + wordnet.isNoun(word2));
        StdOut.println("isNoun(" + (word1 + " " + word2) + ") = "
                       + wordnet.isNoun(word1 + " " + word2));
        StdOut.println("sca(" + word1 + ", " + word2 + ") = "
                       + wordnet.sca(word1, word2));
        StdOut.println("distance(" + word1 + ", " + word2 + ") = "
                       + wordnet.distance(word1, word2));
    }
}
