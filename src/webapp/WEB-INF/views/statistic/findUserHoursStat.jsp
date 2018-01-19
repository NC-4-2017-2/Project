<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Find user</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<form action="/statistic/viewUserHoursStat">
    <div class="col-lg-6">
        <div class="form-group">
            <label for="lastName">Last name:</label>
            <div class="input-group">
                <input type="text" class="form-control" id="lastName"
                       name="lastName" required></div>
        </div>
        <div class="form-group">
            <label for="firstName">First name:</label>
            <div class="input-group">
                <input type="text" class="form-control" id="firstName"
                       name="firstName" required></div>
        </div>
        <div class="form-group">
            <label for="startDate">Start date:</label>
            <div class="input-group">
                <input type="date" class="form-control" id="startDate"
                       name="startDate" required></div>
        </div>
        <div class="form-group">
            <label for="endDate">End date:</label>
            <div class="input-group">
                <input type="date" class="form-control" id="endDate"
                       name="endDate" required></div>
        </div>
        <div class="form-group">
            <input type="submit" class="btn btn-primary btn-md" value="Find">
        </div>
        <%@include file="../errors/errorMap.jsp" %>
    </div>
</form>
</body>
</html>
