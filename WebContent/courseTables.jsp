<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.*,java.util.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 5//EN"
    "http://www.w3.org/TR/html4/strict.dtd">
    
<html>
<head>
	<title>Table</title>
<!-- 	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" /> -->
<!-- 	<script src="http://code.jquery.com/jquery-1.9.1.js"></script> -->
<!-- 	<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script> -->
<!-- 	<script src="./alljs.js"></script> -->
	
</head>
<body>

<table border="1">
	<th>Course</th><th>Title</th><th>Click to add</th>
	<c:forEach items="${coursesForPrefix}" var="course">
		<tr>
			<td>${course.coursePrefix } ${course.courseNum }</td><td>${course.title }</td>
			<td><button name="${course.id}" onclick="addClass(this)">Add</button></td>
		</tr>
	</c:forEach>
</table>
</body>
</html>
