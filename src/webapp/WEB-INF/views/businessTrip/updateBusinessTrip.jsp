<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>UpdateBusinessTrip</title>
</head>
<body>
<form action="/businessTrip/businessTripToUpdate/${businessTripId}"
      method="post">
    Choose country:<select name="country" required>
    <option selected="selected"
            value="${tripCountry}">${tripCountry}</option>
    <c:forEach items="${countryList}" var="country">
        <option value="${country}">${country}</option>
    </c:forEach>
</select><br>

    StartDate:<input type="date" name="startDate" value="${startDate}"
                     required><br>
    EndDate:<input type="date" name="endDate" value="${endDate}"
                   required><br>

    Status:<select name="status" required>
    <c:if test="${status eq 'APPROVED'}">
        <option selected="selected" value="APPROVED">APPROVED</option>
        <option value="DISAPPROVED">DISAPPROVED</option>
    </c:if>

    <c:if test="${status eq 'DISAPPROVED'}">
        <option selected="selected" value="DISAPPROVED">DISAPPROVED</option>
        <option value="APPROVED">APPROVED</option>
    </c:if>
    </select><br>

    <%@include file="../errors/errorMap.jsp" %>
    <input type="submit" value="Update">
</form>
</body>
</html>
