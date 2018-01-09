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
        <td>Name:</td>
        <td>${project.name}</td>
    </tr>
    <tr>
        <td>Start date:</td>
        <td>${project.startDate}</td>
    </tr>
    <tr>
        <td>End date:</td>
        <td>${project.endDate}</td>
    </tr>
    <tr>
        <td>PM:</td>
        <td>${projectManager.lastName} ${projectManager.firstName}</td>
    </tr>
    <tr>
        <td>Project status:</td>
        <td>${project.projectStatus}</td>
    </tr>
</table>
<%@include file="../errors/errorMap.jsp" %>

<c:if test="${currentUser.userId eq projectManager.userId && project.projectStatus ne 'CLOSED'}">
    <form action="/project/showProjectUsersToDelete/${project.projectId}">
        <button type="submit">Show users</button>
    </form>
    <form action="/project/userToAdd/${project.projectId}">
        <button type="submit">Add user</button>
    </form>
    <form action="/project/closeProject/${project.projectId}" method="post">
        <button type="submit">Close project</button>
    </form>
    <form action="/project/viewSprints/${project.projectId}">
        <button type="submit">Show sprint list</button>
    </form>
    <form action="/project/createSprint/${project.projectId}">
        <button type="submit">Add sprint</button>
    </form>
</c:if>
</body>
</html>
