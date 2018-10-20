import java.awt.Container;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.io.IOException;

public class GUI extends JFrame {

    private JTextField field;
    private JList<String> list;
    private DefaultListModel<String> model;

    public GUI() throws IOException {
        initUI();
    }

    private void initUI() {
        field = new JTextField(15);
        list = new JList<>();
        model = new DefaultListModel<>();
        list.setModel(model);

        field.getDocument().addDocumentListener(new MyDocumentListener());

        createLayout(field, list);
        setTitle("Autocomplete");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private class MyDocumentListener implements DocumentListener {

        private String prefix;
        private Term[] matches;

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
            Document doc = e.getDocument();  // Get Document that triggered event
            int len = doc.getLength();  // Get number of characters of content in Document

            try {
                model.clear();
                prefix = doc.getText(0, len);  // Get user text input

                // Do not perform search if user input = "" (i.e. text box has been cleared)
                if (prefix.length() > 0) {
                    // INVALID PREFIX EXTENSION - iterate through substrings of prefix until match is found
                    matches = Project1.autocomplete.allMatches(prefix);

                    // Add match queries to list model
                    for (int i = 0; i < Project1.noOfArguments && i < matches.length; i++) {
                        model.addElement(matches[i].query);
                    }
                } else {
                    model.clear();
                }

                // Thrown when model location is accessed that does not exist
            } catch (BadLocationException ex) {
                Logger.getLogger(Project1.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
    }

    private void createLayout(JComponent... arg) {
        Container pane = getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);

        gl.setHorizontalGroup(gl.createParallelGroup()
                .addComponent(arg[0])  // Add search box
                .addComponent(arg[1])  // add list
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addComponent(arg[0], GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(arg[1])
        );
        setSize(500, 150 + 20 * Project1.noOfArguments);  // Scale window size according to max length of list
    }
}
