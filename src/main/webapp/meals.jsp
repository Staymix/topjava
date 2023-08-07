<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
<html>
<head>
    <jsp:useBean id="meals" type="java.util.List" scope="request"/>
    <title>Meal list</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<a href="meals?action=create">Add Meal</a>
<table border="1">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach var="meal" items="${meals}">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr style="color: ${(meal.excess ? "red" : "greenyellow")}">
            <th>${f:formatLocalDateTime(meal.dateTime, "yyyy-MM-dd HH:mm")}</th>
            <th>${meal.description}</th>
            <th>${meal.calories}</th>
            <th><a href="meals?id=${meal.id}&action=update">Update</a></th>
            <th><a href="meals?id=${meal.id}&action=delete">Delete</a></th>
        </tr>
    </c:forEach>
</table>
</body>
</html>