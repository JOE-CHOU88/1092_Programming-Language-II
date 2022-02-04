import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
public class SignUpPanel extends JPanel{
	private static final int FIELD_WIDTH=10;
	private SQLExec exec;
	private JPanel loginPanel;
	private JPanel userInfoPanel;
	private JPanel buttonPanel;
	private JLabel imgLabel;
	private JLabel userIDLabel;
	private JLabel passwordLabel;
	private JLabel __, warningLabel;
	private JTextField userIDField;
	private JPasswordField passwordField;
	private JButton backButton, sendButton;
	private Font font0;
	private Font font12;
	public SignUpPanel(JPanel cardPanel){
		font0 = new Font("", 1, 0);
		font12 = new Font("", 1, 12);
		createButton();
		addBackButtonListener(cardPanel);
		addSendButtonListener(cardPanel);
	}
	public void createButton() {
		loginPanel = new JPanel();
		userInfoPanel = new JPanel();
		buttonPanel = new JPanel(new GridLayout(2, 2));
		ImageIcon Icon15 = Helper.getImageIcon("https://www.fuzhong15.ntpc.gov.tw/files/file_pool/1/0l342646330975748167/%e5%ae%98%e7%b6%b2logo.png");
		imgLabel = new JLabel(Icon15);
		userIDLabel = new JLabel("User ID:");
		passwordLabel = new JLabel("Passward:");
		__ = new JLabel("not goona use this JLabel");
		__.setVisible(false);;
		warningLabel = new JLabel("");
		warningLabel.setVisible(false);
		userIDField = new JTextField(FIELD_WIDTH);
		passwordField = new JPasswordField(FIELD_WIDTH);
		backButton = new JButton("Back");
		sendButton = new JButton("Send");
		
		userInfoPanel.add(userIDLabel);
		userInfoPanel.add(userIDField);
		userInfoPanel.add(passwordLabel);
		userInfoPanel.add(passwordField);
		
		buttonPanel.add(backButton);
		buttonPanel.add(sendButton);
		buttonPanel.add(__);
		buttonPanel.add(warningLabel);
		
		loginPanel.setLayout(new BorderLayout());
		loginPanel.add(imgLabel,BorderLayout.NORTH);
		loginPanel.add(userInfoPanel,BorderLayout.CENTER);
		loginPanel.add(buttonPanel,BorderLayout.SOUTH);
		add(loginPanel);
	}
	public void addBackButtonListener(JPanel cardPanel) {
		class ClickListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				resetField();
				CardLayout cardLayout=(CardLayout) cardPanel.getLayout();
				cardLayout.show(cardPanel,"login");
			}
		}
		ActionListener listener = new ClickListener();
		backButton.addActionListener(listener);
	}
	public void addSendButtonListener(JPanel cardPanel) {
		class ClickListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				String userID = userIDField.getText();
				String password = String.valueOf(passwordField.getPassword());
				if(! userID.isBlank() && ! password.isBlank()) {
					exec = new SQLExec();
					
					try {
						boolean isDuplicate = exec.isUserIDExist(userID);
						if (!isDuplicate) {
							exec.addUser(userID, password);
							warningLabel.setText("");
							JOptionPane.showMessageDialog(null, "Registered successfully", "Register", JOptionPane.INFORMATION_MESSAGE);
							((CardLayout)cardPanel.getLayout()).show(cardPanel, "login");
						}else {
							// duplicate userID
							warningLabel.setText("This userID has been used.");
							warningLabel.setVisible(true);
						}
					} catch (SQLException | NullPointerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						System.out.println("�s�Wuser�ɵo�Ϳ��~");
					}
					
					exec.closeConnection();
				}else {
					warningLabel.setText("Please enter your userID and passward.");
					warningLabel.setVisible(true);
				}
				resetField();
			}
		}
		ActionListener listener = new ClickListener();
		sendButton.addActionListener(listener);
	}
	public void resetField() {
		userIDField.setText("");
		passwordField.setText("");
	}
}
