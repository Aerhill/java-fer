<%@ page import="java.util.Random" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body bgcolor="${pickedBgCol}">
<%
Random rand = new Random();
String[] colors = {"RED","BLUE","WHITE","BLACK","PINK"};

%>
	<p><font color="<%= colors[rand.nextInt(Integer.SIZE - 1)%5] %>">
		A Professor was traveling by boat. On his way he asked the sailor:
		<br>
		“Do you know Biology, Ecology, Zoology, Geography, physiology?
		<br>
		The sailor said no to all his questions.
		<br>
		Professor: What the hell do you know on earth. You will die of illiteracy.
		<br>
		After a while the boat started sinking. The Sailor asked the Professor, do you know swiminology and escapology from sharkology?
		<br>
		The professor said no.
		<br>
		Sailor: “Well, sharkology and crocodilogy will eat your assology, headology and you will dieology because of your mouthology.
	</font>
	</p>
</body>
</html>
