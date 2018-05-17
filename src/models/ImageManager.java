package models;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import views.Table;

public class ImageManager {
	private BufferedImage im;
	ArrayList<BufferedImage> images;

	public BufferedImage getIm() {
		return im;
	}

	public ImageManager() {
		images = new ArrayList<>();
	}

	public static void changeImage(String images) throws IOException {
			//			String p[] = string.split("#");
			//.println(images+ "   datosss");
			BufferedImage buf = ImageIO.read(new File("./data/"+images));
			set_Blanco_y_Negro(buf, images);
	}

	public static void set_Blanco_y_Negro(BufferedImage f, String img){
		BufferedImage bn = new BufferedImage(f.getWidth(),f.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
		for(int i=0;i<f.getWidth();i++)
			for(int j=0;j<f.getHeight();j++)
				bn.setRGB(i, j, f.getRGB(i, j));
		Table.changeBlack(img);
//		images.add(bn);
//		return images;
	}


	public void saveBlackAndWhite() {
		for (BufferedImage bufferedImage : images) {
			convertImage(bufferedImage);
		}
	}

	public Icon convertImage(BufferedImage buffer) {
		//.println(buffer.getWidth() + "   altura imagen a blanco y negro");
		return new ImageIcon(buffer);
	}

	public HashSet<String> getImagesSource(){
		HashSet<String> images = new HashSet<>();
		File file = new File("./data");
		//.println(file.list());
		File[] imagesArray = file.listFiles();
		for (int i = 0; i < imagesArray.length; i++) {
			images.add(imagesArray[i].getPath());
		}
		return images;
	}
	public ArrayList<BufferedImage> getImages() {
		return images;
	}
}
