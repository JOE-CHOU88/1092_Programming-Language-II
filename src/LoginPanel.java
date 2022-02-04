//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.event.*;
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.awt.BorderLayout;
//import java.awt.CardLayout;
//import java.util.ArrayList;
//
//public class LoginPanel extends JPanel{
//	private static final int FIELD_WIDTH=10;
//	private JPanel loginPanel;
//	private JPanel userIDPanel;
//	private JLabel imgLabel;
//	private JLabel userIDLabel;
//	private JTextField userIDField;
//	private JButton loginButton;
//	public LoginPanel() {
//		createComp();
//	}
//	public void createComp() {
//		loginPanel=new JPanel();
//		userIDPanel=new JPanel();
//		imgLabel=new JLabel(new ImageIcon());
//		URL url;
//		try {
//			url = new URL("https://scontent.ftpe7-1.fna.fbcdn.net/v/t1.18169-9/969139_538180219589796_1429633815_n.jpg?_nc_cat=110&ccb=1-3&_nc_sid=973b4a&_nc_ohc=hQOxszwq6roAX_4-9qD&_nc_ht=scontent.ftpe7-1.fna&oh=1e234a80eff769c75a0cdf1e311bb5df&oe=60CCC8CC");
//			BufferedImage img;
//			img = ImageIO.read(url);
//			ImageIcon icon = new ImageIcon(img);
//			imgLabel.setIcon(icon);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		userIDLabel=new JLabel("User ID:");
//		userIDField=new JTextField(FIELD_WIDTH);
//		loginButton=new JButton("Log in");
//		
//		userIDPanel.add(userIDLabel);
//		userIDPanel.add(userIDField);
//		loginPanel.setLayout(new BorderLayout());
//		loginPanel.add(imgLabel,BorderLayout.NORTH);
//		loginPanel.add(userIDPanel,BorderLayout.CENTER);
//		loginPanel.add(loginButton,BorderLayout.SOUTH);
//		add(loginPanel);
//	}
//	public void addButtonListener(JPanel panel, JMenuBar mb) {
//		class ClickListener implements ActionListener{
//			public void actionPerformed(ActionEvent e) {
//				CardLayout cardLayout=(CardLayout) panel.getLayout();
//				cardLayout.show(panel,"home");
//				mb.setVisible(true);
//			}
//		}
//		ActionListener listener=new ClickListener();
//		loginButton.addActionListener(listener);
//	}
//}

import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
public class LoginPanel extends JPanel{
	private static final int FIELD_WIDTH=10;
	private SQLExec exec;
	private JPanel loginPanel;
	private JPanel userInfoPanel;
	private JPanel buttonPanel;
	private JLabel imgLabel;
	private JLabel userIDLabel;
	private JLabel passwardLabel;
	private JLabel __, warningLabel;
	private JTextField userIDField;
	private JPasswordField passwordField;
	private JButton loginButton, signInButton;
	public LoginPanel(JPanel cardPanel, JMenuBar mb){
		createButton();
		addSignUpButtonListener(cardPanel);
		this.addLoginButtonListener(cardPanel, mb);
	}
	public void createButton() {
		loginPanel = new JPanel();
		userInfoPanel = new JPanel();
		buttonPanel = new JPanel(new GridLayout(2, 2));
		ImageIcon Icon15 = Helper.getImageIcon("https://www.fuzhong15.ntpc.gov.tw/files/file_pool/1/0l342646330975748167/%e5%ae%98%e7%b6%b2logo.png");
		//Icon15.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
		imgLabel = new JLabel(Icon15);
		userIDLabel = new JLabel("User ID:");
		passwardLabel = new JLabel("Passward:");
		__ = new JLabel("not goona use this JLabel");
		__.setVisible(false);;
		warningLabel = new JLabel("");
		warningLabel.setVisible(false);
		
		userIDField = new JTextField(FIELD_WIDTH);
		passwordField = new JPasswordField(FIELD_WIDTH);
		loginButton = new JButton("Log in");
		signInButton = new JButton("Sign up");
		
		userInfoPanel.add(userIDLabel);
		userInfoPanel.add(userIDField);
		userInfoPanel.add(passwardLabel);
		userInfoPanel.add(passwordField);
		
		buttonPanel.add(loginButton);
		buttonPanel.add(signInButton);
		buttonPanel.add(__);
		buttonPanel.add(warningLabel);
		
		loginPanel.setLayout(new BorderLayout());
		loginPanel.add(imgLabel,BorderLayout.NORTH);
		loginPanel.add(userInfoPanel,BorderLayout.CENTER);
		loginPanel.add(buttonPanel,BorderLayout.SOUTH);
		add(loginPanel);
	}
	public void addLoginButtonListener(JPanel cardPanel, JMenuBar mb) {
		class ClickListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				String userID = userIDField.getText();
				String password = String.valueOf(passwordField.getPassword());
				if(! userID.isBlank() && ! password.isBlank()) {
					exec = new SQLExec();
//					System.out.println(userID + "\n" + password);
					
					try {
						boolean loginSuccessful = exec.login(userID, password);
						if(loginSuccessful) {
							User user = new User(userID, password);
							InfoPanel homePanel = new InfoPanel(cardPanel, user);
							SearchPanel searchPanel = new SearchPanel(cardPanel, user);
							
							cardPanel.add(homePanel, "home");
							cardPanel.add(searchPanel, "search");
							
							((CardLayout) cardPanel.getLayout()).show(cardPanel, "home");
//							cardLayout.show(cardPanel,"signup");
							
							warningLabel.setText("");
						
							mb.setVisible(true);
						}else {
							boolean UserIDExist = exec.isUserIDExist(userID);
							if (UserIDExist) {
								warningLabel.setText("Wrong Password");
							}else {
								warningLabel.setText("You haven't sign up before. Please click the sign up button.");
							}
							warningLabel.setVisible(true);
						}
					} catch (SQLException | NullPointerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						System.out.println("Connection error");
						warningLabel.setText("Connection error");
						warningLabel.setVisible(true);
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
		loginButton.addActionListener(listener);
	}
	public void addSignUpButtonListener(JPanel cardPanel) {
		class ClickListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				resetField();
				CardLayout cardLayout=(CardLayout) cardPanel.getLayout();
				cardLayout.show(cardPanel,"signup");
				warningLabel.setVisible(false);
			}
		}
		ActionListener listener = new ClickListener();
		signInButton.addActionListener(listener);
	}
	public void resetField() {
		userIDField.setText("");
		passwordField.setText("");
	}
}

