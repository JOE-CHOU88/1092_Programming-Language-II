import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Movie {
	private int id;
	private String name;
	private String director;
	private String description;
	private String rating;
	private String theater;
	private String trailer;
	private String award;
	private String language;
	private ImageIcon picture;
	private Date date;
	private Time time;
	
	public Movie(int id, String name, String director, String description, String rating, String theater, String trailer, String award, String language, String pictureURL, Date date, Time time) {
		this.id = id;
		this.name = name;
		this.director = director;
		this.description = description;
		this.rating = rating;
		this.theater = theater;
		this.trailer = trailer;
		this.award = award;
		this.language = language;
		try {
			URL url = new URL(pictureURL);
			
			BufferedImage img = ImageIO.read(url);
			ImageIcon icon = new ImageIcon(img);
			this.picture = icon;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.date = date;
		this.time = time;
	}
	
	public int getID() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDirector() {
		return director;
	}
	public String getDescription() {
		return description;
	}
	public String getRating() {
		return rating;
	}
	public String getTheater() {
		return theater;
	}
	public String getTrailer() {
		return trailer;
	}
	public String getLanguage() {
		return language;
	}
	public ImageIcon getPicture() {
		return picture;
	}
	public Date getDate() {
		return date;
	}
	public Time getTime() {
		return time;
	}
	public String getHourAndMin() {
		return getTime().toString().substring(0, 5);
	}
	public String getAward() {
		return award;
	}
}
