public class Position {

    /* Index i dalam 2d */
    protected int i;
    /* Index j dalam 2d */
    protected int j;
    /* Index urutan dalam 1d */
    protected int index;

    /**
     * Konstruktor
     * @param i index i dalam 2d
     * @param j index j dalam 2d
     * @param index index urutan dalam 1d
     */
    public Position(int i, int j, int index){
        this.setI(i);
        this.setJ(j);
        this.setIndex(index);
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
