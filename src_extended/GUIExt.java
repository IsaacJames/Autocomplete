import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.io.IOException;

public class GUIExt extends JFrame {

    private JTextField field;
    private JList<String> list;
    private DefaultListModel<String> model;
    private JLabel listLabel;
    private JLabel didYouMeanLabel;
    private JLabel resultLabel;

    // Fields to track execution time
    private JLabel searchStats;
    private long startTime;
    private long endTime;

    public GUIExt() throws IOException {
        initUI();
    }

    private void initUI() {
        field = new JTextField(15);
        list = new JList<>();
        model = new DefaultListModel<>();
        listLabel = new JLabel();
        didYouMeanLabel = new JLabel();
        resultLabel = new JLabel();
        searchStats = new JLabel();
        searchStats.setFont(new Font("", Font.PLAIN, 10));
        list.setModel(model);

        field.getDocument().addDocumentListener(new MyDocumentListener());

        createLayout(field, didYouMeanLabel, list, listLabel, resultLabel, searchStats);
        setTitle("Autocomplete");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private class MyDocumentListener implements DocumentListener {

        private String prefix;
        private TermExt[] matches;

        @Override
        public void insertUpdate(DocumentEvent e) {
            updateLabel(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateLabel(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }

        private void updateLabel(DocumentEvent e) {
            startTime = System.nanoTime();
            Document doc = e.getDocument();  // Get Document that triggered event
            int len = doc.getLength();  // Get number of characters of content in Document

            try {
                model.clear();
                didYouMeanLabel.setText("");
                searchStats.setText("");
                resultLabel.setText("");
                prefix = doc.getText(0, len);  // Get user text input
                boolean notFound = false;
                int noOfMatches = 0;

                // Do not perform search if user input = "" (i.e. text box has been cleared)
                if (prefix.length() > 0) {
                    // INVALID PREFIX EXTENSION - iterate through substrings of prefix until match is found
                    while ((matches = Project1Ext.autocomplete.allMatches(prefix)).length < 1) {
                        notFound = true;
                        prefix = prefix.substring(0, prefix.length() - 1);
                    }
                    noOfMatches = matches.length;
                    if (notFound) {
                        noOfMatches = 0;
                        didYouMeanLabel.setText("Did you mean " + matches[0].query + "?");  // Inform user of new search due to invalid prefix
                    }
                    // Add match queries to list model
                    for (int i = 0; i < Project1Ext.noOfArguments && i < matches.length; i++) {
                        model.addElement(matches[i].query);
                    }
                } else {
                    model.clear();
                }

                endTime = System.nanoTime();
                // Display information about time to get matches
                searchStats.setText("Number of matches: " + noOfMatches + " (" + Double.toString((endTime - startTime) / 1000000d) + " ms)");

                // Thrown when model location is accessed that does not exist
            } catch (BadLocationException ex) {
                Logger.getLogger(Project1Ext.class.getName()).log(
                        Level.SEVERE, null, ex);
            }

            list.addMouseListener(new MouseAdapter() {
                String query = "";
                Long weight = 0l;

                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO: DELETE?
                    super.mouseClicked(e);
                    if (e.getClickCount() == 2) {  // Check for double-click
                        if (resultLabel.getText().equals("")) {
                            query = matches[list.getSelectedIndex()].query;
                            weight = matches[list.getSelectedIndex()].weight;
                            if (query.contains(",")) {
                                resultLabel.setText("City: " + query + "    Population: " + weight);
                            } else {
                                resultLabel.setText("Word: " + query + "    Frequency: " + weight);
                            }
                            model.clear();
                            searchStats.setText("");
                            didYouMeanLabel.setText("");
                        }
                    }
                }
            });
        }
    }

    private void createLayout(JComponent... arg) {
        Container pane = getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);

        gl.setHorizontalGroup(gl.createParallelGroup()
                .addComponent(arg[5])  // Add search stats
                .addComponent(arg[0])  // Add search box
                .addComponent(arg[4])  // Add result label
                .addComponent(arg[1])  // Add "did you mean?" label
                .addComponent(arg[2])  // add list
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addComponent(arg[5])
                .addComponent(arg[0], GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(arg[4])
                .addComponent(arg[1])
                .addComponent(arg[2])
        );
        setSize(500, 150 + 20 * Project1Ext.noOfArguments);  // Scale window size according to max length of list
    }
}
