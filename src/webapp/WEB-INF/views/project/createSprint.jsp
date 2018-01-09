<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>Create project form</title>
</head>
<body>
<form action="/project/createSprint/${projectId}" method="post">
    <table border="0">
        <tr>
            <td>
                Sprint name:
            </td>
            <td>
                <input type="text" name="sprintName">
            </td>
        </tr>
        <tr>
            <td>
                Start date:
            </td>
            <td>
                <input type="date" name="startDate">

            </td>
        </tr>
        <tr>
            <td>
                Planned end date:
            </td>
            <td>
                <input type="date" name="plannedEndDate">
            </td>
        </tr>
        <br>
    </table>
    <%@include file="../errors/errorMap.jsp" %>
    <input type="submit" value="Create"/></td>
</form>
</body>
</html>
