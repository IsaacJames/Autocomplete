import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.EventQueue;

public class Project1Ext {

    static AutocompleteExt autocomplete;
    static int noOfArguments;

    public static void main(String[] args) {
        try {
            // checking if correct number of arguments is inputted
            if (args.length != 2) {
                System.out.println("Usage: java Project1 <input file> <integer number of results displayed>");
            }
            // getting command line arguments
            autocomplete = new AutocompleteExt(createTerms(args[0]));
            noOfArguments = Integer.parseInt(args[1]);

            // checking if the inputted integer is positive
            if (noOfArguments <= 0) {
                System.out.println("Usage: java Project1 <input file> <integer number of results displayed>");
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            return;
        } catch (IOException e) {
            System.out.println("IO Exception " + e.getMessage());
            return;
        } catch (NumberFormatException e) { // checking if the inputted number is an integer
            System.out.println(e.getMessage() + " could not parse as integer");
            System.out.println("Usage: java Project1 <input file> <integer number of results displayed>");
            return;
        }

        // invoking GUI
        EventQueue.invokeLater(() -> {
            GUIExt ex = null;
            try {
                ex = new GUIExt();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ex.setVisible(true);
        });
    }

    // creating terms from a given input file
    public static TermExt[] createTerms(String filepath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        String line;
        ArrayList<TermExt> terms = new ArrayList<>();
        // first line is a header/number of lines in the file
        reader.readLine();

        // Read in data file
        while (((line = reader.readLine()) != null)) {
            String[] splitLine;
            // the line needs to be trimmed differently
            if (line.contains(",")) {
                splitLine = line.trim().split("\\t");
            } else {
                splitLine = line.trim().split("\\s");
            }
            long weight = Long.parseLong(splitLine[0]);
            String query = splitLine[1];
            // creating new Term object and adding it to arraylist
            TermExt newTerm = new TermExt(query, weight);
            terms.add(newTerm);
        }
        reader.close();
        // arraylist is converted to an array of terms
        TermExt[] termsInArray = new TermExt[terms.size()];
        terms.toArray(termsInArray);
        return termsInArray;
    }
}
