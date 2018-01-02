<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>Vacation</title>
</head>
<body>
<table border="0">
    <tr>
        <td>
            User
        </td>
        <td>
            ${author.lastName} ${author.firstName}
        </td>
    </tr>
    <tr>
        <td>
            Project
        </td>
        <td>
            ${project.name}
        </td>
    </tr>
    <tr>
        <td>
            Start date
        </td>
        <td>
            ${vacation.startDate}
        </td>
    </tr>
    <tr>
        <td>
            End date
        </td>
        <td>
            ${vacation.endDate}
        </td>
    </tr>
    <tr>
        <td>
            Project manager
        </td>
        <td>
            ${projectManager.lastName} ${projectManager.firstName}
        </td>
    </tr>
    <tr>
        <td>
            Line manager
        </td>
        <td>
            ${lineManager.lastName} ${lineManager.firstName}
        </td>
    </tr>
    <tr>
        <td>
            Project manager status
        </td>
        <td>
            ${vacation.pmStatus}
        </td>
    </tr>
    <tr>
        <td>
            Line manager status
        </td>
        <td>
            ${vacation.lmStatus}
        </td>
    </tr>
</table>
<%@include file="../errors/errorMap.jsp" %>
</body>
</html>
