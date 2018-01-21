<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Create project form</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<form action="/project/createSprint/${projectId}" method="post">
    <div class="col-lg-6">
        <div class="form-group">
            <label for="sprintName">Sprint name: </label>
            <div class="input-group">
                <input type="text" class="form-control" name="sprintName"
                       id="sprintName" placeholder="Enter sprint name" required>
            </div>
        </div>
        <div class="form-group">
            <label for="startDate">Start date:</label>
            <div class="input-group">
                <input type="date" class="form-control" name="startDate"
                       id="startDate" placeholder="Enter start date" required>
            </div>
        </div>
        <div class="form-group">
            <label for="plannedEndDate">Planned end date:</label>
            <div class="input-group">
                <input type="date" class="form-control" name="plannedEndDate"
                       id="plannedEndDate" placeholder="Enter planned end date"
                       required></div>
        </div>
        <div class="form-group">
            <input onclick="window.history.go(-1); return false;"
                   type="button" class="btn btn-primary btn-md"
                   value="Back"/>
            <input type="submit" class="btn btn-primary btn-md"
                   value="Create"/>
        </div>
        <%@include file="../errors/errorMap.jsp" %>
    </div>
</form>
</body>
</html>
