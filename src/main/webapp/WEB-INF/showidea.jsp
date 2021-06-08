<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" 
href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">

<title>Insert title here</title>
</head>
<body>
<h1>Show idea info here!</h1>

<h1>${ideaToShow.title}</h1>
<h3>Created By: ${ideaToShow.creator.firstName}</h3>

<h3>Users who liked this idea:</h3>
<c:forEach items = "${ideaToShow.usersWholiked}" var = "user">

<h3>${user.firstName}</h3>

</c:forEach>



<a href="/ideas/${ideaToShow.id}/edit"><button class = "btn btn-warning">Edit</button></a> <a href= "/ideas/${ideaToShow.id}/delete"><button class = "btn btn-danger">Delete</button></a>
</body>
</html>