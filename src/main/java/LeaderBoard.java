import com.google.gson.Gson;

import java.io.*;
import java.util.*;

public class LeaderBoard {

    private String pathName = "D:/data.json";
    protected List<Player> data = new ArrayList<Player>();

    LeaderBoard(){
        this.readFile();
    }

    public void readFile(){
        try{
            FileReader fileReader = new FileReader(pathName);
            List<Object> list = new Gson().fromJson(fileReader, ArrayList.class);

            if (list != null) {
                for (Object item: list){
                    this.push(new Gson().fromJson(item.toString(), Player.class));
                }
                sort();
            }

            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeFile(){
        try{
            FileWriter fileWriter = new FileWriter(pathName);
            fileWriter.write(new Gson().toJson(data));
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void push(Player player){
        data.add(player);
    }

    private void sort() {
        data.sort(new Comparator<Player>() {
            public int compare(Player o1, Player o2) {
                return o2.getScore() - o1.getScore();
            }
        });
    }
}
