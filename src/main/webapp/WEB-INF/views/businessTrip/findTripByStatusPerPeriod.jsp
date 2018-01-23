<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Find trip by status and period</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<form action="/businessTrip/viewTrip">
    <div class="col-lg-6">
        <label for="status">Choose status:</label>
        <div class="input-group">
            <select name="status" id="status" required>
                <option value="APPROVED">Approved</option>
                <option value="DISAPPROVED">Disapproved</option>
                <option value="WAITING_FOR_APPROVAL">Waiting for approval
                </option>
            </select>
        </div>
        <div class="form-group">
            <label for="startDate">Start date:</label>
            <div class="input-group">
                <input type="date" class="form-control" id="startDate"
                       name="startDate" placeholder="Enter start date"
                       required></div>
        </div>
        <div class="form-group">
            <label for="endDate">Choose end date:</label>
            <div class="input-group">
                <input type="date" class="form-control" id="endDate"
                       name="endDate" placeholder="Enter end date" required>
            </div>
        </div>
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
