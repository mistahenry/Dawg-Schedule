package helper;

import java.security.AllPermission;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.net.URL;
import model.*;
import javax.servlet.http.*;

public class Helper implements HttpSessionBindingListener  {
	private Connection conn;
	/**
	 * prepared statement to create the CourseOffering table
	 */
	private PreparedStatement createCourseOfferingStmt;
	/**
	 * prepared statement to drop CourseOffering table
	 */
	private PreparedStatement dropCourseOfferingStmt;
	/**
	 * prepared statement to read the CourseOffering csv into CourseOffering
	 */
	private PreparedStatement loadCourseOfferingStmt;
	/**
	 * prepared statement to add primary key
	 */
	private PreparedStatement addCourseOfferingPrimaryKeyStmt;
	/**
	 * prepared statement to create Course view
	 */
	private PreparedStatement createCoursesViewStmt;
	/**
	 * prepared statement to drop Courses view
	 */
	private PreparedStatement dropCoursesViewStmt;
	/**
	 * prepared statement to create Course table
	 */
	private PreparedStatement createCourseStmt;
	/**
	 * prepared statement to delete Course table
	 */
	private PreparedStatement dropCourseStmt;
	/**
	 * prepared statement to add coursePrimaryKey
	 */
	private PreparedStatement addCoursePrimaryKeyStmt;
	/**
	 * prepared statement to drop section table
	 */
	private PreparedStatement dropSectionStmt;
	/**
	 * prepared statement to add section table 
	 */
	private PreparedStatement createSectionTableStmt;
	/**
	 * prepared statement to add primary key to section table
	 */
	private PreparedStatement addSectionPrimaryKeyStmt;
	/**
	 * prepared statement to drop the meeting table
	 */
	private PreparedStatement dropMeetingTableStmt;
	/**
	 * prepared statement to create the meeting table
	 */
	private PreparedStatement createMeetingTableStmt;
	/**
	 * prepared statement to add primary key to meeting table
	 */
	private PreparedStatement addMeetingPrimaryKeyStmt;
	/**
	 * Statement to retrieve a list of sections for a particular course
	 */
	private PreparedStatement getSectionListStatement;
	/**
	 * Statement to retrieve a list of meetings for a particular section
	 */
	private PreparedStatement getMeetingListStatement;
	/**
	 * statement to get all the course prefixes
	 */
	private PreparedStatement getAllCoursePrefixesStmt;
	/**
	 * statement to get all courses of a prefix
	 */
	private PreparedStatement getAllCoursesStmt;
	/**
	 * statement to get a course of a particular id
	 */
	private PreparedStatement getCourseOfIdStmt;
	private PreparedStatement cleanMeetingStmt;
	private PreparedStatement getSearchPrefixesStmt;

