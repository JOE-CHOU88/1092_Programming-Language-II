import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Viewer extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final int FRAME_WIDTH=800;
	public static final int FRAME_HEIGHT=600;
	public static void main(String[] args) throws SQLException {
		JFrame f=new JFrame("Fuzhong-15");
		JPanel cardPanel=new JPanel(new CardLayout());

		
		// �s�@menu
		JMenuBar mb=new JMenuBar();
		JMenu m=new JMenu("Menu");
		JMenuItem ml=new JMenuItem("Log out");
		JMenuItem me=new JMenuItem("Exit");
		m.add(ml);
		m.add(me);
		mb.add(m);
		f.setJMenuBar(mb);
		
		
		LoginPanel loginPanel=new LoginPanel(cardPanel, mb);
		SignUpPanel signUpPanel=new SignUpPanel(cardPanel);
		
		cardPanel.add(loginPanel,"login");
		cardPanel.add(signUpPanel,"signup");
		
		((CardLayout) cardPanel.getLayout()).show(cardPanel,"login");
		
		// �]�wlogout���欰
		class LogOutListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				((CardLayout) cardPanel.getLayout()).show(cardPanel,"login");
				mb.setVisible(false);
			}
		}
		ActionListener l=new LogOutListener();
		ml.addActionListener(l);
		
		// �]�wexit���欰
		class ExitListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		ActionListener e=new ExitListener();
		me.addActionListener(e);
		
		// �NcardsLayout�[�Jframe�����
		mb.setVisible(false);
		f.add(cardPanel);
		f.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}	
}

