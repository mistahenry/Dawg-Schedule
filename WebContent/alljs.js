function addClass(sel){
	$(this).unbind();
	$.ajaxSetup({async: false,cache: false});
	$.post("./CourseController", {courseId:sel.name});
	$.get("./currentCourses.jsp", function(data){
		$("#current-course-list").html(data);	
	});
	sel.onclick = null;
	var button = $(sel);
	button.text("Remove");
	button.on("click",function() {
        return removeCourse2(sel);
    });
};
function removeCourse(sel){
	$.ajaxSetup({async:false,cache:false});
	$.post("./CourseController",{drop:sel.name});
	$.get("./currentCourses.jsp", function(data){
		$("#current-course-list").html(data);		
	});
	
};
function removeCourse2(sel){
	$(this).unbind();
	$.ajaxSetup({async:false,cache:false});
	$.post("./CourseController",{drop:sel.name});
	$.get("./currentCourses.jsp", function(data){
		$("#current-course-list").html(data);		
	});
	var button = $(sel);
	button.text("Add");
	button.on("click",function(){
		return addClass(sel);
	});
};

$(".search").keyup(function (){
	var searchChar = $(".search").val().toUpperCase();
	$(".ui-accordion-header").each(function() {
	    if($(this).attr('id').substring(0,searchChar.length).toUpperCase() !== searchChar){
	    	$(this).hide();
	    }
	    else{
	    	if(($(this).is(':hidden'))){
	    		$(this).show();
	    	}
	    }
	});
	$(".ui-accordion-content").hide();

});


//$(document).ready(function(){
//	$(".search").keyup(function (){
//		$.ajaxSetup({async:false,cache:false});
//		$.post("./CourseController", {searchChar:$(".search").val()});
//		$.get("./courseAccordion.jsp", function(data){
//			$(".accordion").html(data);
//		});
//	});
//});
