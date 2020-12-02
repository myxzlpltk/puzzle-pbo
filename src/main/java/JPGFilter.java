import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Class untuk filter JFileChooser
 */
public class JPGFilter extends FileFilter {

    /**
     * Mengecek ekstensi dan menentukan untuk menerima file atau tidak
     *
     * @param f File object
     * @return boolean
     */
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        else {
            String filename = f.getName().toLowerCase();
            return filename.endsWith(".jpg") || filename.endsWith(".jpeg") ;
        }
    }

    /**
     * Deskripsi yang muncul di kotak pencarian
     *
     * @return String
     */
    @Override
    public String getDescription() {
        return "JPG Images (*.jpg)";
    }
}