	public Helper() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "willmba");
			createCourseOfferingStmt = conn.prepareStatement("create table CourseOffering(callNum varchar(6)," +
					"coursePrefix varchar(4), courseNum varchar(5),  title varchar(50), instructor varchar(60), available varchar(30), " +
					"credits varchar(7), days varchar(10), periodStart varchar(5), periodEnd varchar(5), " +
					"bldg varchar(12), room varchar(12))"); 
					
			dropCourseOfferingStmt = conn.prepareStatement("drop table if exists CourseOffering");
			String url = "/tmp/CourseOfferings.csv";
			loadCourseOfferingStmt = conn.prepareStatement("load data infile '"+ url + "' into table CourseOffering" + 
					" fields terminated by ',' lines terminated by '\r\n'");
			addCourseOfferingPrimaryKeyStmt = conn.prepareStatement("ALTER TABLE CourseOffering ADD id INT PRIMARY KEY AUTO_INCREMENT");
			createCoursesViewStmt = conn.prepareStatement("create view Courses as select Distinct courseNum,coursePrefix, title from CourseOffering where available = 'Available' and days != 'vr'");
			createCourseStmt = conn.prepareStatement("create table Course as select * from Courses");
			dropCourseStmt = conn.prepareStatement("drop table if exists Course");
			dropCoursesViewStmt = conn.prepareStatement("drop view if exists Courses");
			addCoursePrimaryKeyStmt = conn.prepareStatement("ALTER TABLE Course ADD id INT PRIMARY KEY AUTO_INCREMENT");
			dropSectionStmt = conn.prepareStatement("drop table if exists Section");
			createSectionTableStmt = conn.prepareStatement("create table Section as select Distinct o.callNum, c.id as courseId, o.credits, o.instructor from Course c, CourseOffering o" +
					" where c.coursePrefix = o.coursePrefix and c.courseNum = o.courseNum and o.credits != '0' and o.days != 'VR' and o.days != 'S' and o.days != 'AR'");
			addSectionPrimaryKeyStmt = conn.prepareStatement("ALTER TABLE Section ADD id INT PRIMARY KEY AUTO_INCREMENT");
			dropMeetingTableStmt = conn.prepareStatement("drop table if exists Meeting");
			createMeetingTableStmt = conn.prepareStatement("create table Meeting as select s.id as sectionId, o.days, o.periodStart, o.periodEnd from " +
					"Section s, CourseOffering o where s.callNum = o.callNum");
			addMeetingPrimaryKeyStmt = conn.prepareStatement("ALTER TABLE Meeting ADD id INT PRIMARY KEY AUTO_INCREMENT");
			getSectionListStatement = conn.prepareStatement("select * from Section where courseId=? order by id");
			getMeetingListStatement = conn.prepareStatement("select * from Meeting where sectionId=? order by id");
			getAllCoursePrefixesStmt = conn.prepareStatement("select Distinct coursePrefix from Course order by coursePrefix"); 
			getAllCoursesStmt = conn.prepareStatement("select * from Course where coursePrefix = ?");
			getCourseOfIdStmt = conn.prepareStatement("select * from Course where id = ?");
			cleanMeetingStmt = conn.prepareStatement("delete from Meeting where days = 'VR' or days = 'AR' or periodStart ='' or periodEnd = ''");
			getSearchPrefixesStmt = conn.prepareStatement("select Distinct coursePrefix from Course where coursePrefix like ?");
		}
		catch(Exception e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	/**
	 * creates the CourseOffering table
	 */
	public void createCourseOffering(){
		try{
			createCourseOfferingStmt.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * drops the CourseOffering table
	 */
	public void dropCourseOffering(){
		try{
			dropCourseOfferingStmt.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * load infile execution
	 */
	public void loadCourseOffering(){
		try{
			loadCourseOfferingStmt.executeUpdate();
			addCourseOfferingPrimaryKeyStmt.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
	/**
	 * create Course table
	 */
	public void createCourseTable(){
		try{
			dropCourseStmt.executeUpdate();
			dropCoursesViewStmt.executeUpdate();
			createCoursesViewStmt.executeUpdate();
			createCourseStmt.executeUpdate();
			addCoursePrimaryKeyStmt.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
	/**
	 * create Section table
	 */
	public void createSectionTable(){
		try{
			dropSectionStmt.executeUpdate();
			createSectionTableStmt.executeUpdate();
			addSectionPrimaryKeyStmt.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * create Meeting table
	 */
	public void createMeetingTable(){
		try{
			dropMeetingTableStmt.executeUpdate();
			createMeetingTableStmt.executeUpdate();
			addMeetingPrimaryKeyStmt.executeUpdate();
			cleanMeetingStmt.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * get all sections that belong to a particular course
	 * @param c the course
	 * @return list - an arrayList of all sections of course c
	 */
	public ArrayList<Section> getSectionList(int courseId){
		ArrayList<Section> list = new ArrayList<Section>();
		int id;
		String /*title,*/ instructor, callNum, creditHours;
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();
		try{
			getSectionListStatement.setInt(1, courseId);
			ResultSet set = getSectionListStatement.executeQuery();
			while(set.next()) {
				// set the received values to create a Section object
				id=set.getInt("id");
				callNum=set.getString("callNum");
				creditHours=set.getString("credits");
				instructor=set.getString("instructor");
				meetings = getMeetingList(id);
				Section section = new Section(id, callNum, creditHours, instructor, courseId, meetings);
				list.add(section);
			}
		}
		catch(Exception e) {
			System.out.println("Error retrieving Section List\n " + e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}
	/**
	 * get all meetings that belong to a particular section
	 * @param c the course
	 * @return list - an arrayList of all meetings of section c
	 */
	public ArrayList<Meeting> getMeetingList(int sectionId){
		ArrayList<Meeting> list = new ArrayList<Meeting>();
		int id;
		String timeStart, timeEnd;
		try{
			getMeetingListStatement.setInt(1, sectionId);
			ResultSet set = getMeetingListStatement.executeQuery();
			while(set.next()) {
				// set the received values to create a Meeting object
				id=set.getInt("id");
				timeStart=set.getString("periodStart");
				timeEnd=set.getString("periodEnd");
				ArrayList<String> days = new ArrayList<String>();
				String dayStr = set.getString("days");
				for(int i =0; i < dayStr.length(); i++){
					days.add(Character.toString(dayStr.charAt(i)));
				}
				for(String meetingDay:days){
					Meeting meeting = new Meeting(id, timeStart, timeEnd, meetingDay, sectionId);
					list.add(meeting);
				}
			}
		}
		catch(Exception e) {
			System.out.println("Error retrieving Section List\n " + e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}
	/**
	 * gets all the coursePrefixes
	 */
	public ArrayList<String> getCoursePrefixes(){
		ArrayList<String> prefixes = new ArrayList<String>();
		try{
			ResultSet rs = getAllCoursePrefixesStmt.executeQuery();
			while(rs.next()){
				prefixes.add(rs.getString("coursePrefix"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return prefixes;
	}
	/**
	 * gets all course of a prefix
	 */
	public ArrayList<Course> getCourses(String prefix){
		ArrayList<Course> courses = new ArrayList<Course>();
		try{
			getAllCoursesStmt.setString(1, prefix);
			ResultSet rs = getAllCoursesStmt.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				String num = rs.getString("courseNum");
				String title = rs.getString("title");
				Course course = new Course(id, title, prefix, num);
				courses.add(course);
			}			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return courses;
	}
	
	public Course getCourse(int id){
		try{
			getCourseOfIdStmt.setInt(1, id);
			ResultSet rs = getCourseOfIdStmt.executeQuery();
			while(rs.next()){
				String num = rs.getString("courseNum");
				String title = rs.getString("title");
				String prefix = rs.getString("coursePrefix");
				Course course = new Course(id, title, prefix, num);
				return course;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<String> getSearchPrefixes(String searchChar){
		ArrayList<String> prefixes = new ArrayList<String>();
		try{
			getSearchPrefixesStmt.setString(1, searchChar + "%");
			ResultSet rs = getSearchPrefixesStmt.executeQuery();
			while(rs.next()){
				prefixes.add(rs.getString("coursePrefix"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return prefixes;
	}
	/**
	* tests if their is a conflict
	* @author Will Henry
	*/
	public static boolean isConflict(Section sectionToAdd, Section section){
		
			 if(section.getCourseId() == sectionToAdd.getCourseId()){
				 return true;
			 }
		 	//checks if an existing section causes a conflict
		 	for(Meeting meeting: section.getMeetings()){
		 		//checks if a meeting in a section conflicts with all meetings in section to add
		 		int meetingStart = convertToMilitary(meeting.getMeetingStart());
		 		int meetingEnd = convertToMilitary(meeting.getMeetingEnd());
		 		for(Meeting meetingToAdd: sectionToAdd.getMeetings()){
		 			//a meeting in sectionToAdd is compared to meeting
		 			if(meetingToAdd.getMeetingDay().equals(meeting.getMeetingDay())){
		 				int meetingToAddStart = convertToMilitary(meetingToAdd.getMeetingStart());
		 				int meetingToAddEnd = convertToMilitary(meetingToAdd.getMeetingEnd());
		 				if(meetingToAddStart >= meetingStart && meetingToAddStart <= meetingEnd || 
		 					meetingToAddEnd >= meetingStart && meetingToAddEnd <= meetingEnd){
		 					return true;
		 				}
		 			}

		 		}
		 	}
		 
		 return false;
	}
	/**
	* converts time to military time
	* @author Will Henry
	*/
	public static int convertToMilitary(String time){
		int militaryHours = Integer.parseInt(time.substring(0,4))/100;
		int militaryMinutes = Integer.parseInt(time.substring(0,4))%100;
		if(time.charAt(4) == 'A'){
			if(militaryHours == 12){
				militaryHours = 0;
			}
			return militaryHours*100+militaryMinutes;
		}
		else if(time.charAt(4) == 'P' && militaryHours == 12){
			return militaryHours*100+militaryMinutes;
		}
		else{
			return militaryHours*100+militaryMinutes+1200;
		}
	}
	/**
	 * get all sections for a list of courses
	 */
	public ArrayList<Section> getAllSections(ArrayList<Course> courses){
		ArrayList<Section> allSections = new ArrayList<Section>();
		for(Course c: courses){
			ArrayList<Section> sections = getSectionList(c.getId());
			allSections.addAll(sections);
		}
		return allSections;
	}
	/**
	 * create Hashmap of Hashmap of Booleans (true is conflict)
	 */
	public static HashMap<Integer, HashMap<Integer, Boolean>> getConflictHashmap(ArrayList<Section> sections){
		HashMap<Integer, HashMap<Integer, Boolean>> conflictMap = new HashMap<Integer, HashMap<Integer, Boolean>>();
		for(Section s: sections){
			HashMap<Integer, Boolean> booleanMap = new HashMap<Integer, Boolean>();
			for(Section s1: sections){
				booleanMap.put(s1.getId(), isConflict(s1,s));
			}
			conflictMap.put(s.getId(), booleanMap);
		}
		return conflictMap;
		
	}
	/**
	 * creates an arrayList of non-conflicting sections
	 */
	public static boolean isConflict(Section s, ArrayList<Section> sections, HashMap<Integer, HashMap<Integer, Boolean>> conflictMap){
		HashMap<Integer, Boolean> booleanMap = conflictMap.get(s.getId());
		boolean isConflict = false;
		for(Section s1: sections){
			//conflict map so true means is a conflict
			if(booleanMap.get(s1.getId())){
				isConflict = true;
				break;
			}
		}
		return isConflict;
	}
	public static ArrayList<Section> eliminateDayConstraintSections(ArrayList<Section> allSections, ArrayList<String> daysNotAllowed){
		ArrayList<Section> goodToGoSections = new ArrayList<Section>();
		for(Section s: allSections){
			boolean noConflict = true;
			for(Meeting meeting: s.getMeetings()){
				for(String day: daysNotAllowed){
		 			if(meeting.getMeetingDay().equals(day)){
		 				noConflict = false;
		 				break;
		 			}
		 		}
			}
			if(noConflict){
				goodToGoSections.add(s);
			}
			
		}
		return goodToGoSections;
	}
	/**
	 * returns an arraylist of all sections that meet time constraints
	 */
	public static ArrayList<Section> eliminateTimeConstraintSections(ArrayList<Section> allSections, int dayStart, int dayEnd){
		ArrayList<Section> goodToGoSections = new ArrayList<Section>();

		for(Section s: allSections){
			boolean noConflict = true;
			for(Meeting meeting: s.getMeetings()){
				int meetingStart = convertToMilitary(meeting.getMeetingStart());
		 		int meetingEnd = convertToMilitary(meeting.getMeetingEnd());
		 		if(meetingStart < dayStart || meetingEnd > dayEnd){
		 			noConflict = false;
		 			break;
		 		} 
			}
			if(noConflict){
				goodToGoSections.add(s);
			}

		}
		return goodToGoSections;
	} 
	
	/**
	 * gets all the combinations of the list that you pass in as a parameter
	 */
	public static ArrayList<ArrayList<Section>> combination(ArrayList<Section> list, int maxCredits, HashMap<Integer, HashMap<Integer, Boolean>> conflictMap) {
		ArrayList<ArrayList<Section>> ps = new ArrayList<ArrayList<Section>>();
        ps.add(new ArrayList<Section>());
        System.out.println(list.size());
        // for every item in the original list
        for (Section item : list) {
        	ArrayList<ArrayList<Section>> newPs = new ArrayList<ArrayList<Section>>();
            for (ArrayList<Section> subset : ps) {
                // copy all of the current powerset's subsets
                newPs.add(subset);
 
                // plus the subsets appended with the current item
                ArrayList<Section> newSubset = new ArrayList<Section>(subset);
                if(!isConflict(item, newSubset, conflictMap)){ 
                    newSubset.add(item);
                    int credits = 0;
                    for(Section s: newSubset){
                    	credits += Integer.parseInt(s.getCreditHours());
                    }
                    if(credits <= maxCredits){
                        newPs.add(newSubset);
                    }
                }
               
            }
            // powerset is now powerset of list.subList(0, list.indexOf(item)+1)
            ps = newPs;
        }
        return ps;
    }
	
	/**
	 * 
	 */
	public static ArrayList<Schedule> getSchedules(ArrayList<Section> requiredSections, int minCredits,
			ArrayList<ArrayList<Section>> combinations, HashMap<Integer, HashMap<Integer, Boolean>> conflictMap){
		
		ArrayList<Schedule> schedules = new ArrayList<Schedule>();
		for(ArrayList<Section> aCombo: combinations){
			int credits = 0;
			boolean found = false;
			boolean possibleSchedule = true;
			int sectionsFound = 0;
			for(Section s: aCombo){
				credits += Integer.parseInt(s.getCreditHours());
			}
			if(credits >= minCredits){
				for(Section rs:requiredSections){
					found = false;
					for(Section s: aCombo){
						if(s.getId() == rs.getId()){
							found = true;
							break;
						}
					}
					if(found){
						sectionsFound++;
					}	
				}
				if(sectionsFound == requiredSections.size()){
					Schedule schedule = new Schedule(aCombo, credits);
					for(Section s: schedule.getSections()){
						s.setTitle();
					}
					schedules.add(schedule);
				}				
			}
		}
		return schedules;
	}
	public Connection getConn() {
		return conn;
	}
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	public void valueBound(HttpSessionBindingEvent event) {  
	    // do nothing -- the session-scoped bean is already bound  
	  }  
	    
	  public void valueUnbound(HttpSessionBindingEvent event) {
	    try {   
	      if (conn != null) conn.close();  
	      
	    }  
	    catch (Exception ignored) {
	    	ignored.printStackTrace();
	    }  
	  }  
}
