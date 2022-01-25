import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import java.util.Arrays;

public class GoogleCalender {
    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GoogleCalender.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static void update(User user) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        
    	// Iterate over the events in the specified calendar
        String pageToken = null;
        do {
          Events events = service.events().list("primary").setPageToken(pageToken).execute();
          List<Event> items = events.getItems();
          for (Event event : items) {
        	String eventName = event.getSummary();
        	if (eventName != null) {
        		if (eventName.contains("府中15")) {
                	service.events().delete("primary", event.getId()).execute();
                }
        	}
          }
          pageToken = events.getNextPageToken();
        } while (pageToken != null);
        
        SQLExec exec = new SQLExec();
        ResultSet rs = null;
        try {
    		rs = exec.getTracingMovies(user.getUserID());
    		rs.beforeFirst();
   			while (rs.next()) {
   			
			Event event = new Event()
		        	    .setSummary(rs.getString("name") + "(府中15)")
		        	    .setLocation("新北市板橋區府中路15號B1")
		        	    .setDescription(rs.getString("description"));

			Date date = rs.getDate("date");
			Time startTime = rs.getTime("time");
			
	    	DateTime startDateTime = new DateTime(String.format("%sT%s+08:00", date, startTime));
	    	EventDateTime start = new EventDateTime().setDateTime(startDateTime);
	    	event.setStart(start);
	    	
	    	DateTime endDateTime = new DateTime(String.format("%sT%s+08:00", date, startTime));
	    	EventDateTime end = new EventDateTime().setDateTime(endDateTime);
	    	event.setEnd(end);

	    	String calendarId = "primary";
	    	event = service.events().insert(calendarId, event).execute();
	    	System.out.printf("Event created: %s\n", event.getHtmlLink());
   			}
			
		} catch (SQLException | NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			exec.closeConnection();
		}
       
		JOptionPane.showMessageDialog(null, "Action Complete!", "Google Calender", JOptionPane.INFORMATION_MESSAGE);
    }
}