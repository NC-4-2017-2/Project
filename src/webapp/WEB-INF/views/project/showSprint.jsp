<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>Show Sprint</title>
</head>
<body>
<table border="0">
    <tr>
        <td>
        Sprint name:
        </td>
        <td>
            ${sprint.name}
        </td>
    </tr>
    <tr>
        <td>
            Sprint start date:
        </td>
        <td>
            ${sprint.startDate}
        </td>
    </tr>
    <tr>
        <td>
            Sprint planned end date:
        </td>
        <td>
            ${sprint.plannedEndDate}
        </td>
    </tr>
    <tr>
        <td>
            Sprint actual end date:
        </td>
        <td>
            ${sprint.endDate}
        </td>
    </tr>
    <tr>
        <td>
            Sprint status:
        </td>
        <td>
            ${sprint.status}
        </td>
    </tr>
</table>
<%@include file="../errors/errorMap.jsp" %>
<form action="/project/closeSprint/${sprint.sprintId}" method="post">
    <button type="submit">Close sprint</button>
</form>
</body>
</html>
