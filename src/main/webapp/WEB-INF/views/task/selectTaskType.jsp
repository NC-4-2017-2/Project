<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Select task type</title>
</head>
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
<script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
<script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<body>
<form action="/task/selectTaskType">
    <div class="col-lg-6">

        <div class="form-group">
            <label for="taskType">Select type of task:</label>
            <select name="taskType" id = "taskType" required>
                <option value="PROJECT_TASK">PROJECT_TASK</option>
                <option value="REQUEST_TASK">REQUEST_TASK</option>
            </select>
        </div>

    <%@include file="../errors/errorMap.jsp" %>

    <div class="form-group">
    <input onclick="window.history.go(-1); return false;" type="button"
           class="btn btn-primary btn-md" value="Back"/>
    <input type="submit" value="Next" class="btn btn-primary btn-md"/>
    </div>

    </div>
</form>
</body>
</html>
