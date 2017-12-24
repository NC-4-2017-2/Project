<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>CreateWorkingDay</title>
</head>
<body>
<form action="/workingDay/create" method="post">
    <table border="0">
        <tr>
            <td>Monday:</td>
            <td><input name="mondayStartTime" type="time"></td>
            <td> - </td>
            <td><input name="mondayEndTime" type="time"></td>
        </tr>
        <tr>
            <td>Tuesday:</td>
            <td><input name="tuesdayStartTime" type="time"></td>
            <td> - </td>
            <td><input name="tuesdayEndTime" type="time"></td>
        </tr>
        <tr>
            <td>Wednesday:</td>
            <td><input name="wednesdayStartTime" type="time"></td>
            <td> - </td>
            <td><input name="wednesdayEndTime" type="time"></td>
        </tr>
        <tr>
            <td>Thursday:</td>
            <td><input name="thursdayStartTime" type="time"></td>
            <td> - </td>
            <td><input name="thursdayEndTime" type="time"></td>
        </tr>
        <tr>
            <td>Friday:</td>
            <td><input name="fridayStartTime" type="time"></td>
            <td> - </td>
            <td><input name="fridayEndTime" type="time"></td>
        </tr>
        <tr>
            <td>Saturday:</td>
            <td><input name="saturdayStartTime" type="time"></td>
            <td> - </td>
            <td><input name="saturdayEndTime" type="time"></td>
        </tr>
        <tr>
            <td>Sunday:</td>
            <td><input name="sundayStartTime" type="time"></td>
            <td> - </td>
            <td><input name="sundayEndTime" type="time"></td>
        </tr>
    </table>
    <p><c:if test="${not empty errorMap}">
        <c:forEach items="${errorMap}" var="error">
            ${error.value}<br>
        </c:forEach>
    </c:if></p>
    <input type="submit" value="Create">
</form>
</body>
</html>
