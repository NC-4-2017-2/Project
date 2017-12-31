<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>Project</title>
</head>
<body>
<table border="0">
    <tr>
        <td>Name: </td>
        <td>${project.name}</td>
    </tr>
    <tr>
        <td>Start date: </td>
        <td>${project.startDate}</td>
    </tr>
    <tr>
        <td>End date: </td>
        <td>${project.endDate}</td>
    </tr>
    <tr>
        <td>PM Id: </td>
        <td>${project.projectManagerId}</td>
    </tr>
    <tr>
        <td>Project status: </td>
        <td>${project.projectStatus}</td>
    </tr>
</table>
<%@include file="../errors/errorMap.jsp" %>

<form action="/project/userToAdd/${project.projectId}" >
    <button type="submit">Add users</button>
</form>
<form action="/project/showProjectUsersToDelete/${project.projectId}" >
    <button type="submit">Delete users</button>
</form>
<form action="/project/closeProject/${project.projectId}" method="post">
    <button type="submit">Add users</button>
</form>

</body>
</html>
