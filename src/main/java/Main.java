import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Main extends JFrame implements ActionListener {

    private JTextField inputName;
    private JPanel panel1;
    private JButton buttonPlay;
    private JButton buttonImage;
    private JLabel labelNama;
    private JTextField inputImage;
    private JTable tableLeaderBoard;
    private JLabel labelLeaderBoard;
    private File image;

    /**
     * Konstruktor
     */
    public Main(){
        super("Main Menu");

        /* Setting UI to windows look and feel */
        setUI();

        /* Activate action listener */
        buttonImage.addActionListener(this);
        buttonPlay.addActionListener(this);

        /* Activate leaderboard */
        LeaderBoard leaderBoard = new LeaderBoard();
        /* Setting leaderboard tabel model */
        tableLeaderBoard.setModel(new ModelLeaderBoard(leaderBoard));

        /* Setting frame */
        setLocation(300,200);
        setContentPane(panel1);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Setting UI to windows look and feel
     */
    private void setUI() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Action performed
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        /* Jika user klik pilih gambar */
        if(e.getSource() == buttonImage){
            /* Inisialisasi JFileChooser */
            JFileChooser fc = new JFileChooser();
            /* Mengatur filter file */
            fc.setAcceptAllFileFilterUsed(false);
            fc.addChoosableFileFilter(new JPGFilter());

            /* Jika file diterima */
            if(fc.showOpenDialog(panel1) == JFileChooser.APPROVE_OPTION){
                /* Atur nama file ke input text readonly */
                inputImage.setText(fc.getSelectedFile().getName());
                /* Simpan file yang dipilih ke variabel image */
                image = fc.getSelectedFile();
            }
        }
        /* Jika user klik mulai */
        else if(e.getSource() == buttonPlay){
            /* Jika nama masih kosong */
            if(inputName.getText().isEmpty()){
                JOptionPane.showMessageDialog(panel1, "Nama harus diisi!");
            }
            /* Jika gambar masih kosong */
            else if(inputImage.getText().isEmpty()){
                JOptionPane.showMessageDialog(panel1, "Pilih gambar terlebih dahulu!");
            }
            else{
                /* Mulai game */
                setVisible(false);
                GamePlay gamePlay = new GamePlay(inputName.getText(), image);
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
    }
}
