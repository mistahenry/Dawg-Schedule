<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.*,java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h3>Step 2: Make sure the courses you add appear here</h3>
<table border="1">
	<th>Course</th><th>Title</th><th>Click to remove</th>
	<c:forEach items="${currentCourses}" var="course">
		<tr>
			<td>${course.coursePrefix } ${course.courseNum }</td><td>${course.title }</td>
			<td><button name="${course.id}_drop" onclick="removeCourse(this)">Remove</button></td>
		</tr>
	</c:forEach>
</table>
</body>
</html>