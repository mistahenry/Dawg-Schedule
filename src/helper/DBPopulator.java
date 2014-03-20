package helper;

import java.io.*;
import java.net.*;
import java.util.*;

import model.*;


/**
 * This class pulls the latest section data from uga.edu and populates the section and meeting tables
 */
public class DBPopulator {
	public static void main(String[] args) { 
		Helper helper = new Helper();
		helper.dropCourseOffering();
		helper.createCourseOffering();
		createCsv();
		System.out.println("loading columns");
		helper.loadCourseOffering();
		helper.createCourseTable();
		helper.createSectionTable();
		helper.createMeetingTable();
		
	}
	private static void createCsv(){
		try {
			System.out.println("reading csv to tmp");
			URL url = new URL("https://apps.reg.uga.edu/reporting/static_reports/course_offering_UNIV_201408.csv");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			TrustModifier.relaxHostChecking(conn);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			PrintWriter writer = new PrintWriter("/tmp/CourseOfferings.csv", "UTF-8");
			String str;
			// read a line
			while ((str = in.readLine()) != null) {
				String fixedCsv = "";
				ArrayList<String> line = new ArrayList<String>(0);
				//line.addAll(Arrays.asList(str.split("\\s*,\\s*")));
				line.addAll(Arrays.asList(str.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)")));
				for (int j = 0; j < line.size(); j++) {
					if(j !=4 && j!=5){
						line.set(j, line.get(j).replaceAll("\\s",""));
					}
					line.set(j, line.get(j).substring(1,line.get(j).length()-1));						
				}
				removeUselessColumns(line);

				for(int i = 0; i < line.size(); i++){
					String toAdd = line.get(i).trim();
					if(i == 4 || i == 3){
						String commaless = "";
						for(int j = 0; j < toAdd.length(); j++){
							if(toAdd.charAt(j) != ','){
								commaless += toAdd.charAt(j);
							}
						}
						toAdd = commaless;
					}
				
					fixedCsv += toAdd;
					if(i != line.size() -1){
						fixedCsv += ",";
					}
					else{
					}
				}
				fixedCsv+="\r\n";
				writer.print(fixedCsv);
			}
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private static void removeUselessColumns(ArrayList<String> list) {

		list.remove(22);
		list.remove(21);
		list.remove(18);
		list.remove(17);
		list.remove(16);
		list.remove(15);
		list.remove(14);
		list.remove(13);
		list.remove(12);
		list.remove(8);
		list.remove(0);

		if (list.get(4).equals("null")) {
			list.set(4, "STAFF");
		}

		list.set(6, list.get(6).substring(0,1));	
	}
}