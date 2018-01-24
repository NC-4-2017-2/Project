<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Select task type</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<body>
<form action="/task/selectTaskType">
    <div class="col-lg-6">

        <div class="form-group">
            <label for="taskType">Select type of task:</label>
            <select style="width: 176px; height: 30px;" name="taskType" id = "taskType" required>
                <option value="PROJECT_TASK">Project task</option>
                <option value="REQUEST_TASK">Request task</option>
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
