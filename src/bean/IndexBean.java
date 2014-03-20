package bean;
import java.util.ArrayList;
import helper.Helper;
public class IndexBean {
	private ArrayList<String> coursePrefixes = new ArrayList<String>(); 
	public IndexBean(){
		Helper helper = new Helper();
		coursePrefixes = helper.getCoursePrefixes();
	}
	public ArrayList<String> getCoursePrefixes() {
		return coursePrefixes;
	}
	public void setCoursePrefixes(ArrayList<String> coursePrefixes) {
		this.coursePrefixes = coursePrefixes;
	}
}
