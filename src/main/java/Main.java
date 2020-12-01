import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private JTextField inputName;
    private JPanel panel1;
    private JButton buttonPlay;
    private JButton buttonImage;
    private JLabel labelNama;

    public Main(){
        buttonImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.showOpenDialog(panel1);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main Menu");
        frame.setContentPane(new Main().panel1);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
