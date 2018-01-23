<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Enter last and first name</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<body>
<form action="/task/findTaskByFirstAndLastName">
    <div class="col-lg-6">

        <div class="form-group">
            <label for="lastName">Enter user last name:</label>
            <div class="input-group">
                <input type="text" class="form-control" id="lastName" name="lastName" placeholder="Enter last name" required></div>
        </div>

        <div class="form-group">
            <label for="firstName">Enter user first name:</label>
            <div class="input-group">
                <input type="text" class="form-control" id="firstName" name="firstName" placeholder="Enter first name" required></div>
        </div>

        <label for="status">Status of task:</label>
        <div class="form-group">
            <select class="status" id="status" name="status">
                <option value="OPENED">Opened</option>
                <option value="CLOSED">Closed</option>
                <option value="REOPENED">Reopened</option>
                <option value="READY_FOR_TESTING">Ready for testing</option>
            </select>
        </div>

    <br>
        <div class="form-group">
            <input onclick="window.history.go(-1); return false;" type="button"
                   class="btn btn-primary btn-md" value="Back"/>
            <input type="submit" class="btn btn-primary btn-md" value="Find">
        </div>

        <%@include file="../errors/errorMap.jsp" %>
    </div>
</form>
</body>
</html>
