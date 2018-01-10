<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>Create project</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/highcharts.js"></script>
</head>
<body>
<form action="/project/createProject" method="post">
    <div class="col-lg-6">
        <div class="form-group">
            <label for="projectName">Project name: </label>
            <div class="input-group">
                <input type="text" class="form-control" name="projectName" id="projectName" placeholder="Enter project name" required></div>
        </div>
        <div class="form-group">
            <label for="projectStartDate">Project start date:</label>
            <div class="input-group">
                <input type="date" class="form-control" id="projectStartDate" name="projectStartDate" placeholder="Enter project start date" required></div>
        </div>
        <div class="form-group">
            <label for="projectEndDate">Project end date:</label>
            <div class="input-group">
                <input type="date" class="form-control" id="projectEndDate" name="projectEndDate" placeholder="Enter project end date" required></div>
        </div>
        <div class="form-group">
            <label for="pmId">Project PM:</label>
            <div class="input-group">
                <select name="pmId" id="pmId" required>
                    <c:forEach items="${pmOnTransitList}" var="pm">
                        <option value="${pm.userId}">${pm.lastName} ${pm.firstName} ${pm.dateOfBirth}</option>
                    </c:forEach>
                </select>
        </div>
    </div>
    <%@include file="../errors/errorMap.jsp" %>
    <input type="submit" class="btn btn-primary btn-md" value="Create"/>
    </div>
</form>
</body>
</html>
