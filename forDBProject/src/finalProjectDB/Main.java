package finalProjectDB;

import javax.swing.*;

public class Main {

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                createGUI();
            }
        });
    }


    private static void createGUI() {
        mainUI ui = new mainUI();
        JPanel sc = ui.getMainPanel();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(sc);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}