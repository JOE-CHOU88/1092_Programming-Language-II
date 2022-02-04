import java.sql.*;
import java.util.ArrayList;

import javax.swing.JCheckBox;

public class SQLExec {
	private String url = "jdbc:mysql://localhost:3306/fuzhong15?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8"; //jdbc:mysql://140.119.19.73:9306/TG15?useUnicode=true&characterEncoding=utf8
	private String user = "root";
	private String password = "";
	Connection conn;
	
	public SQLExec(){
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("SQLExec18: Cannot connect!");
		}
	}
	
	public ResultSet getAllMovies() throws SQLException, NullPointerException{
		String sql = "SELECT * "
				+ "FROM schedule "
				+ "JOIN movie "
				+ "ON schedule.movie_id = movie.id "
				+ "JOIN rating "
				+ "ON movie.rating_id = rating.id "
				+ "JOIN theater "
				+ "ON movie.theater_id = theater.id "
//				+ "WHERE date Like '%_07_%' "
				+ "ORDER BY date ASC, time ASC";
//		System.out.println(sql);
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		return rs;
	}
	public ResultSet searchMovies(String movieName, String date, String timeStart, String timeEnd, ArrayList<JCheckBox> ratings, String director, String award) throws SQLException, NullPointerException{
//		System.out.println("<---------------searchMovies--------------->");
//		System.out.println("convert before: " + date);
		date = Helper.convertDateFormat(date);
		System.out.println("convert after: " + date);
		timeStart = Helper.convertTimeFormat(timeStart);
		System.out.println(timeStart);
		timeEnd = Helper.convertTimeFormat(timeEnd);
		System.out.println(timeEnd);
		
		
		String sql = "SELECT * "
				+ "FROM schedule "
				+ "JOIN movie "
				+ "ON schedule.movie_id = movie.id "
				+ "JOIN rating "
				+ "ON movie.rating_id = rating.id "
				+ "JOIN theater "
				+ "ON movie.theater_id = theater.id "
				+ "WHERE name LIKE ? AND "
				+ "date LIKE ? AND "
				+ "time >= ? AND "
				+ "time <= ? AND "
				+ "(rating.type = ? OR "
				+ "rating.type = ? OR "
				+ "rating.type = ? OR "
				+ "rating.type = ? OR "
				+ "rating.type = ?) AND "
				+ "director LIKE ? AND "
				+ "award LIKE ? "
				+ "ORDER BY date ASC, time ASC";
		
		if (award.equals("")) {
			sql = sql.replace("award LIKE ?", "(award LIKE ? OR award IS NULL) ");
		}
		
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, "%" + movieName + "%");
		ps.setString(2, "%" + date);
		ps.setString(3, timeStart);
		ps.setString(4, timeEnd);
		for (int i = 5; i <= 9; i++) {
			if (ratings.get(i-5).isSelected()){
				ps.setString(i, ratings.get(i-5).getText());
			}else {
				ps.setString(i, "_");
			}
		}
		ps.setString(10, "%" + director + "%");
		ps.setString(11, "%" + award + "%");
		System.out.println(ps.toString());
		ResultSet rs = ps.executeQuery();
		
		return rs;
	}
	public ResultSet getTracingScheduleIDs (String userID) throws SQLException, NullPointerException{
		
		String sql = "SELECT * FROM tracing where user_id = ?";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, userID);
		
		if(! this.findTable("Tracing")) {
			createTracingTable();
			System.out.println("Create tracing table!!!!!Done");
		}
		
		ResultSet rs = ps.executeQuery();

		return rs;
	}
	public ResultSet getTracingMovies (String userID) throws SQLException, NullPointerException{
		
		String sql = "SELECT * "
				+ "FROM tracing "
				+ "JOIN schedule "
				+ "ON tracing.schedule_id = schedule.id "
				+ "JOIN movie "
				+ "ON schedule.movie_id = movie.id "
				+ "JOIN rating "
				+ "ON movie.rating_id = rating.id "
				+ "JOIN theater "
				+ "ON movie.theater_id = theater.id "
				+ "WHERE tracing.user_id = ? "
				+ "ORDER BY date ASC, time ASC";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, userID);
		
		ResultSet rs = ps.executeQuery();

		return rs;
	}
	public boolean findTable(String tableName) throws SQLException, NullPointerException{
		PreparedStatement stat = conn.prepareStatement("SHOW TABLES LIKE ?");
		stat.setString(1, tableName);
		if(stat.executeQuery().next()) {
			return true;
		}else {
			return false;
		}

	}
	public boolean createUserTable() throws SQLException, NullPointerException{
		String sql = "CREATE TABLE IF NOT EXISTS User"
				+ " (ID INT NOT NULL AUTO_INCREMENT,"
				+ " UserID VARCHAR(100) NOT NULL,"
				+ " Password VARCHAR(100) NOT NULL,"
				+ " Trace VARCHAR(10000),"
				+ " PRIMARY KEY (ID))";
		PreparedStatement createTableStat = conn.prepareStatement(sql);
		if(createTableStat.executeUpdate() == 0) {
			return true;
		}else {
			return false;
		}
	}
	public boolean addUser(String UserID, String password) throws SQLException, NullPointerException{
			String sql = "INSERT INTO User(UserID, Password) VALUES(?,?)";
			PreparedStatement createTableStat = conn.prepareStatement(sql);
			createTableStat.setString(1, UserID);
			createTableStat.setString(2, password);
			if(createTableStat.executeUpdate() == 1) {
				return true;
			}else {
				return false;
			}	
	}
	public boolean login(String userID, String password) throws SQLException, NullPointerException{
		String sql = "SELECT UserID, Password FROM User WHERE UserID = ? AND Password = ?";
		PreparedStatement loginstmt = conn.prepareStatement(sql);

		loginstmt.setString(1, userID);
		loginstmt.setString(2, password);
		//System.out.println("---------" + loginstmt + "---------");
		if(! this.findTable("User")) {
			createUserTable();
			System.out.println("Create user table!!!!!Done");
		}
		ResultSet result = loginstmt.executeQuery();
		if(result.next()) {
			return true;
		}
		return false;
	}
	public boolean isUserIDExist(String userID) throws SQLException, NullPointerException{
			String sql = "SELECT UserID FROM User WHERE UserID = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userID);
			ResultSet result = stmt.executeQuery();
			if(result.next()) {
				return true;
			}
			return false;

	}
	public boolean createTracingTable() throws SQLException, NullPointerException{
		String sql = "CREATE TABLE IF NOT EXISTS Tracing"
				+ " (ID INT NOT NULL AUTO_INCREMENT,"
				+ " user_ID VARCHAR(100) NOT NULL,"
				+ " schedule_ID VARCHAR(100) NOT NULL,"
				+ " PRIMARY KEY (ID))";
		PreparedStatement createTableStat = conn.prepareStatement(sql);
		if(createTableStat.executeUpdate() == 0) {
			return true;
		}else {
			return false;
		}
	}
	public boolean addTracing(String userID, int scheduleID) throws SQLException, NullPointerException{
		String sql = "INSERT INTO tracing(user_id, schedule_id) VALUES(?,?)";
		PreparedStatement insertStmt = conn.prepareStatement(sql);
		insertStmt.setString(1, userID);
		insertStmt.setInt(2, scheduleID);
		int ret = insertStmt.executeUpdate();
		if(ret == 1) {
			return true;
		}
		return false;
	}
	public boolean deleteTracing(String userID, int scheduleID) throws SQLException, NullPointerException{
		String sql = "DELETE FROM tracing WHERE user_id = ? AND schedule_id = ?";
		PreparedStatement deleteStmt = conn.prepareStatement(sql);
		deleteStmt.setString(1, userID);
		deleteStmt.setInt(2, scheduleID);
		int ret = deleteStmt.executeUpdate();
		if(ret == 1) {
			return true;
		}
		return false;
	}
	public boolean deleteAllTracing(String userID) throws SQLException, NullPointerException{
		String sql = "DELETE FROM tracing WHERE user_id = ?";
		PreparedStatement deleteStmt = conn.prepareStatement(sql);
		deleteStmt.setString(1, userID);
		int ret = deleteStmt.executeUpdate();
		if(ret == 1) {
			return true;
		}
		return false;
	}
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException | NullPointerException e) {
			// TODO Auto-generated catch block
			// swallow all error
			;
		}
	}

}
