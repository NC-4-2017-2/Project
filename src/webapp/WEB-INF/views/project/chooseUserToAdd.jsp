<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>Chose user</title>
</head>
<body>
<form action="/project/addUserFromDuplicate/${projectId}/${startDate}/${endDate}"
      method="post">
    Choose user :
    <select name="user">
        <c:forEach items="${usersList}" var="user">
            <option value="${user.userId}"
                    name="user">${user.firstName} ${user.lastName} ${user.dateOfBirth}</option>
        </c:forEach>
    </select><br>
    <%@include file="../errors/errorMap.jsp" %>
    <input type="submit" value="Add">
</form>
</body>
</html>
