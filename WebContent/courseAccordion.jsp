<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.*,java.util.*" %>
<jsp:useBean class="bean.IndexBean" id = "bean" scope="session"/>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 5//EN"
    "http://www.w3.org/TR/html4/strict.dtd">


<html>
<head>
	<title>Courses</title>
<!-- 	<script src="./alljs.js"></script> -->
	
	<script>
	function loadCoursesForPrefix(event,ui){
		$.ajaxSetup({async: false,cache: false});
		$.post("./CourseController", {coursePrefix:ui.newHeader[0].id});
		$.get("./courseTables.jsp");
		$.get("./courseTables.jsp", function(data){
			$("#accordioncontents" + ui.newHeader[0].id).html(data);
		});
	};
	$(document).ready(function() {
        $( "#accordion" ).accordion({
        	collapsable:true,
        	active:true,
        	heightStyle:"content",
			activate:loadCoursesForPrefix
        });
    });
	</script>
</head>
<body>
<c:choose>
	<c:when test="${searchPrefixes == null}">
		<div id="accordion">
			<c:forEach items="${bean.coursePrefixes}" var="prefix">
				<h3 id="${prefix}"><a href="#">${prefix}</a></h3>
					<div id="accordioncontents${prefix}">
					</div>
			</c:forEach>
		</div>
	</c:when>
	<c:otherwise>
		<div id="accordion">
			<c:forEach items="${searchPrefixes}" var="prefix">
				<h3 id="${prefix}"><a href="#">${prefix}</a></h3>
					<div id="accordioncontents${prefix}">
					</div>
			</c:forEach>
		</div>
	</c:otherwise>

</c:choose>
</body>

</html>