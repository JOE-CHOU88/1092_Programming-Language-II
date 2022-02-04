import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import javax.swing.*;

public class InfoPanel extends JPanel{
	private JPanel topLinePanel;
	private JButton homeButton;
	private JButton tracingButton;
	private JButton searchButton;
	
	private JTabbedPane tabbedPanel;
	
	private SQLExec exec;
	private ResultSet rs;
	
	public InfoPanel(JPanel cardPanel, User user) {
		//System.out.println("<------------------Construct InfoPanel------------------>");
		this.setLayout(new BorderLayout());
		createTopLineComp(cardPanel, user);
		createTabbedPanel();
		getResultSet();
		createDescriptionPanel(cardPanel, user);
		createTrailerPanel();
		exec.closeConnection();
		cardPanel.add(this, "home");
		 
	}


	public InfoPanel(JPanel cardPanel, User user, String movieName, String date, String timeStart, String timeEnd,
			ArrayList<JCheckBox> ratings, String director, String award) {
		// TODO Auto-generated constructor stub
		this.setLayout(new BorderLayout());
		createTopLineComp(cardPanel, user);
		createTabbedPanel();
		getResultSet(movieName, date, timeStart, timeEnd, ratings, director, award);
		createDescriptionPanel(cardPanel, user);
		createTrailerPanel();
		exec.closeConnection();
		cardPanel.add(this, "result");
	}

	private void createTopLineComp(JPanel cardPanel, User user) {
		homeButton = new JButton("Home");
		tracingButton = new JButton("Tracing");
		searchButton = new JButton("Search");
		
		topLinePanel = new JPanel();
		topLinePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		topLinePanel.add(homeButton);
		topLinePanel.add(tracingButton);
		topLinePanel.add(searchButton);
		
		this.add(topLinePanel, BorderLayout.NORTH);
		
		homeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				((CardLayout) cardPanel.getLayout()).show(cardPanel, "home");
			}});
		tracingButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new TracingPanel(cardPanel, user);
				((CardLayout) cardPanel.getLayout()).show(cardPanel, "tracing");
			}
			
		});
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				((CardLayout) cardPanel.getLayout()).show(cardPanel, "search");
			}});
	}
	public void createTabbedPanel() {
		tabbedPanel = new JTabbedPane(JTabbedPane.BOTTOM);
		this.add(tabbedPanel, BorderLayout.CENTER);
	}
	public void getResultSet() {
		exec = new SQLExec();
		rs = null;
		try {
			//System.out.println("<---------------InfoPanel 97--------------->");
			rs = exec.getAllMovies();
//			if (rs.next()) {
//				System.out.println("Yah");
//			}else {
//				System.out.println("Nop");
//			}
		} catch (SQLException | NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("�P��Ʈw�����Ҧ��q�v��Ʈɵo�Ϳ��~");
		}
	}
	private void getResultSet(String movieName, String date, String timeStart, String timeEnd,	ArrayList<JCheckBox> ratings, String director, String award) {
		// TODO Auto-generated method stub
		exec = new SQLExec();

		rs = null;
		try {
			SQLExec exec = new SQLExec();
			rs = exec.searchMovies(movieName, date, timeStart, timeEnd, ratings, director, award);
		} catch (SQLException | NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("�j�M�ɵo�Ϳ��~");
		}
	}
	public void createDescriptionPanel(JPanel cardPanel, User user) {
		JPanel entryPanel=new JPanel();
		BoxLayout boxLayout = new BoxLayout(entryPanel, BoxLayout.Y_AXIS);
		entryPanel.setLayout(boxLayout);

		try {
			rs.beforeFirst();
			while (rs.next()) {
				int id = rs.getInt("schedule.id");
				String name = rs.getString("name");
				System.out.println(name);
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
				
				JButton operationButton;
				operationButton = new TraceButton(user, id);
				
				JPanel wrapperPanel = new JPanel(new BorderLayout());
				wrapperPanel.add(descriptionButton, BorderLayout.CENTER);
				wrapperPanel.add(operationButton, BorderLayout.EAST);
				
				entryPanel.add(wrapperPanel);
			}
			
		} catch (SQLException | NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JScrollPane scrollPanel = new JScrollPane(entryPanel); 
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.getVerticalScrollBar().setUnitIncrement(27);
		
		tabbedPanel.add("Info", scrollPanel);
	}
	public void createTrailerPanel() {
		ArrayList<String> trailers = new ArrayList<String>();
		
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
					//System.out.println(trailer);
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
