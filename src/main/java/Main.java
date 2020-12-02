import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Main implements ActionListener {
    private JTextField inputName;
    private JPanel panel1;
    private JButton buttonPlay;
    private JButton buttonImage;
    private JLabel labelNama;
    private JTextField inputImage;
    private JTable tableLeaderBoard;
    private JLabel labelLeaderBoard;
    private File image;

    public Main(){
        buttonImage.addActionListener(this);
        buttonPlay.addActionListener(this);
        LeaderBoard leaderBoard = new LeaderBoard();
        tableLeaderBoard.setModel(new ModelLeaderBoard(leaderBoard));
    }

    private static void setUI() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        setUI();

        JFrame frame = new JFrame("Main Menu");
        frame.setContentPane(new Main().panel1);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buttonImage){
            JFileChooser fc = new JFileChooser();
            fc.setAcceptAllFileFilterUsed(false);
            fc.addChoosableFileFilter(new JPGFilter());

            if(fc.showOpenDialog(panel1) == JFileChooser.APPROVE_OPTION){
                inputImage.setText(fc.getSelectedFile().getName());
                image = fc.getSelectedFile();
            }
        }
        else if(e.getSource() == buttonPlay){
            if(inputName.getText().isEmpty()){
                JOptionPane.showMessageDialog(panel1, "Nama harus diisi!");
            }
            else if(inputImage.getText().isEmpty()){
                JOptionPane.showMessageDialog(panel1, "Pilih gambar terlebih dahulu!");
            }
            else{

            }
        }
    }
}
