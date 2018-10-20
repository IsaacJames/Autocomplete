import java.util.Arrays;

public class Autocomplete {

    private Term[] terms;

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) throws NullPointerException {
        this.terms = terms;
        // sorting a given array referring to a single function, compareTo
        Arrays.sort(terms, Term::compareTo);
    }

    // Returns all terms that start with the given
    // prefix, in descending order of weight.
    public Term[] allMatches(String prefix) throws NullPointerException {
        // converting inputted prefix to a Term object
        Term key = new Term(prefix, 0);
        // finding the boundaries of matches in a sorted array
        int first = BinarySearchDeluxe.firstIndexOf(terms, key, Term.byPrefixOrder(prefix.length()));
        int last = BinarySearchDeluxe.lastIndexOf(terms, key, Term.byPrefixOrder(prefix.length()));
        // creating an array of terms with a given prefix
        Term[] sortedTerms = {};
        // in case there are no matches
        if (first == -1) {
            return sortedTerms;
        }
        // adding matches to a new array
        sortedTerms = Arrays.copyOfRange(terms, first, last + 1);
        // sorting matches by weights
        Arrays.sort(sortedTerms, Term.byReverseWeightOrder());
        return sortedTerms;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) throws NullPointerException {
        return allMatches(prefix).length;
    }
}
