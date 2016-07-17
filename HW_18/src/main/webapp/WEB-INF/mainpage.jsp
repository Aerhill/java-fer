<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<style type="text/css">
	img {
    	max-width:800px;
	}
	#fullSizeDiv {
		margin-top: 10px
	}
</style>
</head>

<body>
<div class="container-fluid">
	<div class="col-lg-12 well text-center" id="buttonsDiv"></div>
	
	<div class="col-lg-12 center-block" id="thumbnailDiv"></div>

	<div class="col-lg-12" id="fullSizeDiv"></div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
<script type="text/javascript">
$( document ).ready(function() {
	$.get( "getTags", function( data ) {
		content = "";
		$.each(data, function (key, value) {
	        content += "<input class='btn btn-primary ' type='button' id=" + value + " value=" + value + " onclick=dohvati(this.id); return false;' /> ";
        });
        $("#buttonsDiv").html(content);
	}, "json");
});

function dohvati(id) {
	$('#fullSizeDiv').html("");
	$.get( "createThumbnail?tag=" + id  , function(data) {
		content = ""
		$.each(data, function(key, value) {
			content += "<img class='img-thumbnail' name='"+value+"'src='getThumbnail?name=thumbnail-"+ value + "' onclick='getFullSize(this.name)'/>"
		});
		$("#thumbnailDiv").html(content);
	}, "json");
}

function getFullSize(name) {
	$.get("getPictureInfo?name="+name, function(data) {
		$("#fullSizeDiv").html("<img class='col-lg-6 img-rounded' src='fullSizePicture?name="+name+"'/> " + 
		 " <div class='col-lg-6'><p class='bg-primary'>Description: " + data[0] + "<p class='bg-danger'> Tags: " + data[1]);
	}, "json");
}
</script>
</body>
</html>