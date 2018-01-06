<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>Create business trip</title>
</head>
<body>
<form action="/businessTrip/createBusinessTrip/projectId/${projectId}/pmId/${pmId}"
      method="post">
    Choose country : <select name="country" required>
    <c:forEach items="${countryList}" var="country">
        <option value="${country}">${country}</option>
    </c:forEach>
</select><br>
    Choose start date : <input type="date" name="startDate" required><br>
    Choose end date : <input type="date" name="endDate" required><br>
    <c:choose>
        <c:when test="${authorId eq pmId }">
            <input type="hidden" name="authorId" value="${pmId}"/>
            Choose user : <select name="user">
            <c:forEach items="${userList}" var="user">
                <option value="${user.userId}"
                        name="user">${user.firstName} ${user.lastName} ${user.dateOfBirth}</option>
            </c:forEach>
        </select><br>
        </c:when>
        <c:otherwise>
            <input type="hidden" name="user" value="${authorId}"/>
            <input type="hidden" name="authorId" value="${authorId}"/>
        </c:otherwise>
    </c:choose>

    <%@include file="../errors/errorMap.jsp" %>
    <input type="submit" value="Create">
</form>
</body>
</html>
