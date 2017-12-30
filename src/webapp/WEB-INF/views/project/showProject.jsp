<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

<a href="">Add users</a>
<form action="/project/showProjectUsersToDelete/${project.projectId}" >
    <button type="submit">Delete users</button>
</form>
<a href="">Close project</a>

</body>
</html>
