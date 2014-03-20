package model;
/**
 * the model class for a section. A requirement is made up of many courses
 * @author Will Henry
 *
 */
public class Course {

	private int id;
	private String coursePrefix;
	private String courseNum;
	private String title;

	/**
	 * @param id
	 * @param reqFulfilled
	 * @param coursePrefix
	 * @param courseNum
	 * @author Will Henry
	 */
	public Course(int id, String title, String coursePrefix,
			String courseNum) {
		this.id = id;
		this.title = title;
		this.coursePrefix = coursePrefix;
		this.courseNum = courseNum;
	}

	/**
	 * getter for the id of the Course
	 * @return id - the id of the course
	 * @author Will Henry
	 */
	public int getId() {
		return id;
	}


	/**
	 * getter for the course prefix
	 * @return coursePrefix - the prefix of the course
	 * @author Will Henry
	 */
	public String getCoursePrefix() {
		return coursePrefix;
	}

	/**
	 * getter for the course number
	 * @return courseNum - the course number 
	 * @author Will Henry
	 */
	public String getCourseNum() {
		return courseNum;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
}