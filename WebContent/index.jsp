<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean class="bean.IndexBean" id = "bean" scope="session"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dawg Schedule</title>
<link rel="stylesheet" type="text/css" href="theme.css">


	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
	<script src="./alljs.js"></script>
</head>
<body>
<div class="wrapper">
<h1 class="header">Dawg Schedule- Finding You the Perfect Schedule</h3>

<h4 class="description">Do you hate Friday classes? Wish you didn't have to take class before 11 am every single day? Follow the steps below, and we will try to find you a schedule that meets all your needs</h4>
<% if(request.getAttribute("error") != null){ %>
	<h4 class="error">Error: no schedules fit your constraints. Either add more courses that you could take or reduce the time constraints reducing the number of days
	where you want no class or widening the time interval. Some courses might always conflict. Keep trying!
	</h4>
<%} %>
<div id="courseAccordion" class="courseAccordion">
	<h3>Step 1: Select all the courses that you are considering taking (as many as 10)</h3>
	<form>
		<p>Search:<input class="search" type="text" name="searchVal">
 	</form>
 	<div class="accordion">
 		<jsp:include page="courseAccordion.jsp"/>		
 	</div>
</div>
<div class="currentCourses">
<div id="current-course-list" >
	
	<jsp:include page="currentCourses.jsp"/>
	
