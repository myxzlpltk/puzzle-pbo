import javax.swing.*;

public abstract class Puzzle extends JFrame {

    public Puzzle(String title) {
        super(title);
    }

    protected abstract void initialize();
    protected abstract void draw();
    protected abstract void randomize();
    protected abstract boolean isSolvable();
    protected abstract void checkSolution();
}
