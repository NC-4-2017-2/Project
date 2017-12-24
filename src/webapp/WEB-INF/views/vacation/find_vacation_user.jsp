<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>FindVacationListByUser</title>
</head>
<body>
<form action="/vacation/findVacationUser">
    <tr>
        Choose project:
        <select name="userId" required>
            <c:forEach items="${userNames}" var="name">
                <option value="${name.key}">${name.value}</option>
            </c:forEach>
        </select><br>
    </tr>

    <input type="submit" value="Find">
</form>
</body>
</html>
