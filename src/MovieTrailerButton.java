import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

public class MovieTrailerButton extends JButton{
	public MovieTrailerButton(Movie movie) {
		setIcon(new ImageIcon(movie.getPicture().getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
		
		String textTemplate = "<html>電影名稱：%s<br>分級：%s</html>";
		String text = String.format(textTemplate, movie.getName(), movie.getRating());
		setText(text);
		
		setHorizontalAlignment(SwingConstants.LEFT);
		setIconTextGap(20);
		
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Helper.openURL(movie.getTrailer());
			}
			
		});
	}
}
