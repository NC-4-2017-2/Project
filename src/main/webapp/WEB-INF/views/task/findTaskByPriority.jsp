<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Find your task by priority</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<body>
<form action="/task/findTaskByPriority">
    <div class="col-lg-6">

        <label for="priority">Choose priority your task:</label>
        <div class="form-group">
            <select class="priority" id="priority" name="priority">
                <option value="CRITICAL">CRITICAL</option>
                <option value="HIGH">HIGH</option>
                <option value="NORMAL">NORMAL</option>
                <option selected="selected" value="LOW">LOW</option>
            </select>
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
