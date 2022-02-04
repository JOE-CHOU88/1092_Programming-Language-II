import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

public class MovieDescriptionButton extends JButton{
	public MovieDescriptionButton(Movie movie) {
		this.setPreferredSize(new Dimension(100, 100));
		setIcon(new ImageIcon(movie.getPicture().getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
		
		String textTemplate = "<html>電影名稱：%s<br>日期：%s<br>時間：%s<br>分級：%s</html>";
		String text = String.format(textTemplate, movie.getName(), movie.getDate(), movie.getHourAndMin(), movie.getRating());
		setText(text);
		
		setHorizontalAlignment(SwingConstants.LEFT);
		setIconTextGap(20);
		
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DetailFrame detailFrame = new DetailFrame(movie);
				detailFrame.setVisible(true);
			}
			
		});
	}
}
