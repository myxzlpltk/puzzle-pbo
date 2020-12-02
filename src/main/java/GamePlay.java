import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Timer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GamePlay extends JFrame implements ActionListener {

    private JPanel panel1;
    private JLabel labelName;
    private JLabel labelTime;
    private JPanel panelPuzzle;
    private BufferedImage source;

    private JButton button;
    private JButton whiteButton;
    private Image image;
    private int pos[][] = {
        {0, 1, 2},
        {3, 4, 5},
        {6, 7, 8},
    };
    private ArrayList<Position> state = new ArrayList<>();
    private ArrayList<Position> solved = new ArrayList<>();
    private int seconds = 0;
    private String name;

    public GamePlay(String name, File file){
        super("Main Menu");

        /* Setting layout puzzle */
        panelPuzzle.setLayout(new GridLayout(3, 3, 0, 0));

        initializePuzzle(file);
        randomPuzzle();
        drawPuzzle();

        /* Name */
        this.name = name;
        labelName.setText(name);

        /* Timer */
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seconds++;
                labelTime.setText(
                    String.format("%2s:%2s", seconds/60, seconds%60).replace(' ', '0')
                );
            }
        }, 0, 1000);

        /* Setting frame */
        setContentPane(this.panel1);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializePuzzle(File file){
        try {
            /* Read file */
            source = ImageIO.read(file);

            /* Get width and height */
            int width = source.getWidth();
            int height = source.getHeight();
            /* Get dimension */
            int dim = Math.min(width, height);

            /* Crop center and resize to 300px * 300px */
            source = source.getSubimage((width-dim)/2,(height-dim)/2,dim,dim);
            source = resize(source,300,300);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* Load position array */
        int index = 1;
        for ( int i = 0; i < 3; i++) {
            for ( int j = 0; j < 3; j++) {
                Position position = new Position(i,j,index++);
                state.add(position);
                solved.add(position);
            }
        }
    }

    /* Random Puzzle */
    private void randomPuzzle() {
        /* Selama puzzle unsolvable, acak! */
        do{
            Collections.shuffle(state);
        } while (!isSolvable(state));
    }

    /* Inject cell to panel */
    private void drawPuzzle() {
        int width = source.getWidth();
        int height = source.getHeight();
        for (Position position: state){
            if (position.j == 2 && position.i == 2) {
                whiteButton = new JButton();
                whiteButton.setBackground(Color.WHITE);
                panelPuzzle.add(whiteButton);
            }
            else {
                button = new JButton();
                button.setMargin(new Insets(0,0,0,0));
                button.setBorder(BorderFactory.createEmptyBorder());
                button.addActionListener(this);
                panelPuzzle.add(button);
                image = createImage(new FilteredImageSource(
                    source.getSource(),
                    new CropImageFilter(
                        position.j*width/3, position.i*height/3,
                        (width/3)+1, height/3)
                    )
                );
                button.setIcon(new ImageIcon(image));
            }
        }
    }

    /**
     * Mengecek apakah puzzle dapat diselesaikan
     * @param state list posisi
     * @return boolean
     */
    private boolean isSolvable(ArrayList<Position> state) {
        /* Clone */
        ArrayList<Position> newState = (ArrayList<Position>) state.clone();
        newState = (ArrayList<Position>) newState.stream().filter(new Predicate<Position>() {
            @Override
            public boolean test(Position position) {
                return position.index != 9;
            }
        }).collect(Collectors.toList());

        /* Set inversion ke 0 */
        int inversion = 0;
        /* Looping */
        for (int i=0; i<newState.size(); i++){
            for (int j=i+1; j<newState.size(); j++){
                if(newState.get(j).index > newState.get(i).index){
                    inversion++;
                }
            }
        }

        /* Jika inversion genap maka dapat diselesaikan */
        return inversion%2 == 0;
    }

    /**
     * Action performed
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        /* Dont touch this shitty code */
        JButton button = (JButton) e.getSource();
        button.getParent().requestFocus();
        Dimension size = button.getSize();

        int labelX = whiteButton.getX();
        int labelY = whiteButton.getY();
        int buttonX = button.getX();
        int buttonY = button.getY();
        int buttonPosX = buttonX / size.width;
        int buttonPosY = buttonY / size.height;
        int buttonIndex = pos[buttonPosY][buttonPosX];

        if (labelX == buttonX && (labelY - buttonY) == size.height ) {

            int labelIndex = buttonIndex + 3;

            panelPuzzle.remove(buttonIndex);
            panelPuzzle.add(whiteButton, buttonIndex);
            panelPuzzle.add(button,labelIndex);
            panelPuzzle.validate();

            Collections.swap(state, buttonIndex, labelIndex);
        }

        if (labelX == buttonX && (labelY - buttonY) == -size.height ) {

            int labelIndex = buttonIndex - 3;
            panelPuzzle.remove(labelIndex);
            panelPuzzle.add(button,labelIndex);
            panelPuzzle.add(whiteButton, buttonIndex);
            panelPuzzle.validate();

            Collections.swap(state, buttonIndex, labelIndex);
        }

        if (labelY == buttonY && (labelX - buttonX) == size.width ) {

            int labelIndex = buttonIndex + 1;

            panelPuzzle.remove(buttonIndex);
            panelPuzzle.add(whiteButton, buttonIndex);
            panelPuzzle.add(button,labelIndex);
            panelPuzzle.validate();

            Collections.swap(state, buttonIndex, labelIndex);
        }

        if (labelY == buttonY && (labelX - buttonX) == -size.width ) {

            int labelIndex = buttonIndex - 1;

            panelPuzzle.remove(buttonIndex);
            panelPuzzle.add(whiteButton, labelIndex);
            panelPuzzle.add(button,labelIndex);
            panelPuzzle.validate();

            Collections.swap(state, buttonIndex, labelIndex);
        }
        /* End of shitty code */

        isSolved();
    }

    /**
     * Is Game Solved
     */
    private void isSolved() {
        if(state.equals(solved)){
            LeaderBoard leaderBoard = new LeaderBoard();
            leaderBoard.push(new Player(name, seconds));
            leaderBoard.writeFile();

            JOptionPane.showMessageDialog(
                    null,
                    "Nama: "+name+"\nSkor: "+seconds
            );
            setVisible(false);
            Main main = new Main();
        }
    }

    /**
     * Resize image
     * @param img gambar
     * @param newW width baru
     * @param newH height baru
     * @return gambar
     */
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
