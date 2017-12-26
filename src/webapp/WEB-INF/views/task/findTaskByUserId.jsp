<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Find task by your id</title>
</head>
<body>
<form action="/task/findTaskByUserId">
    Enter your id:<tr>
    <td><input type="text" name="userId" size="20"
               value=${task.getUserId()}></td>
    </tr><br>
    <p><c:if test="${not empty errorMap}">
        <c:forEach items="${errorMap}" var="error">
            ${error.value}<br>
        </c:forEach>
    </c:if></p>
    <br>
    <input type="submit" value="Find">
</form>

</body>
</html>
