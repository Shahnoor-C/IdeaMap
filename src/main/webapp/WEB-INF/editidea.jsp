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
<h1>Edit this idea!</h1>

<form:form action="/ideas/update/${idea.id}" method="post" modelAttribute="idea">
    <p>
        <form:label path="title">Content:</form:label>
        <form:errors path="title"/>
        <form:input path="title"/>
    </p>
 
    <input type="submit" value="Update"/> 
</form:form>   

</body>
</html>