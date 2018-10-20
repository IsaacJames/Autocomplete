import java.util.Comparator;

// Search API
public class BinarySearchDeluxe {
    // Returns the index of the first key in a[] that
    //equals the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) throws NullPointerException {
        // boundaries of binary search
        int low = 0;
        int high = a.length - 1;
        // index of the first match, set to -1 in case there are no matches
        int index = -1;
        int comparingToMiddle;

        while (low <= high) {
            int middle = Math.floorDiv(low + high, 2);
            comparingToMiddle = comparator.compare(key, a[middle]);

            if (comparingToMiddle > 0) {
                low = middle + 1;
            } else if (comparingToMiddle < 0) {
                high = middle - 1;
            }

            // if the key is in the middle, set index to found match and change the upper boundary of the binary
            // search to "found index - 1" to make sure there are no earlier recurrences
            else {
                index = middle;
                high = middle - 1;
            }
        }
        return index;
    }

    // Returns the index of the last key in a[] that
    //equals the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) throws NullPointerException {
        // boundaries of binary search
        int low = 0;
        int high = a.length - 1;
        // index of the first match, set to -1 in case there are no matches
        int index = -1;
        int comparingToMiddle;

        while (low <= high) {
            int middle = Math.floorDiv(low + high, 2);
            comparingToMiddle = comparator.compare(key, a[middle]);

            if (comparingToMiddle < 0) {
                high = middle - 1;
            } else if (comparingToMiddle > 0) {
                low = middle + 1;
            }

            // if the key is in the middle, set index to found match and change the lower boundary of the binary
            // search to "found index + 1" to make sure there are no later recurrences
            else {
                index = middle;
                low = middle + 1;
            }
        }
        return index;
    }
}