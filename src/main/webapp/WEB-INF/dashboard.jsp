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
<h1>Welcome ${loggedinuser.firstName}</h1>
<a href = "/">Logout</a>

<table class="table">
  <thead>
    <tr>
      
      <th scope="col">Ideas</th>
      <th scope="col">Created By:</th>
      <th scope="col">Likes</th>
      <th scope="col">Action</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach items = "${allideas}" var = "idea">
    <tr>
      <td><a href="/ideas/${idea.id}">${idea.title}</a></td>
      <td>${idea.creator.firstName} ${idea.creator.lastName}</td>
      <td>${idea.usersWholiked.size()}</td>
      <td>
      <c:choose>
      	<c:when test = "${idea.usersWholiked.contains(loggedinuser)}">
      		<a href ="/ideas/${idea.id}/unlike">Unlike</a>
      	
      	</c:when>
      	<c:otherwise>
		      <a href = "/ideas/${idea.id}/like"><button class = "btn btn-info">Like</button></a>
      	
      	</c:otherwise>
      
      </c:choose>
      
      </td>
    </tr>
   </c:forEach>
  </tbody>
</table>
<a href="/ideas/new"><button class= "btn btn-primary">Create an idea</button></a>
</body>
</html>