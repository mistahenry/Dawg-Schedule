<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.*,java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Possible Schedules</title>
<link rel="stylesheet" type="text/css" href="theme.css">

</head>
<body>
<h1 class="header">Possible Schedules</h1>
<div class="schedules">
	<c:forEach items="${schedules}" var="schedule">
		<table border="1">
			<th>Course</th><th>Call Number</th><th>Start</th><th>End</th><th>Day</th>
			<c:forEach items="${schedule.sections }" var="section">
				<c:forEach items="${section.meetings}" var="meeting">
					<tr><td>${section.title }</td><td>${section.callNum }</td><td>${meeting.meetingStart }</td><td>${meeting.meetingEnd }</td><td>${meeting.meetingDay }</td></tr>
				</c:forEach>
			</c:forEach>
		</table>
		<p class="credits">Credits: ${schedule.hours }</p>
		
	</c:forEach>
</div>	
</body>
</html>