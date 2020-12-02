public class Player {

    /* Nama player */
    private String name;
    /* Skor player */
    private int score;

    /**
     * Konstruktor
     * @param name nama player
     * @param score skor player
     */
    public Player(String name, int score){
        /* Mengatur nama */
        this.setName(name);
        /* Mengatur Skor */
        this.setScore(score);
    }

    /* Setter nama player */
    public void setName(String name) {
        this.name = name;
    }

    /* Setter skor player */
    public void setScore(int score) {
        this.score = score;
    }

    /* Getter nama player */
    public String getName() {
        return name;
    }

    /* Getter skor player */
    public int getScore() {
        return score;
    }
}
