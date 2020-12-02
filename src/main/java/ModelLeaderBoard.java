import javax.swing.table.AbstractTableModel;

public class ModelLeaderBoard extends AbstractTableModel {

    /* Menentukan nama header */
    private String headers[] = {"#", "Nama", "Skor"};
    /* Obyek LeaderBoard */
    private LeaderBoard leaderBoard;

    /* Menerima obyek leaderboard */
    public ModelLeaderBoard(LeaderBoard leaderBoard){
        this.leaderBoard = leaderBoard;
    }

    /* Menentukan jumlah baris adalah 5 */
    public int getRowCount() {
        return 5;
    }

    /* menentukan jumlah kolom adalah 3*/
    public int getColumnCount() {
        return 3;
    }

    /* Menentukan isian sel */
    public Object getValueAt(int rowIndex, int columnIndex) {
        /* jika indexnya ada di data */
        if(rowIndex < leaderBoard.data.size()){
            switch (columnIndex){
                /* Semisal kolom pertama */
                case 0:
                    return rowIndex+1;
                /* Semisal kolom kedua */
                case 1:
                    return leaderBoard.data.get(rowIndex).getName();
                /* Semisal kolom ketiga */
                case 2:
                    return leaderBoard.data.get(rowIndex).getScore();
            }
        }

        /* Default return */
        return "-";
    }

    /* Menentukan nama kolom */
    @Override
    public String getColumnName(int column) {
        return headers[column];
    }
}
