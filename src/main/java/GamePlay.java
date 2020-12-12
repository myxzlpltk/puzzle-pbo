import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Timer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GamePlay extends Puzzle implements ActionListener {

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
    private ArrayList<Position> originalState = new ArrayList<>();
    private int timer = 0;
    private File file;
    private String name;

    public GamePlay(String name, File file){
        super("GamePlay");

        /* Setting layout puzzle */
        panelPuzzle.setLayout(new GridLayout(3, 3, 0, 0));

        /* Name */
        this.name = name;
        labelName.setText(name);

        /* File */
        this.file = file;

        initialize();
        randomize();
        draw();

        /* Timer */
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timer++;
                labelTime.setText(
                    String.format("%2s:%2s", timer/60, timer%60).replace(' ', '0')
                );
            }
        }, 0, 1000);

        /* Setting frame */
        setContentPane(this.panel1);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    protected void initialize(){
        try {
            /* Read file */
            source = ImageIO.read(file);

            /* Get width and height */
            int width = source.getWidth();
            int height = source.getHeight();

            /* Crop center and resize to 300px * 300px */
            source = ImageHelper.cropCenterSquare(source);
            source = ImageHelper.resize(source,300);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* Load position array */
        int index = 1;
        for ( int i = 0; i < 3; i++) {
            for ( int j = 0; j < 3; j++) {
                Position position = new Position(i,j,index++);
                state.add(position);
            }
        }

        originalState = (ArrayList<Position>) state.clone();
    }

    /* Random Puzzle */
    protected void randomize() {
        /* Selama puzzle unsolvable, acak! */
        do{
            Collections.shuffle(state);
        } while (!isSolvable());
    }

    /* Inject cell to panel */
    protected void draw() {
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
                image = ImageHelper.crop(
                    source,
                    position.j*width/3, position.i*height/3,
                    width/3, height/3
                );
                button.setIcon(new ImageIcon(image));
            }
        }
    }

    /**
     * Mengecek apakah puzzle dapat diselesaikan
     * @return boolean
     */
    protected boolean isSolvable() {
        /* Set inversion ke 0 */
        int inversion = 0;
        /* Create temp state */
        ArrayList<Position> state = (ArrayList<Position>) this.state.clone();
        state = (ArrayList<Position>) state.stream().filter(new Predicate<Position>() {
            @Override
            public boolean test(Position position) {
                return position.index != 9;
            }
        }).collect(Collectors.toList());

        /* Looping */
        for (int i=0; i<state.size(); i++){
            for (int j=i+1; j<state.size(); j++){
                if(state.get(j).index > state.get(i).index){
                    inversion++;
                }
            }
        }

        /* Jika inversion genap maka dapat diselesaikan */
        return inversion%2 == 0;
    }

    /**
     * Is Game Solved
     */
    protected void checkSolution() {
        if(state.equals(originalState)){
            LeaderBoard leaderBoard = new LeaderBoard();
            leaderBoard.push(new Player(name, timer));
            leaderBoard.writeFile();

            JOptionPane.showMessageDialog(
                    null,
                    "Nama: "+name+"\nSkor: "+timer
            );
            setVisible(false);
            Main main = new Main();
        }
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

        checkSolution();
    }

}
