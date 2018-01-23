<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>Status of your task</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
    <form action="/task/findOwnTask">
        <div class="col-lg-6">

            <label for="status">Status of task:</label>
            <div class="form-group">
                <select class="status" id="status" name="status">
                    <option value="OPENED">Opened</option>
                    <option value="CLOSED">Closed</option>
                    <option value="REOPENED">Reopened</option>
                    <option value="READY_FOR_TESTING">Ready for testing</option>
                </select>
            </div>

            <%@include file="../errors/errorMap.jsp" %>

            <div class="form-group">
                <input onclick="window.history.go(-1); return false;" type="button"
                       class="btn btn-primary btn-md" value="Back"/>
                <input type="submit" class="btn btn-primary btn-md" value="Find">
            </div>
     </div>
</form>
</body>
</html>
