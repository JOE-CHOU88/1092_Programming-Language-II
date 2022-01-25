import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class UntraceButton extends JButton{
	private String userID;
	private int scheduleID;
	
	public UntraceButton(JPanel cardPanel, User user, int scheduleID) {
		this.userID = user.getUserID();
		this.scheduleID = scheduleID;
		
		this.setText("-");
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub				
				SQLExec exec = new SQLExec();
				try {
					exec.deleteTracing(userID, scheduleID);
					user.getTracingList().remove(Integer.valueOf(scheduleID));
					JOptionPane.showMessageDialog(null, "Successful Removed", "Info", JOptionPane.PLAIN_MESSAGE);
					
					new TracingPanel(cardPanel, user); 
					((CardLayout) cardPanel.getLayout()).show(cardPanel, "tracing");
				} catch (NullPointerException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Can't connect with database", "Error", JOptionPane.ERROR_MESSAGE);
				}
				exec.closeConnection();
			}});
	}
}
