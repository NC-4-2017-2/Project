<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Add user</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<form action="/project/addUser/${projectId}" method="post">
    <div class="col-lg-6">
        <div class="form-group">
            <label for="lastName">Last name:</label>
            <div class="input-group">
                <input type="text" class="form-control" id="lastName"
                       name="lastName" placeholder="Enter last name"
                       required></div>
        </div>
        <div class="form-group">
            <label for="firstName">First name:</label>
            <div class="input-group">
                <input type="text" class="form-control" id="firstName"
                       name="firstName" placeholder="Enter first name"
                       required></div>
        </div>
        <div class="form-group">
            <label for="startDate">Start date:</label>
            <div class="input-group">
                <input type="date" class="form-control" id="startDate"
                       name="startDate" placeholder="Enter start date"
                       required></div>
        </div>
        <div class="form-group">
            <label for="endDate">End date:</label>
            <div class="input-group">
                <input type="date" class="form-control" id="endDate"
                       name="endDate" placeholder="Enter end date"
                       required></div>
        </div>
        <div class="form-group">
            <input type="submit" class="btn btn-primary btn-md" value="Add">
        </div>
        <%@include file="../errors/errorMap.jsp" %>
    </div>
</form>
</body>
</html>
