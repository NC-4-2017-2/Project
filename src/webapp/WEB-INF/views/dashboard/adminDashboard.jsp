<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<%@include file="../errors/errorMap.jsp" %>

<div class="col-sm-9">
    <div class="well">
        <h4>Table working hours</h4>
        <%@include file="../workingDay/createWorkingDayForm.jsp" %>
    </div>
    <div class="row">
        <div class="col-sm-3">
            <div class="well">
                <h4>Create vacation on me</h4>
                <%@include file="../vacation/createVacationForm.jsp" %>
            </div>
        </div>
        <div class="col-sm-3">
            <div class="well">
                <h4>Create new user in system</h4>
            </div>
        </div>
        <div class="col-sm-3">
            <div class="well">
                <h4>Create project</h4>
                <%@include file="../project/createProjectDashboardForm.jsp" %>
            </div>
        </div>
        <div class="col-sm-3">
            <div class="well">
                <h4>Bounce</h4>
                <p>30%</p>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-4">
            <div class="well">
                <p>Text</p>
                <p>Text</p>
                <p>Text</p>
            </div>
        </div>
        <div class="col-sm-4">
            <div class="well">
                <p>Text</p>
                <p>Text</p>
                <p>Text</p>
            </div>
        </div>
        <div class="col-sm-4">
            <div class="well">
                <p>Text</p>
                <p>Text</p>
                <p>Text</p>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-8">
            <div class="well">
                <p>Text</p>
            </div>
        </div>
        <div class="col-sm-4">
            <div class="well">
                <p>Text</p>
            </div>
        </div>
    </div>
</div>

</body>
</html>
