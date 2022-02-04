import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class SearchPanel extends JPanel{ 
	private JLabel movielabel, datelabel,fromlabel,tolabel,ratinglabel,directorlabel,awardlabel;
	private JComboBox<String> datecomb,fromcomb,tocomb;
	private JCheckBox Gcheckb,PGcheckb,PG12checkb,PG15checkb,Rcheckb;
	private ArrayList<JCheckBox> ratings;
	private JTextField directorfield, moviefield, awardField;
	private JButton bc,bs;

	
	public SearchPanel(JPanel panel, User user){
		setLayout(new GridLayout(0, 2));
		
		createComp();
		addButtonListener();
		addButtonListener(panel, user);
		
		panel.add(this, "search");
	}
	public void createComp() {
		datelabel=new JLabel("Date:"); 
		fromlabel=new JLabel("Time: From"); 
		tolabel=new JLabel("           To"); 
		ratinglabel=new JLabel("Rating:");
		movielabel=new JLabel("Movie Name:"); 
		moviefield=new JTextField();
		directorlabel=new JLabel("Director:"); 
		directorfield=new JTextField();
		awardlabel=new JLabel("Award:");
		datecomb=new JComboBox<String>(); 
		fromcomb=new JComboBox<String>(); 
		tocomb=new JComboBox<String>(); 
		awardField=new JTextField();
		Gcheckb=new JCheckBox("普", true); 
		PGcheckb=new JCheckBox("護", true); 
		PG12checkb=new JCheckBox("輔12", true); 
		PG15checkb=new JCheckBox("輔15", true); 
		Rcheckb=new JCheckBox("限", true);
		
		ratings = new ArrayList<JCheckBox>();
		ratings.add(Gcheckb);
		Gcheckb.setSelected(true);
		ratings.add(PGcheckb);
		PGcheckb.setSelected(true);
		ratings.add(PG12checkb);
		PG12checkb.setSelected(true);
		ratings.add(PG15checkb);
		PG15checkb.setSelected(true);
		ratings.add(Rcheckb);
		Rcheckb.setSelected(true);
		JPanel p1=new JPanel();
		p1.add(Gcheckb); p1.add(PGcheckb); p1.add(PG12checkb); p1.add(PG15checkb); p1.add(Rcheckb);
		bc=new JButton("Clear"); bs=new JButton("Send");
		
		int month = 1;
		int month2 = 2;
		
		datecomb.addItem(""); datecomb.addItem(month + "/1"); datecomb.addItem(month + "/2"); datecomb.addItem(month + "/3"); datecomb.addItem(month + "/4"); datecomb.addItem(month + "/5"); datecomb.addItem(month + "/6");
		datecomb.addItem(month + "/7"); datecomb.addItem(month + "/8"); datecomb.addItem(month + "/9"); datecomb.addItem(month + "/10"); datecomb.addItem(month + "/11"); datecomb.addItem(month + "/12");
		datecomb.addItem(month + "/13"); datecomb.addItem(month + "/14"); datecomb.addItem(month + "/15"); datecomb.addItem(month + "/16"); datecomb.addItem(month + "/17"); datecomb.addItem(month + "/18");
		datecomb.addItem(month + "/19"); datecomb.addItem(month + "/20"); datecomb.addItem(month + "/21"); datecomb.addItem(month + "/22"); datecomb.addItem(month + "/23"); datecomb.addItem(month + "/24");
		datecomb.addItem(month + "/25"); datecomb.addItem(month + "/26"); datecomb.addItem(month + "/27"); datecomb.addItem(month + "/28"); datecomb.addItem(month + "/29"); datecomb.addItem(month + "/30");
		datecomb.addItem(month + "/31");
		datecomb.addItem(month2 + "/1"); datecomb.addItem(month2 + "/2"); datecomb.addItem(month2 + "/3"); datecomb.addItem(month2 + "/4"); datecomb.addItem(month2 + "/5"); datecomb.addItem(month2 + "/6");
		datecomb.addItem(month2 + "/7"); datecomb.addItem(month2 + "/8"); datecomb.addItem(month2 + "/9"); datecomb.addItem(month2 + "/10"); datecomb.addItem(month2 + "/11"); datecomb.addItem(month2 + "/12");
		datecomb.addItem(month2 + "/13"); datecomb.addItem(month2 + "/14"); datecomb.addItem(month2 + "/15"); datecomb.addItem(month2 + "/16"); datecomb.addItem(month2 + "/17"); datecomb.addItem(month2 + "/18");
		datecomb.addItem(month2 + "/19"); datecomb.addItem(month2 + "/20"); datecomb.addItem(month2 + "/21"); datecomb.addItem(month2 + "/22"); datecomb.addItem(month2 + "/23"); datecomb.addItem(month2 + "/24");
		datecomb.addItem(month2 + "/25"); datecomb.addItem(month2 + "/26"); datecomb.addItem(month2 + "/27"); datecomb.addItem(month2 + "/28");
		
		fromcomb.addItem("00:00"); fromcomb.addItem("01:00"); fromcomb.addItem("02:00"); fromcomb.addItem("03:00"); fromcomb.addItem("04:00"); fromcomb.addItem("05:00");
		fromcomb.addItem("06:00"); fromcomb.addItem("07:00"); fromcomb.addItem("08:00"); fromcomb.addItem("09:00"); fromcomb.addItem("10:00"); fromcomb.addItem("11:00");
		fromcomb.addItem("12:00"); fromcomb.addItem("13:00"); fromcomb.addItem("14:00"); fromcomb.addItem("15:00"); fromcomb.addItem("16:00"); fromcomb.addItem("17:00");
		fromcomb.addItem("18:00"); fromcomb.addItem("19:00"); fromcomb.addItem("20:00"); fromcomb.addItem("21:00"); fromcomb.addItem("22:00"); fromcomb.addItem("23:00");
		
		tocomb.addItem("00:00"); tocomb.addItem("01:00"); tocomb.addItem("02:00"); tocomb.addItem("03:00"); tocomb.addItem("04:00"); tocomb.addItem("05:00");
		tocomb.addItem("06:00"); tocomb.addItem("07:00"); tocomb.addItem("08:00"); tocomb.addItem("09:00"); tocomb.addItem("10:00"); tocomb.addItem("11:00");
		tocomb.addItem("12:00"); tocomb.addItem("13:00"); tocomb.addItem("14:00"); tocomb.addItem("15:00"); tocomb.addItem("16:00"); tocomb.addItem("17:00");
		tocomb.addItem("18:00"); tocomb.addItem("19:00"); tocomb.addItem("20:00"); tocomb.addItem("21:00"); tocomb.addItem("22:00"); tocomb.addItem("23:00");
		tocomb.setSelectedIndex(tocomb.getItemCount()-1);
	
		add(movielabel); 
		add(moviefield);
		add(datelabel); 
		add(datecomb);
		add(fromlabel);
		add(fromcomb);
		add(tolabel); 
		add(tocomb);
		add(ratinglabel);
		add(p1);
		add(directorlabel); 
		add(directorfield);
		add(awardlabel); 
		add(awardField);
		add(bc); 
		add(bs);
	}
	public void addButtonListener() {
		class ClickListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				directorfield.setText("");
				moviefield.setText("");
				awardField.setText("");
				datecomb.setSelectedIndex(0);
				fromcomb.setSelectedIndex(0);
				tocomb.setSelectedIndex(23);
				Gcheckb.setSelected(true);
				PGcheckb.setSelected(true);
				PG12checkb.setSelected(true);
				PG15checkb.setSelected(true);
				Rcheckb.setSelected(true);
			}
		}
		ActionListener listener=new ClickListener();
		bc.addActionListener(listener);
    }
	public void addButtonListener(JPanel panel, User user) {
		class ClickListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {		
				// ���o�U����쪺��
				String movieName = moviefield.getText();				
				String director = directorfield.getText();
				String date = String.valueOf(datecomb.getSelectedItem());
				//System.out.println(date);
				String timeStart = String.valueOf(fromcomb.getSelectedItem());
				String timeEnd = String.valueOf(tocomb.getSelectedItem());
				String award = String.valueOf(awardField.getText());
				
				new InfoPanel(panel, user, movieName, date, timeStart, timeEnd, ratings, director, award);
				((CardLayout)panel.getLayout()).show(panel, "result");
			}
		}
		ActionListener listener=new ClickListener();
		bs.addActionListener(listener);
    }
}

