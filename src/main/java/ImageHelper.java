import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageHelper {

    /**
     * Resize image
     * @param img gambar
     * @param width width baru
     * @param height height baru
     * @return gambar
     */
    public static BufferedImage resize(BufferedImage img, int width, int height) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public static BufferedImage resize(BufferedImage img, int dimension) {
        return resize(img, dimension, dimension);
    }

    /**
     * Crop on Center Square
     * @param img
     * @return
     */
    public static BufferedImage cropCenterSquare(BufferedImage img){
        int width = img.getWidth();
        int height = img.getHeight();
        int dim = Math.min(width, height);

        return img.getSubimage((width-dim)/2,(height-dim)/2,dim,dim);
    }

    public static BufferedImage crop(BufferedImage img, int x, int y, int w, int h){
        return img.getSubimage(x,y,w,h);
    }
}
