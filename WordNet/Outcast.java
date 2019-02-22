import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
// An immutable data type for outcast detection.
public class Outcast {
    private WordNet wordnet;
    // Construct an Outcast object given a WordNet object.
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }
    // The outcast noun from nouns.
    public String outcast(String[] nouns) {
        int outcastDistance = 0;
        int max = 0;
        String outcast = nouns[0];
        
        for (int i = 0; i < nouns.length; i++) {
            outcastDistance = outcastDistance(nouns[i], nouns);
            if (outcastDistance > max) {
                max = outcastDistance;
                outcast = nouns[i];
            }
        }
        return outcast;
    }
    
    private int outcastDistance(String noun, String[] nouns) {
        int distance = 0;
        for (int i = 0; i < nouns.length; i++) {
            distance += wordnet.distance(noun, nouns[i]);
        }
        return distance;
    }
    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println("outcast(" + args[t] + ") = "
                           + outcast.outcast(nouns));
        }
    }
}
