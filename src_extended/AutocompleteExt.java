import java.util.Arrays;

public class AutocompleteExt {

    private TermExt[] terms;

    // Initializes the data structure from the given array of terms.
    public AutocompleteExt(TermExt[] terms) throws NullPointerException {
        this.terms = terms;
        // sorting a given array referring to a single function, compareTo
        Arrays.sort(terms, TermExt::compareTo);
    }

    // Returns all terms that start with the given
    // prefix, in descending order of weight.
    public TermExt[] allMatches(String prefix) throws NullPointerException {
        // converting inputted prefix to a Term object
        TermExt key = new TermExt(prefix, 0);
        // finding the boundaries of matches in a sorted array
        int first = BinarySearchDeluxeExt.firstIndexOf(terms, key, TermExt.byPrefixOrder(prefix.length()));
        int last = BinarySearchDeluxeExt.lastIndexOf(terms, key, TermExt.byPrefixOrder(prefix.length()));
        // creating an array of terms with a given prefix
        TermExt[] sortedTerms = {};
        // in case there are no matches
        if (first == -1) {
            return sortedTerms;
        }
        // adding matches to a new array
        sortedTerms = Arrays.copyOfRange(terms, first, last + 1);
        // sorting matches by weights
        Arrays.sort(sortedTerms, TermExt.byReverseWeightOrder());
        return sortedTerms;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) throws NullPointerException {
        return allMatches(prefix).length;
    }
}
