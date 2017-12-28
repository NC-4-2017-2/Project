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
<form action="/businessTrip/updateBusinessTrip/${businessTripId}"
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

    <%@include file="../errors/errorMap.jsp" %>
    <input type="submit" value="Update">
</form>
</body>
</html>
