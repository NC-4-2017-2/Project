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
    <h4>Working hours</h4>
    <div class="well">
        <h4>Table working hours</h4>
        <%@include file="../workingDay/createWorkingDayForm.jsp" %>
        <h4>Find working hours by status</h4>
        <%@include file="../workingDay/findPMWorkingDayForm.jsp" %>
    </div>
    <div class="row">
        <div class="col-sm-3">
            <h4>Vacations</h4>
            <div class="well">
                <h4>Create vacation on me</h4>
                <%@include file="../vacation/createVacationForm.jsp" %>
            </div>
        </div>
        <div class="col-sm-3">
            <h4>Business trips</h4>
            <div class="well">
                <h4>Create business trip</h4>
                <form action="/businessTrip/createBusinessTrip">
                    <button type="submit">Create</button>
                </form>
                <h4>Show trips by status</h4>
                <%@include file="../businessTrip/findPMTripForm.jsp" %>
            </div>
        </div>
        <div class="col-sm-3">
            <div class="well">
                <h4>Sessions</h4>
                <p>10 Million</p>
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
