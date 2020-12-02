import javax.swing.filechooser.FileFilter;
import java.io.File;

public class JPGFilter extends FileFilter {
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

    @Override
    public String getDescription() {
        return "JPG Images (*.jpg)";
    }
}