</div>
<div>
	<h3>Step 3: Choose the times you want class on each day (no class for a day is an option in the start dropdowns)</h3>
	<form action="Controller" method="get">
	<table border="1">
		<th>Monday</th><th>Tuesday</th><th>Wednesday</th><th>Thursday</th><th>Friday</th>
		<tr><td>Start</td><td>Start</td><td>Start</td><td>Start</td><td>Start</td></tr>
		<tr><td><select name="monday-start">
			<option selected="selected" value="800">8 am</option>
			<option value="900">9 am</option>
			<option value="1000">10 am</option>
			<option value="1100">11 am</option>
			<option value="1200">12 pm</option>
			<option value="1300">1 pm</option>
			<option value="1400">2 pm</option>
			<option value="1500">3 pm</option>
			<option value="1600">4 pm</option>
			<option value="1700">5 pm</option>
			<option value="0">No class</option>	
		</select>
		</td>
		<td><select name="tuesday-start">
			<option selected="selected" value="800">8 am</option>
			<option value="900">9 am</option>
			<option value="1000">10 am</option>
			<option value="1100">11 am</option>
			<option value="1200">12 pm</option>
			<option value="1300">1 pm</option>
			<option value="1400">2 pm</option>
			<option value="1500">3 pm</option>
			<option value="1600">4 pm</option>
			<option value="1700">5 pm</option>
			<option value="0">No class</option>
				
		</select>
		</td>
		<td><select name="wednesday-start">
			<option selected="selected" value="800">8 am</option>
			<option value="900">9 am</option>
			<option value="1000">10 am</option>
			<option value="1100">11 am</option>
			<option value="1200">12 pm</option>
			<option value="1300">1 pm</option>
			<option value="1400">2 pm</option>
			<option value="1500">3 pm</option>
			<option value="1600">4 pm</option>
			<option value="1700">5 pm</option>
			<option value="0">No class</option>
				
		</select>
		</td>
		<td><select name="thursday-start">
			<option selected="selected" value="800">8 am</option>
			<option value="900">9 am</option>
			<option value="1000">10 am</option>
			<option value="1100">11 am</option>
			<option value="1200">12 pm</option>
			<option value="1300">1 pm</option>
			<option value="1400">2 pm</option>
			<option value="1500">3 pm</option>
			<option value="1600">4 pm</option>
			<option value="1700">5 pm</option>
			<option value="0">No class</option>
				
		</select>
		</td>
		<td><select name="friday-start">
			<option selected="selected" value="800">8 am</option>
			<option value="900">9 am</option>
			<option value="1000">10 am</option>
			<option value="1100">11 am</option>
			<option value="1200">12 pm</option>
			<option value="1300">1 pm</option>
			<option value="1400">2 pm</option>
			<option value="1500">3 pm</option>
			<option value="1600">4 pm</option>
			<option value="1700">5 pm</option>
			<option value="0">No class</option>	
				
		</select>
		</td>
		</tr>
		<tr><td>End</td><td>End</td><td>End</td><td>End</td><td>End</td></tr>
		<tr><td><select name="monday-end">
			<option value="900">9 am</option>
			<option value="1000">10 am</option>
			<option value="1100">11 am</option>
			<option value="1200">12 pm</option>
			<option value="1300">1 pm</option>
			<option value="1400">2 pm</option>
			<option value="1500">3 pm</option>
			<option value="1600">4 pm</option>
			<option value="1700">5 pm</option>
			<option selected="selected" value="1800">6 pm</option>	
			<option value="1900">7 pm</option>	
			<option value="2000">8 pm</option>	
			<option value="2100">9 pm</option>	
			<option value="2200">10 pm</option>	
				
		</select>
		</td>
		<td><select name="tuesday-end">
			<option value="900">9 am</option>
			<option value="1000">10 am</option>
			<option value="1100">11 am</option>
			<option value="1200">12 pm</option>
			<option value="1300">1 pm</option>
			<option value="1400">2 pm</option>
			<option value="1500">3 pm</option>
			<option value="1600">4 pm</option>
			<option value="1700">5 pm</option>
			<option selected="selected" value="1800">6 pm</option>	
			<option value="1900">7 pm</option>	
			<option value="2000">8 pm</option>	
			<option value="2100">9 pm</option>	
			<option value="2200">10 pm</option>		
				
		</select>
		</td>
		<td><select name="wednesday-end">
			<option value="900">9 am</option>
			<option value="1000">10 am</option>
			<option value="1100">11 am</option>
			<option value="1200">12 pm</option>
			<option value="1300">1 pm</option>
			<option value="1400">2 pm</option>
			<option value="1500">3 pm</option>
			<option value="1600">4 pm</option>
			<option value="1700">5 pm</option>
			<option selected="selected" value="1800">6 pm</option>	
			<option value="1900">7 pm</option>	
			<option value="2000">8 pm</option>	
			<option value="2100">9 pm</option>	
			<option value="2200">10 pm</option>		
				
		</select>
		</td>
		<td><select name="thursday-end">
			<option value="900">9 am</option>
			<option value="1000">10 am</option>
			<option value="1100">11 am</option>
			<option value="1200">12 pm</option>
			<option value="1300">1 pm</option>
			<option value="1400">2 pm</option>
			<option value="1500">3 pm</option>
			<option value="1600">4 pm</option>
			<option value="1700">5 pm</option>
			<option selected="selected" value="1800">6 pm</option>	
			<option value="1900">7 pm</option>	
			<option value="2000">8 pm</option>	
			<option value="2100">9 pm</option>	
			<option value="2200">10 pm</option>		
				
		</select>
		</td>
		<td><select name="friday-end">
			<option value="900">9 am</option>
			<option value="1000">10 am</option>
			<option value="1100">11 am</option>
			<option value="1200">12 pm</option>
			<option value="1300">1 pm</option>
			<option value="1400">2 pm</option>
			<option value="1500">3 pm</option>
			<option value="1600">4 pm</option>
			<option value="1700">5 pm</option>
			<option selected="selected" value="1800">6 pm</option>	
			<option value="1900">7 pm</option>	
			<option value="2000">8 pm</option>	
			<option value="2100">9 pm</option>	
			<option value="2200">10 pm</option>		
				
		</select>
		</td>
	</table>
	<h3>Step 4: Set how many credit hours you want </h3>
	<table border="1">
	<th>Min Credits</th><th>Max Credits</th>
	<tr>
	<td>
	<select name="min-credits">
		<option value="5">5</option>
		<option value="6">6</option>
		<option value="7">7</option>
		<option value="8">8</option>
		<option value="9">9</option>
		<option value="10">10</option>
		<option value="11">11</option>
		<option selected="selected" value="12">12</option>
		<option value="13">13</option>
		<option value="14">14</option>
		<option value="15">15</option>
		<option value="16">16</option>
		<option value="17">17</option>
		<option value="18">18</option>
		<option value="19">19</option>
		<option value="20">20</option>
	</select>
	</td>
	<td>
	<select name="max-credits">
		<option value="6">6</option>
		<option value="7">7</option>
		<option value="8">8</option>
		<option value="9">9</option>
		<option value="10">10</option>
		<option value="11">11</option>
		<option value="12">12</option>
		<option value="13">13</option>
		<option value="14">14</option>
		<option selected="selected" value="15">15</option>
		<option value="16">16</option>
		<option value="17">17</option>
		<option value="18">18</option>
		<option value="19">19</option>
		<option value="20">20</option>
	</select>
	</td>
	</tr>
	</table>
	
	<h3>Step 5: Click the button to see if your perfect schedule is possible</h3>
	<input class="generate" type="submit" value="Generate Schedules">
	</form>
</div>
</div>

</div>
</body>
</html>