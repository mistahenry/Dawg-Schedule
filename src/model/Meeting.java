package model;
/**
 * the model class for a meeting. A Meeting represents a physical meeting time that takes place within a section
 */
public class Meeting {

	private int id;
	private String meetingStart;
	private String meetingEnd;
	private String meetingDay;
	private int sectionId;

	/**
	 * @param id
	 * @param timeStart
	 * @param timeEnd
	 * @param meetingDay
	 * @param roomNumber
	 * @param buildingNumber
	 * @param section
	 */
	public Meeting(int id, String timeStart, String timeEnd, String meetingDay, int sectionId) {
		this.id = id;
		this.meetingStart = timeStart;
		this.meetingEnd = timeEnd;
		this.meetingDay = meetingDay;

		this.sectionId = sectionId;
	}

	/**
	 * getter for the id of the meeting
	 * @return id - the id of the meeting
	 */
	public int getId() {
		return id;
	}



	/**
	 * getter for the meeting day of the meeting
	 * @return meetingDay - the meeting day of the meeting
	 */
	public String getMeetingDay() {
		return meetingDay;
	}



	/**
	 * getter for the section
	 * @return section - the section that the meeting belongs to
	 */
	public int getSectionId() {
		return sectionId;
	}

	public String getMeetingStart() {
		return meetingStart;
	}

	public String getMeetingEnd() {
		return meetingEnd;
	}
}