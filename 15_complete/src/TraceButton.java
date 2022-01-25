import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class TraceButton extends JButton{
	private String userID;
	private int scheduleID;
	
	public TraceButton(User user, int scheduleID) {
		this.userID = user.getUserID();
		this.scheduleID = scheduleID;
		
		this.setText("+");
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub				
				
				
				if (user.getTracingList().contains(scheduleID)){
					JOptionPane.showMessageDialog(null, "Duplicate Tracing", "Warning", JOptionPane.WARNING_MESSAGE);
				}else {
					SQLExec exec = new SQLExec();
					try {
						exec.addTracing(userID, scheduleID);
						user.getTracingList().add(scheduleID);
						JOptionPane.showMessageDialog(null, "Successfully Added", "Info", JOptionPane.INFORMATION_MESSAGE);
					} catch (NullPointerException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Can't connect with database", "Error", JOptionPane.ERROR_MESSAGE);
					}
					exec.closeConnection();
				}
				

				
			}});
	}
}
