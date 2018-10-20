import java.util.Comparator;

// Term API
public class Term implements Comparable<Term> {
    String query;
    long weight;

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query == null) {
            throw new NullPointerException();
        }
        this.query = query;
        if (weight < 0) {
            throw new IllegalArgumentException();
        }
        this.weight = weight;
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return (a, b) -> (int) (b.weight - a.weight);
    }

    // Compares the two terms in lexicographic order
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0) {
            throw new IllegalArgumentException();
        }
        return (prefix, candidate) -> {
            int candidateLength = candidate.query.length();
            int shortened;
            // if given prefix is longer than the word we are comparing it to, compare it to a shortened version
            // of that word
            if (r > candidateLength) {
                shortened = candidateLength;
            } else shortened = r;
            // compare two given lower cases words and return the result
            return prefix.query.toLowerCase().compareTo(candidate.query.toLowerCase().substring(0, shortened));
        };
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return this.query.toLowerCase().compareTo(that.query.toLowerCase());
    }

    // Returns a string representation of this term in the format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return Long.toString(this.weight) + "\t" + this.query;
    }
}
