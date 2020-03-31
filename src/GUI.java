import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.io.FileWriter;

public class GUI extends storyGenerator {
    // Variables for the content of the GUI as well as the list and flag
    private JButton buttonGenerate;
    private JPanel panelMain;
    public JTextArea textArea1;
    private JButton buttonSave;
    private storyGenerator storyGenerator = new storyGenerator();
    public List<String> list = new LinkedList<String>();
    private Boolean canSaveFlag = false;

    // Function to generate action listeners
    public GUI() {
        // Generate button actionListener
        buttonGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the text area so a new story can be displayed and throw a dialog to say a story has been generated (by running the generate function in the storyGenerator class)
                textArea1.setText(null);
                JOptionPane.showMessageDialog(null, "Story has been generated. You may need to resize the window to read it all.");
                storyGenerator.characterGeneration();
                // For each sentence in the list, display thm on a new line in the text area
                list = storyGenerator.getList();
                for (int i = 0; i < list.size(); i++) {
                    textArea1.append(list.get(i) + "\n");
                }
                // Allow the text area to be saved to a text file
                canSaveFlag = true;
            }
        });
        // Save button actionListener
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // If the text area is empty the flag will be false so check to see if the flag is true so we can save something
                if (canSaveFlag) {
                    try {
                        // Write the file by writing each sentence from the list
                        FileWriter writer = new FileWriter("storyOutput.txt");
                        for (int i = 0; i < list.size(); i++) {
                            writer.write(list.get(i) + "\n");
                        }
                        writer.close();
                        JOptionPane.showMessageDialog(null, "Story has been saved to 'storyOutput.txt' in the main folder for this program.");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                } else if (!canSaveFlag) {
                    // If a story hasn't been generated yet, ask the user to generate a story
                    JOptionPane.showMessageDialog(null, "You need to generate a story first!");
                }
            }
        });
    }

    // Accessor method for the text area
    public void setTextArea(String text) {

        textArea1.append(text);
    }

    // main method to setup the gui
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height * 1 / 3;
        int width = screenSize.width * 1 / 3;
        JFrame frame = new JFrame("StoryGen");

        frame.setPreferredSize(new Dimension(width, height));
        frame.setContentPane(new GUI().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
