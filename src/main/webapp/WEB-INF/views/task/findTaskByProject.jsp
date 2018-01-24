<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Find your task by project</title>
</head>
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
<script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
<script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<form action="/task/findTaskByProject">
<div class="col-lg-6">

    <label for="projectNames">Project:</label>
    <div class="form-group">
        <select name="project" style="width: 176px; height: 30px;" id = "projectNames">
            <c:forEach items="${projectNamesList}" var="projectNames" >
                <option value="${projectNames}">${projectNames}</option>
            </c:forEach>
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
