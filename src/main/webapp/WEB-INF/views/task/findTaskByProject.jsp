<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Find your task</title>
</head>
<body>
<form action="/task/findTaskByProject">

    <tr>
        <td>Project Name:</td>
        <td>
            <select name="project">
                <c:forEach items="${projectNamesList}" var="projectNames">
                    <option value="${projectNames}">${projectNames}</option>
                </c:forEach>
            </select>
        </td>
    </tr>

    <%@include file="../errors/errorMap.jsp" %>
    <input type="submit" value="Find">
</form>

</body>
</html>
