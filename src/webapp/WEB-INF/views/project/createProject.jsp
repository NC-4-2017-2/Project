<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>Choose sprints size</title>
</head>
<body>
<form action="/project/createProject"  method="post">
    <table border="0">
        <tr>
            <th>
                Project
            </th>
        </tr>
        <tr>
            <td>
                Project name:
            </td>
            <td>
                <input type="text" name="projectName">
            </td>
        </tr>
        <tr>
            <td>
                Project start date:
            </td>
            <td>
                <input type="date" name="projectStartDate">
            </td>
        </tr>
        <tr>
            <td>
                Project end date:
            </td>
            <td>
                <input type="date" name="projectEndDate">
            </td>
        </tr>
        <tr>
            <td>
                Choose pm:
            </td>
            <td>
                <select name="pmId" required>
                    <c:forEach items="${pmOnTransitList}" var="pm">
                        <option value="${pm.userId}">${pm.lastName} ${pm.firstName} ${pm.dateOfBirth}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
    </table>
    <%@include file="../errors/errorMap.jsp" %>
    <input type="submit" value="Create"/></td>
</form>
</body>
</html>
