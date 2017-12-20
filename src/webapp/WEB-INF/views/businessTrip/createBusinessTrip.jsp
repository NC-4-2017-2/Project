<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>CreateBusinessTrip</title>
</head>
<body>
<form action="createBusinessTripPost" method="post">
    <input type="hidden" name="projectId" value="${projectId}"/>
    <input type="hidden" name="pmId" value="${pmId}"/>
    Choose country : <select name="country">
    <c:forEach items="${countryList}" var="country">
        <option value="${country}">${country}</option>
    </c:forEach>
</select><br>
    Choose start date : <input type="date" name="startDate"><br>
    Choose end date : <input type="date" name="endDate"><br>
    Type last name :<input type="text" name="lastName"><br>
    Type first name :<input type="text" name="firstName"><br>
    <c:if test="${pmId == null}">
        Type PM last name :<input type="text" name="lastNamePm"><br>
        Type LM first name : <input type="text" name="firstNamePm"><br>
    </c:if>
    <c:if test="${pmId != null}">
        <input type="hidden" name="lastNamePm" value="${lastNamePm}"><br>
         <input type="hidden" name="firstNamePm" value="${firstNamePm}"><br>
    </c:if>
    <input type="submit" value="Next">
</form>
</body>
</html>
