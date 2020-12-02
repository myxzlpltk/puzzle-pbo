import javax.swing.table.AbstractTableModel;

public class ModelLeaderBoard extends AbstractTableModel {

    private String headers[] = {"#", "Nama", "Skor"};
    private LeaderBoard leaderBoard;

    public ModelLeaderBoard(LeaderBoard leaderBoard){
        this.leaderBoard = leaderBoard;
    }

    public int getRowCount() {
        return 5;
    }

    public int getColumnCount() {
        return 3;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if(rowIndex < leaderBoard.data.size()){
            switch (columnIndex){
                case 0:
                    return rowIndex+1;
                case 1:
                    return leaderBoard.data.get(rowIndex).getName();
                case 2:
                    return leaderBoard.data.get(rowIndex).getScore();
            }
        }

        return "-";
    }

    @Override
    public String getColumnName(int column) {
        return headers[column];
    }
}
