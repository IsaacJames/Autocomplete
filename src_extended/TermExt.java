import java.text.Normalizer;
import java.util.Comparator;

// Term API
public class TermExt implements Comparable<TermExt> {
    String query;
    long weight;
    private String queryNorm;

    // Initializes a term with the given query string and weight.
    public TermExt(String query, long weight) {
        if (query == null) {
            throw new NullPointerException();
        }
        this.query = query;
        // adding a normalised version of the word which uses only ASCII symbols
        this.queryNorm = Normalizer.normalize(query, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        if (weight < 0) {
            throw new IllegalArgumentException();
        }
        this.weight = weight;
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<TermExt> byReverseWeightOrder() {
        return (a, b) -> (int) (b.weight - a.weight);
    }

    // Compares the two terms in lexicographic order
    // but using only the first r characters of each query.
    public static Comparator<TermExt> byPrefixOrder(int r) {
        if (r < 0) {
            throw new IllegalArgumentException();
        }
        return (prefix, candidate) -> {
            int candidateLength = candidate.queryNorm.length();
            int shortened;
            // if given prefix is longer than the word we are comparing it to, compare it to a shortened version
            // of that word
            if (r > candidateLength) {
                shortened = candidateLength;
            } else shortened = r;
            // compare two given normalised, lower cases words and return the result
            return prefix.queryNorm.toLowerCase().compareTo(candidate.queryNorm.toLowerCase().substring(0, shortened));
        };
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(TermExt that) {
        return this.queryNorm.toLowerCase().compareTo(that.queryNorm.toLowerCase());
    }

    // Returns a string representation of this term in the format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return Long.toString(this.weight) + "\t" + this.query;
    }
}