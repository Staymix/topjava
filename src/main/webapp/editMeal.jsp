<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
<html>
<head>
    <jsp:useBean id="meals" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <title>Meal</title>
</head>
<body>
<a href="meals">Home</a>
<hr>
<c:choose>
    <c:when test="${meals.id != null}">
        <h2>Edit meal</h2>
    </c:when>
    <c:otherwise>
        <h2>Add meal</h2>
    </c:otherwise>
</c:choose>
<form method="post" action="meals" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="id" value="${meals.id}">
    <p>
        <dt>DateTime: <input type="datetime-local" name="date"
                             value="${f:formatLocalDateTime(meals.dateTime, "yyyy-MM-dd HH:mm")}" required>
        </dt>
    </p>
    <p>
        <dt>Description: <input type="text" size="50" name="description" value="${meals.description}" required></dt>
    </p>
    <p>
        <dt>Calories: <input type="number" name="calories" value="${meals.calories}" required min="1"></dt>
    </p>
    <button type="submit">Save</button>
    <a href="meals"><input type="button" value="Cancel"></a>
</form>
</body>
</html>
