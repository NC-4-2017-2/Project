<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>FindVacationListByUserLm</title>
</head>
<body>
<form action="/vacation/findVacationUserLm">
    <tr>
        Choose project:
        <select name="userId" required>
            <c:forEach items="${userNames}" var="name">
                <option value="${name.key}">${name.value}</option>
            </c:forEach>
        </select><br>
    </tr>
    <tr>
        Choose pm status:
        <select name="lmStatus" required>
            <option value="APPROVED">APPROVED</option>
            <option value="DISAPPROVED">DISAPPROVED</option>
            <option value="WAITING_FOR_APPROVAL">WAITING FOR APPROVAL</option>
        </select><br>
    </tr>
    <input type="submit" value="Find">
</form>
</body>
</html>
