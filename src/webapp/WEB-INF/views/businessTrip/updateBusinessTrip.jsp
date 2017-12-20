<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<form action="/businessTrip/businessTripToUpdate/${trip.businessTripId}" method="post">
    Country:<input type="text" name="country" value="${trip.country}"><br>

    <%--<select name="country">--%>
        <%--<c:forEach items="${countryList}" var="country">--%>
            <%--<option value="${country}">${country}</option>--%>
            <%--<c:if test="${trip.country eq '${country}'}">--%>
                <%--<option selected="selected" value="${trip.country}">"${country}"</option>--%>
            <%--</c:if>--%>
        <%--</c:forEach>--%>
    <%--</select><br>--%>

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

    <input type="submit" value="Next">
</form>
</body>
</html>
