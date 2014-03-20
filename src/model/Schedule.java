 package model;
import java.util.ArrayList;
public class Schedule {
	private ArrayList<Section> sections;
	int hours;
	
	public Schedule(ArrayList<Section> sections, int hours) {
		this.sections = sections;
		this.hours = hours;
	}
	
	public ArrayList<Section> getSections() {
		return sections;
	}
	public int getHours() {
		return hours;
	}
}
