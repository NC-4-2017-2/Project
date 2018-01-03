<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>Vacation</title>
</head>
<body>
<table border="0">
    <tr>
        <td>
            User
        </td>
        <td>
            ${author.lastName} ${author.firstName}
        </td>
    </tr>
    <tr>
        <td>
            Project
        </td>
        <td>
            ${project.name}
        </td>
    </tr>
    <tr>
        <td>
            Start date
        </td>
        <td>
            ${vacation.startDate}
        </td>
    </tr>
    <tr>
        <td>
            End date
        </td>
        <td>
            ${vacation.endDate}
        </td>
    </tr>
    <tr>
        <td>
            Project manager
        </td>
        <td>
            ${projectManager.lastName} ${projectManager.firstName}
        </td>
    </tr>
    <tr>
        <td>
            Line manager
        </td>
        <td>
            ${lineManager.lastName} ${lineManager.firstName}
        </td>
    </tr>
    <tr>
        <td>
            Project manager status
        </td>
        <td>
            ${vacation.pmStatus}
        </td>
    </tr>
    <tr>
        <td>
            Line manager status
        </td>
        <td>
            ${vacation.lmStatus}
        </td>
    </tr>
</table>
<%@include file="../errors/errorMap.jsp" %>
<c:if test="${vacation.lmStatus ne 'APPROVED' && vacation.pmStatus ne 'APPROVED' && vacation.userId eq currentUser.userId}">
    <form action="/vacation/updateAuthorVacation/${vacation.vacationId}">
        <button type="submit">Update vacation</button>
    </form>
</c:if>
<c:if test="${vacation.pmId ne vacation.lmId}">
<c:if test="${currentUser.userId eq vacation.pmId}">
    PM status:
    <form action="/vacation/updateStatus/${vacation.vacationId}" method="post">
        <button type="submit" name="status" value="APPROVED">Approve</button>
        <button type="submit" name="status" value="DISAPPROVED">Disapprove</button>
    </form>
    <br>
</c:if>
<c:if test="${currentUser.userId eq vacation.lmId}">
    LM status:
    <form action="/vacation/updateStatus/${vacation.vacationId}" method="post">
        <button type="submit" name="status" value="APPROVED">Approve</button>
        <button type="submit" name="status" value="DISAPPROVED">Disapprove</button>
    </form>
    <br>
</c:if>
</c:if>
<c:if test="${vacation.pmId eq vacation.lmId}">
    PM and LM status:
    <form action="/vacation/updateStatus/${vacation.vacationId}" method="post">
        <button type="submit" name="status" value="APPROVED">Approve</button>
        <button type="submit" name="status" value="DISAPPROVED">Disapprove</button>
    </form>
    <br>
</c:if>
</body>
</html>
