<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>Create vacation</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<form action="/vacation/createVacation" method="post">
    <div class="col-lg-6">
        <div class="form-group">
            <label for="startDate">Choose start date:</label>
            <div class="input-group">
                <input type="date" class="form-control" id="startDate" name="startDate" placeholder="Enter start date" required></div>
        </div>
        <div class="form-group">
            <label for="endDate">Choose end date:</label>
            <div class="input-group">
                <input type="date" class="form-control" id="endDate" name="endDate" placeholder="Enter end date" required></div>
        </div>
        <%@include file="../errors/errorMap.jsp" %>
        <input type="submit" class="btn btn-primary btn-md" value="Create"/>
    </div>
</form>
</body>
</html>
