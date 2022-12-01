package fi.tuni.trafficweatherapp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import javax.swing.*;


/**
 * THIS CLASS IS FROM PREVIOUS PROJECTS
 *
 * JWindow displayed when the program fetches data
 *
 * @author Aleksi Hasu
 */

public class LoadingScreen extends JWindow {

    // Main label to be displayed and updated
    JLabel shownText = new JLabel("Fetching Data...", JLabel.CENTER);




    public LoadingScreen() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // set look and feel
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }
                showScreen();
            }
        });
    }

    /**
     * Create a new JPanel and display it
     */
    public void showScreen() {

        // root panel
        JPanel content = (JPanel) getContentPane();

        // Set the window's bounds, centering the window
        int width = 300;
        int height = 35;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        // Create loading screen label
        JLabel label = new JLabel();
        shownText = new JLabel("Fetching Data...", JLabel.CENTER);
        content.add(label, BorderLayout.CENTER);
        label.setLayout(new GridBagLayout());

        // Set font
        Font font = shownText.getFont();
        shownText.setFont(font.deriveFont(Font.BOLD, 28f));

        // Get vertical position
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        label.add(shownText,gbc);

        // Display it
        setVisible(true);
        toFront();
        new ResourceLoader().execute();
    }

    // SwingWorker class to update the text
    public class ResourceLoader extends SwingWorker<Object, Object> {

        @Override
        protected Object doInBackground() throws Exception {

            // update text while screen is visible
            try {
                int i = 0;
                while(isVisible()){
                    shownText.setText("Fetching Data"+".".repeat(i));
                    ++i;
                    if (i > 3){
                        i = 0;
                    }
                    Thread.sleep(500);
                }
            } catch (Exception e) {
            }
            return null;

        }

        @Override
        protected void done() {
            setVisible(false);
        }


    }

}

