<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>Working Day</title>
</head>
<body>
<form action="/workingDay/showUpdatePMWorkingDayStatus/${workingDay.workingDayId}"
      method="post">
    <table border="0">
        <tr>
            <td>
                Date
            </td>
            <td>
                ${workingDay.date}
            </td>
        </tr>
        <tr>
            <td>
                Status
            </td>
            <td>
                ${workingDay.status}
            </td>
        </tr>
        <tr>
            <td>
                User
            </td>
            <td>
                ${workingDayUser}
            </td>
        </tr>
        <tr>
            <td>
                PM
            </td>
            <td>
                ${workingDayPm}
            </td>
        </tr>
        <tr>
            <td>
                Working hours
            </td>
            <td>
                ${workingDay.workingHours}
            </td>
        </tr>
        <tr>
            <td>
                Week number
            </td>
            <td>
                ${workingDay.weekNumber}
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
</body>
</html>
