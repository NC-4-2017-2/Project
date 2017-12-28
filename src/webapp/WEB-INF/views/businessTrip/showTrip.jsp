<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>Trip</title>
</head>
<body>
<form action="/businessTrip/updateTripStatus/${businessTrip.businessTripId}"
      method="post">
    <table border="0">
        <tr>
            <td>
                User
            </td>
            <td>
                ${businessTripUser}
            </td>
        </tr>
        <tr>
            <td>
                PM
            </td>
            <td>
                ${businessTripPM}
            </td>
        </tr>
        <tr>
            <td>
                Author
            </td>
            <td>
                ${businessTripAuthor}
            </td>
        </tr>
        <tr>
            <td>
                Project name
            </td>
            <td>
                ${businessTripProject}
            </td>
        </tr>
        <tr>
            <td>
                Country
            </td>
            <td>
                ${businessTrip.country}
            </td>
        </tr>
        <tr>
            <td>
                Start date
            </td>
            <td>
                ${businessTrip.startDate}
            </td>
        </tr>
        <tr>
            <td>
                End date
            </td>
            <td>
                ${businessTrip.endDate}
            </td>
        </tr>
        <tr>
            <td>
                Status
            </td>
            <td>
                ${businessTrip.status}
            </td>
        </tr>
    </table>
    <c:if test="${currentUser.jobTitle.name() eq 'PROJECT_MANAGER'}">
        <button type="submit" name="status" value="APPROVED">Approve</button>
        <button type="submit" name="status" value="DISAPPROVED">Disapprove
        </button>
    </c:if>
    <%@include file="../errors/errorMap.jsp" %>
</form>
<c:if test="${businessTrip.status ne 'APPROVED' or businessTrip.pmId eq currentUser.userId}">
    <form action="/businessTrip/updateBusinessTrip/${businessTrip.businessTripId}">
        <button type="submit" value="Update">Update</button>
    </form>
</c:if>
</body>
</html>
