<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<form action="/businessTrip/businessTripToUpdate/${trip.businessTripId}"
      method="post">

    Country:<input type="text" name="country" value="${trip.country}"><br>
    StartDate:<input type="date" name="startDate" value="${trip.startDate}"><br>
    EndDate:<input type="date" name="endDate" value="${trip.endDate}"><br>

    <c:if test="${trip.status eq 'APPROVED'}">
        Status:<select name="status">
        <option selected="selected" value="APPROVED">APPROVED</option>
        <option value="DISAPPROVED">DISAPPROVED</option>
        </select><br>
    </c:if>

    <c:if test="${trip.status eq 'DISAPPROVED'}">
        Status:<select name="status">
        <option selected="selected" value="DISAPPROVED">DISAPPROVED</option>
        <option value="APPROVED">APPROVED</option>
        </select><br>
    </c:if>

    <c:if test="${not empty errorList}">
        <c:forEach items="${errorList}" var="error">
            ${error}<br>
        </c:forEach>
    </c:if>

    <input type="submit" value="Next">
</form>
</body>
</html>
