import static java.awt.Image.SCALE_SMOOTH;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Helper {
	public static ImageIcon getImageIcon(String fullUrlPath) {
		URL url;
        BufferedImage img;
        ImageIcon icon = null;
		try {
			url = new URL(fullUrlPath);
			System.out.println(url);
			//URLConnection connection = url.openConnection();
			//connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			//connection.connect();
			//img = ImageIO.read(connection.getInputStream());
			img = ImageIO.read(url.openStream());
			icon = new ImageIcon(img);
			icon = new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth()/6, icon.getIconHeight()/6, SCALE_SMOOTH));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return icon;
	}
	
	// �ϥΪ̿�J���Ox/y�A���ഫ��2021-x-y
	public static String convertDateFormat(String date) {
		if (date.equals("")){
			return "";
		}
		
		int slashIndex = date.indexOf("/"); 
		int month = Integer.parseInt(date.substring(0, slashIndex));
		int date_ = Integer.parseInt(date.substring(slashIndex+1));
		
		String format = "2022-%02d-%02d";
		return String.format(format, month, date_);
	}
	
	// �ϥΪ̿�J���Oxx:yy�A���ഫ��xx:yy:00
	public static String convertTimeFormat(String time) {
		if (time.equals("")){
			return "";
		}
		int colonIndex = time.indexOf(":"); 
		int hour = Integer.parseInt(time.substring(0, colonIndex));
		int min = Integer.parseInt(time.substring(colonIndex+1));
		
		String format = "%02d:%02d:00";
		return String.format(format, hour, min);
	}
	public static void openURL(String url) {
		Desktop desktop = java.awt.Desktop.getDesktop();
		try {
			//System.out.println(desktop.isSupported(Desktop.Action.BROWSE));
			desktop.browse(new URI(url));
		} catch (IOException | URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
