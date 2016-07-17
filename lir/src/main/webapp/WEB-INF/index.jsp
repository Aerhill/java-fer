<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
</head>
<body>

	<c:forEach var="item" items="${applicationScope['allFiles']}">
		<a href="getInfoForFile?fileName=${item}">${item}</a>
		<br>
	</c:forEach>	
</body>
</html>