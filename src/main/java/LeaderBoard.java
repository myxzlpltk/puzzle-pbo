import com.google.gson.Gson;

import java.io.*;
import java.util.*;

public class LeaderBoard {

    /* Path Name */
    private String pathName = "D:/data.json";
    /* List Scoreboard */
    protected List<Player> data = new ArrayList<Player>();

    /**
     * Method Konstruktor
     */
    LeaderBoard(){
        /* Read File saat diinisialisasi */
        if(new File(pathName).exists()) {
            this.readFile();
        }
    }

    /**
     * Membaca file
     */
    public void readFile(){
        try{
            /* Menginisialisasi resource */
            FileReader fileReader = new FileReader(pathName);
            /* Mengonversi ke list */
            List<Object> list = new Gson().fromJson(fileReader, ArrayList.class);

            /* Jika list tidak kosong */
            if (list != null) {
                /* Iterasi semua item */
                for (Object item: list){
                    /* Deserialisasi obyek Player dan push ke data */
                    this.push(new Gson().fromJson(item.toString(), Player.class));
                }

                /* Urutkan */
                sort();
            }

            /* Tutup resource */
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Menulis file
     */
    public void writeFile(){
        try{
            /* Menginisialisasi resource */
            FileWriter fileWriter = new FileWriter(pathName);
            /* Serialisasi dan tulis ke file */
            fileWriter.write(new Gson().toJson(data));
            /* Tutup resource */
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Push data ke array list
     * @param player
     */
    public void push(Player player){
        /* Just push */
        data.add(player);
    }

    /**
     * Mengurutkan data
     */
    private void sort() {
        /* Urutkan data dengan Comparator */
        data.sort(new Comparator<Player>() {
            /**
             * Method perbandingan
             * @param o1 obyek1
             * @param o2 obyek2
             * @return int
             */
            public int compare(Player o1, Player o2) {
                return o1.getScore() - o2.getScore();
            }
        });
    }
}
