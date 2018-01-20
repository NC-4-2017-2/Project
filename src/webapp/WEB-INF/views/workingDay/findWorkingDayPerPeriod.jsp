<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Choose period</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<form action="/workingDay/viewWorkingDay">
    <div class="col-lg-6">
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
            <input onclick="window.history.go(-1); return false;"
                   type="button" class="btn btn-primary btn-md"
                   value="Back"/>
            <input type="submit" class="btn btn-primary btn-md" value="Find">
        </div>
        <%@include file="../errors/errorMap.jsp" %>
    </div>
</form>
</body>
</html>
