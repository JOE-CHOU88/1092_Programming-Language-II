import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;


public class SearchResultPanel extends JPanel{
	private static final int FIELD_WIDTH = 10; 
	private JButton homeButton;
	private JButton searchButton;
	private JButton trailerButton;	
	private JPanel cardPanel;	
	private CardLayout cardLayout;	
	
	// 搜尋成功的constructor
	public SearchResultPanel(JPanel panel, ResultSet rs){ 
		cardPanel = panel;
		cardLayout = (CardLayout) panel.getLayout();
		
		setLayout(new BorderLayout());
		
		createTopLineComp();
		createMovieButtons(rs);
	}
	// 搜尋失敗的constructor
	public SearchResultPanel(JPanel panel){ 
		cardPanel = panel;
		cardLayout = (CardLayout) panel.getLayout();
		
		setLayout(new BorderLayout());
		
		createTopLineComp();
	}
	
	public void createTopLineComp() {
		homeButton = new JButton();
		homeButton.setIcon(new ImageIcon(new ImageIcon("home.png").getImage().getScaledInstance(19, 19, Image.SCALE_DEFAULT)));

		
		searchButton = new JButton("search");
		trailerButton = new JButton("trailer");
		
		homeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cardLayout.show(cardPanel, "2");
			}
			
		});
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cardLayout.show(cardPanel, "3");
			}
			
		});
				
		JPanel toplinePanel=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		toplinePanel.add(homeButton);
		toplinePanel.add(searchButton);
		toplinePanel.add(trailerButton);
			
		add(toplinePanel, BorderLayout.NORTH);
	}
	public void createMovieButtons(ResultSet rs) {
		searchButton = new JButton("search");
		trailerButton = new JButton("trailer");
				
		JPanel topPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		topPanel.add(searchButton);
		topPanel.add(trailerButton);
		
		JPanel moviePanel=new JPanel();
		BoxLayout boxLayout = new BoxLayout(moviePanel, BoxLayout.Y_AXIS);
		moviePanel.setLayout(boxLayout);
					
		try {
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
				MovieDescriptionButton moviebutton = new MovieDescriptionButton(movie);
				
				moviePanel.add(moviebutton);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JScrollPane scrollPanel=new JScrollPane(moviePanel); 
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.getVerticalScrollBar().setUnitIncrement(27);
		
		add(scrollPanel, BorderLayout.CENTER);
	}
}

