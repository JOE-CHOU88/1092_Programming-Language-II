import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
	private String userID;
	private String passward;
	private ArrayList<Integer> tracingList;
	public User(String userID, String passward) {
		
		this.userID = userID;
		this.passward = passward;
		this.tracingList = new ArrayList<>();
		
		SQLExec exec = new SQLExec();
		ResultSet rs;
		try {
			rs = exec.getTracingScheduleIDs(userID);
			while (rs.next()){
				tracingList.add(rs.getInt("schedule_id"));
			}
		} catch (NullPointerException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("無法從伺服器讀取追蹤清單");
		}
	}
	public String getUserID() {
		return userID;
	}
	public String getPassward() {
		return passward;
	}
	public ArrayList getTracingList() {
		return this.tracingList;
	}
	public void addTracing(int scheduleIdx) {
		this.tracingList.add(scheduleIdx);
	}
}
