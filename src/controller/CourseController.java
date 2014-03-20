package controller;

import helper.Helper;
import java.util.*;

import model.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;



/**
 * Servlet implementation class CourseController
 */
@WebServlet("/CourseController")
public class CourseController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CourseController() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext ctx = this.getServletContext();
		HttpSession session = request.getSession();
		Helper helper = (Helper)session.getAttribute("helper");
		if(helper == null){
			helper = new Helper();
			session.setAttribute("helper", helper);
		}
		System.out.println("exectued");
		int monStart = Integer.parseInt(request.getParameter("monday-start"));
		int tuesStart = Integer.parseInt(request.getParameter("tuesday-start"));
		int wedStart = Integer.parseInt(request.getParameter("wednesday-start"));
		int thursStart = Integer.parseInt(request.getParameter("thursday-start"));
		int friStart = Integer.parseInt(request.getParameter("friday-start"));
		
		int monEnd = Integer.parseInt(request.getParameter("monday-end"));
		int tuesEnd = Integer.parseInt(request.getParameter("tuesday-end"));
		int wedEnd = Integer.parseInt(request.getParameter("wednesday-end"));
		int thursEnd = Integer.parseInt(request.getParameter("thursday-end"));
		int friEnd = Integer.parseInt(request.getParameter("friday-end"));

		ArrayList<Section> allSections = helper.getAllSections((ArrayList<Course>)session.getAttribute("currentCourses"));
		ArrayList<String> badDays = new ArrayList<String>();
		if(monStart == 0){
			badDays.add("M");
		}else{
			allSections = Helper.eliminateTimeConstraintSections(allSections, monStart, monEnd);
		}
		if(tuesStart == 0){
			badDays.add("T");
		}else{
			allSections = Helper.eliminateTimeConstraintSections(allSections, tuesStart, tuesEnd);
		}
		if(wedStart == 0){
			badDays.add("W");
		}else{
			allSections = Helper.eliminateTimeConstraintSections(allSections, wedStart, wedEnd);
		}
		if(thursStart == 0){
			badDays.add("R");
		}else{
			allSections = Helper.eliminateTimeConstraintSections(allSections, thursStart, thursEnd);
		}
		if(friStart == 0){
			badDays.add("F");
		}else{
			allSections = Helper.eliminateTimeConstraintSections(allSections, friStart, friEnd);
		}
		allSections = Helper.eliminateDayConstraintSections(allSections, badDays);
		ArrayList<Section> required = new ArrayList<Section>();
		HashMap<Integer, HashMap<Integer, Boolean>> conflictMap = Helper.getConflictHashmap(allSections);
		int maxCredits = Integer.parseInt(request.getParameter("max-credits"));
		ArrayList<ArrayList<Section>> combos = Helper.combination(allSections, maxCredits, conflictMap);
		int minCredits = Integer.parseInt(request.getParameter("min-credits"));
		ArrayList<Schedule> schedules = Helper.getSchedules(required, minCredits, combos, conflictMap);
		if(schedules.size() == 0){
			RequestDispatcher dispatcher = ctx.getRequestDispatcher("/index.jsp");
			request.setAttribute("error", true);
			dispatcher.forward(request, response);
		}else{
			request.setAttribute("schedules", schedules);
			RequestDispatcher dispatcher = ctx.getRequestDispatcher("/schedules.jsp");
			dispatcher.forward(request, response);
			
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		Helper helper = (Helper)session.getAttribute("helper");
		if(helper == null){
			helper = new Helper();
			session.setAttribute("helper", helper);
		}

		String coursePrefix = request.getParameter("coursePrefix");
		String courseId = request.getParameter("courseId");
		String drop = request.getParameter("drop");
		String searchChar = request.getParameter("searchChar");

		//gets all courses for a prefix
		if(coursePrefix != null){
			
			ArrayList<Course> courseList = helper.getCourses(coursePrefix);
			session.setAttribute("coursesForPrefix", courseList);
			return;
		}
		//adds a course to the current courses
		else if(courseId != null){
			int c_id = Integer.parseInt(courseId);
			if(session.getAttribute("currentCourses") != null){
				ArrayList<Course> currentCourses = (ArrayList<Course>)session.getAttribute("currentCourses");
				Course c = helper.getCourse(c_id);
				currentCourses.add(c);
				return;
			}else{
				ArrayList<Course> currentCourses = new ArrayList<Course>();
				Course c = helper.getCourse(c_id);
				currentCourses.add(c);
				session.setAttribute("currentCourses", currentCourses);
				return;
			}	
		}
		//drops a course
		else if(drop != null){
			//drop = id_drop
			int id = Integer.parseInt(drop.split("_")[0]);
			ArrayList<Course> currentCourses = (ArrayList<Course>)session.getAttribute("currentCourses");
			for(Course c: currentCourses){
				if(c.getId() == id){
					currentCourses.remove(c);
					break;
				}
			}
			return;
		}
		else if(searchChar != null){
			ArrayList<String> searchPrefixes = helper.getSearchPrefixes(searchChar);
			session.setAttribute("searchPrefixes", searchPrefixes);
			return;
		}
	}

}
