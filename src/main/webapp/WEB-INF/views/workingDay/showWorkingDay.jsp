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
<div class="col-lg-4">
    <table class="table table-hover table-dark" border="3">
        <thead>
        <tr>
            <th scope="col" colspan="2" style="text-align: center;">Working
                Day
                <c:if test="${workingDay.pmId eq currentUser.userId && workingDay.status.name() ne 'APPROVED'}">
                    <form action="/workingDay/showUpdatePMWorkingDayStatus/${workingDay.workingDayId}"
                          method="post">
                        <button type="submit" class="btn btn-primary btn-md"
                                name="status"
                                value="APPROVED">Approve
                        </button>
                        <button type="submit" class="btn btn-primary btn-md"
                                name="status"
                                value="DISAPPROVED">Disapprove
                        </button>
                    </form>
                </c:if>
            </th>
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
    <div class="form-group">
        <input onclick="window.history.go(-1); return false;"
               type="button" class="btn btn-primary btn-md"
               value="Back"/>
    </div>
    <%@include file="../errors/errorMap.jsp" %>
</div>
</body>
</html>
