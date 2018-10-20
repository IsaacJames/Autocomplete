import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.EventQueue;

public class Project1 {

    static Autocomplete autocomplete;
    static int noOfArguments;

    public static void main(String[] args) {

        try {
            // checking if correct number of arguments is inputted
            if (args.length != 2) {
                System.out.println("Usage: java Project1 <input file> <integer number of results displayed>");
            }
            // getting command line arguments
            autocomplete = new Autocomplete(createTerms(args[0]));
            noOfArguments = Integer.parseInt(args[1]);

            // checking if the inputted integer is positive
            if (noOfArguments <= 0) {
                System.out.println("Usage: java Project1 <input file> <integer number of results displayed>");
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            return;
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
            return;
        } catch (NumberFormatException e) { // checking if the inputted number is an integer
            System.out.println(e.getMessage() + " could not parse as integer");
            System.out.println("Usage: java Project1 <input file> <integer number of results displayed>");
            return;
        }

        // invoking GUI
        EventQueue.invokeLater(() -> {
            GUI ex = null;
            try {
                ex = new GUI();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ex.setVisible(true);
        });
    }

    public static Term[] createTerms(String filepath) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            ArrayList<Term> terms = new ArrayList<>();
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
                Term newTerm = new Term(query, weight);
                terms.add(newTerm);
            }
            reader.close();
            // arraylist is converted to an array of terms
            Term[] termsInArray = new Term[terms.size()];
            terms.toArray(termsInArray);
            return termsInArray;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
