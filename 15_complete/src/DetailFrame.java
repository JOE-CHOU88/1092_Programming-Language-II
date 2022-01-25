import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.*;

public class DetailFrame extends JFrame{
	private final int FRAME_WIDTH = 300;
	private final int FRAME_HEIGHT = 600;
	private JPanel awardPanel;
	private JLabel awardLabel;
	
	public DetailFrame(Movie movie) {
		setTitle(movie.getName());
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		createComponents(movie);
	}

	public void createComponents(Movie movie) {
		JLabel imgLabel = new JLabel(movie.getPicture());
		JLabel nameLabel = new JLabel("Name: " + movie.getName());
		JLabel directorLabel = new JLabel("Director: " + movie.getDirector());
		JLabel ratingLabel = new JLabel("Rating: " + movie.getRating());
		JLabel theaterLabel = new JLabel("Theater: " + movie.getTheater());
		JLabel languageLabel = new JLabel("Language: " + movie.getLanguage());
		JLabel dateLabel = new JLabel("Date: " + movie.getDate().toString());
		JLabel timeLabel = new JLabel("Time: " + movie.getHourAndMin());
		awardPanel = new JPanel(new GridLayout(0, 1));
		awardLabel = new JLabel();
		
		if(movie.getAward() == null) {
			awardLabel.setText("Award: None");
		}else {
			String awardStr = "<html>Award: ";
			int awardTime = 0;
			for(int i = 0; i < movie.getAward().length(); i++) {
				if(movie.getAward().substring(i,i+1).equals("★")) {
					awardTime += 1;
					if(awardTime > 1) {
						awardStr += "<br>" + movie.getAward().substring(i,i+1);
					}else {
						awardStr += movie.getAward().substring(i,i+1);
					}
				}else {
					awardStr += movie.getAward().substring(i,i+1);
				}
				
				if(i == movie.getAward().length() - 1) {
					awardStr += "<br></html>";
				}
			}
			//System.out.println(awardStr);
			awardLabel.setText(awardStr);
		}
		JLabel trailerLabel = new JLabel("Trailer");
		trailerLabel.setForeground(new Color(6,69,173));
		trailerLabel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				Desktop desktop = java.awt.Desktop.getDesktop();
				//Window window = java.awt.Window.getWindows();
				//System.out.println(desktop);
				try {
					if(Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {
						desktop.browse(new URI(movie.getTrailer()));
					}else {
						System.out.println("desktop is not available!");
					}
					//System.out.println(movie.getTrailer());
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println("DetailFrame 78: IOException or URISyntaxException!!!");
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				trailerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}});
		JTextArea descriptionArea = new JTextArea();
		descriptionArea.setText(movie.getDescription());
		descriptionArea.setLineWrap(true);
		descriptionArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(descriptionArea);
		
		JPanel infoPanel = new JPanel(new GridLayout(0, 1));
		infoPanel.add(nameLabel);
		infoPanel.add(directorLabel);
		infoPanel.add(ratingLabel);
		infoPanel.add(languageLabel);
		infoPanel.add(theaterLabel);
		infoPanel.add(trailerLabel);
		infoPanel.add(dateLabel);
		infoPanel.add(timeLabel);
		infoPanel.add(awardLabel);
		
		JPanel overallPanel = new JPanel(new BorderLayout());
		
		overallPanel.add(imgLabel, BorderLayout.NORTH);
		overallPanel.add(infoPanel, BorderLayout.CENTER);
		overallPanel.add(scrollPane, BorderLayout.SOUTH);
		
		add(overallPanel);
	}
}
