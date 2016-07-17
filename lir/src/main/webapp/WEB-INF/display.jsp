<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
</head>
<body>
	<h1>Datoteka ${name}</h1>
	<pre>${text}</pre>
	<p> Obrađeni sadržaj
	<pre>${translation}</pre>
	
	<a href="statistika">Statistika</a>
</body>
</html>