import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import javax.swing.*;

public class TracingPanel extends JPanel{
	private JPanel topLinePanel;
	private JButton homeButton;
	private JButton googleButton;
	private JButton clearButton;
	
	private JTabbedPane tabbedPanel;
	
	private SQLExec exec;
	private ResultSet rs;
	private JPanel descriptionPanel;
	private JPanel trailerPanel;
	
	public TracingPanel(JPanel cardPanel, User user) {
		this.setLayout(new BorderLayout());
		createTopLineComp(cardPanel, user);
		createTabbedPanel();
		getResultSet(user);
		createDescriptionPanel(cardPanel, user, "tracing");
		createTrailerPanelPanel();
		exec.closeConnection();
		cardPanel.add(this, "tracing");
	}



	private void createTopLineComp(JPanel cardPanel, User user) {
		homeButton = new JButton("Home");
		googleButton = new JButton("Google Calender");
		clearButton = new JButton("Clear");
		
		topLinePanel = new JPanel();
		topLinePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		topLinePanel.add(homeButton);
		topLinePanel.add(clearButton);
		topLinePanel.add(googleButton);
		
		this.add(topLinePanel, BorderLayout.NORTH);
		
		homeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				((CardLayout) cardPanel.getLayout()).show(cardPanel, "home");
			}});
		googleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				new TracingPanel(cardPanel, user);
//				((CardLayout) cardPanel.getLayout()).show(cardPanel, "tracing");
				try {
					GoogleCalender.update(user);
				} catch (IOException | GeneralSecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				user.getTracingList().clear();
				exec = new SQLExec();
				try {
					exec.deleteAllTracing(user.getUserID());
				} catch (NullPointerException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				new TracingPanel(cardPanel, user);
				((CardLayout) cardPanel.getLayout()).show(cardPanel, "tracing");
			}});
	}
	public void createTabbedPanel() {
		tabbedPanel = new JTabbedPane(JTabbedPane.BOTTOM);
		this.add(tabbedPanel, BorderLayout.CENTER);
	}
	public void getResultSet(User user) {
		exec = new SQLExec();
		rs = null;

		try {
			rs = exec.getTracingMovies(user.getUserID());
		} catch (SQLException | NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("與資料庫索取追蹤清單時發生錯誤");
		}
	}

	public void createDescriptionPanel(JPanel cardPanel, User user, String mode) {
		JPanel entryPanel=new JPanel();
		BoxLayout boxLayout = new BoxLayout(entryPanel, BoxLayout.Y_AXIS);
		entryPanel.setLayout(boxLayout);

		try {
			rs.beforeFirst();
			while (rs.next()) {
				int id = rs.getInt("schedule.id");
				String name = rs.getString("name");
				String director = rs.getString("director");
				String description = rs.getString("description");
				String rating = rs.getString("rating.type");
				String theater = rs.getString("theater.type");
				String trailer = rs.getString("trailer");
				String award = rs.getString("award");
				String language = rs.getString("language");
				String pictureURL = rs.getString("picture_url");
				Date date = rs.getDate("date");
				Time time = rs.getTime("time");
		
				Movie movie = new Movie(id, name, director, description, rating, theater, trailer, award, language, pictureURL, date, time);
				MovieDescriptionButton descriptionButton = new MovieDescriptionButton(movie);
				
				JButton operationButton = new UntraceButton(cardPanel, user, id);
				
				JPanel wrapperPanel = new JPanel(new BorderLayout());
				wrapperPanel.add(descriptionButton, BorderLayout.CENTER);
				wrapperPanel.add(operationButton, BorderLayout.EAST);
				
				entryPanel.add(wrapperPanel);
			}
			
		} catch (SQLException | NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JScrollPane scrollPanel=new JScrollPane(entryPanel); 
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.getVerticalScrollBar().setUnitIncrement(27);
		
		tabbedPanel.add("Info", scrollPanel);
	}
	public void createTrailerPanelPanel() {
		ArrayList<String> trailers = new ArrayList();
		
		JPanel entryPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(entryPanel, BoxLayout.Y_AXIS);
		entryPanel.setLayout(boxLayout);
		
		try {
			rs.beforeFirst();
			while (rs.next()) {
				int id = rs.getInt("schedule.id");
				String name = rs.getString("name");
				String director = rs.getString("director");
				String description = rs.getString("description");
				String rating = rs.getString("rating.type");
				String theater = rs.getString("theater.type");
				String trailer = rs.getString("trailer");
				String award = rs.getString("award");
				String language = rs.getString("language");
				String pictureURL = rs.getString("picture_url");
				Date date = rs.getDate("date");
				Time time = rs.getTime("time");
				
				if (!trailers.contains(trailer)) {
					Movie movie = new Movie(id, name, director, description, rating, theater, trailer, award, language, pictureURL, date, time);
					MovieTrailerButton moviebutton = new MovieTrailerButton(movie);
					entryPanel.add(moviebutton);
					
					trailers.add(trailer);
				}
			}
		} catch (SQLException | NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JScrollPane scrollPanel=new JScrollPane(entryPanel); 
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.getVerticalScrollBar().setUnitIncrement(27);
		
		tabbedPanel.add("Trailer", scrollPanel);
	}
}

