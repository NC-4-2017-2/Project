<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<title>ProjectByStartDate</title>
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
<script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
<script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<div class="col-lg-6">
    <form action="/project/findProjectByStartDate">
        <label for="startDate">Start date:</label>
        <div class="input-group">
            <input type="date" name="startDate" id="startDate">
        </div>
        <label for="endDate">End date:</label>
        <div class="input-group">
            <input type="date" name="endDate" id="endDate">
        </div>
        <br>
        <div class="form-group">
            <input onclick="window.history.go(-1); return false;"
                   type="button" class="btn btn-primary btn-md"
                   value="Back"/>
            <input type="submit" class="btn btn-primary btn-md" value="Find">
        </div>
    </form>
    <%@include file="../errors/errorMap.jsp" %>
</div>
</body>
</html>
