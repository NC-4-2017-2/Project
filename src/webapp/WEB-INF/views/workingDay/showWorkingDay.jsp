<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Working Day</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<form action="/workingDay/showUpdatePMWorkingDayStatus/${workingDay.workingDayId}"
      method="post">
    <div class="col-lg-4">
        <table class="table table-hover table-dark" border="3">
            <thead>
            <tr>
                <th scope="col" colspan="2" style="text-align: center;">Vacation</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <th scope="row">Date:</th>
                <td>${workingDay.date}</td>
            </tr>
            <tr>
                <th scope="row">Status:</th>
                <td>${workingDay.status}</td>
            </tr>
            <tr>
                <th scope="row">User:</th>
                <td>${workingDayUser}</td>
            </tr>
            <tr>
                <th scope="row">PM:</th>
                <td>${workingDayPm}</td>
            </tr>
            <tr>
                <th scope="row">Working hours:</th>
                <td>${workingDay.workingHours}</td>
            </tr>
            <tr>
                <th scope="row">Week number:</th>
                <td>${workingDay.weekNumber}</td>
            </tr>
            </tbody>
        </table>
    <c:if test="${currentUser.jobTitle.name() eq 'PROJECT_MANAGER'}">
        <button type="submit" class="btn btn-primary btn-md" name="status" value="APPROVED">Approve</button>
        <button type="submit" class="btn btn-primary btn-md" name="status" value="DISAPPROVED">Disapprove
        </button>
    </c:if>
    <%@include file="../errors/errorMap.jsp" %>
    </div>
</form>
</body>
</html>
