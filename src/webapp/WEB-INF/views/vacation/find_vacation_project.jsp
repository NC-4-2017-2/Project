<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>FindVacationByProject</title>
</head>
<body>
<form action="/vacation/findVacationProject">
    <tr>
        Choose project:
        <select name="projectName" required>
            <c:forEach items="${projectNames}" var="name">
                <option value="${name}">${name}</option>
            </c:forEach>
        </select><br>
    </tr>

    <input type="submit" value="Find">
</form>
</body>
</html>
