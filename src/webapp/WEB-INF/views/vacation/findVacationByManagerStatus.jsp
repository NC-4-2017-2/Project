<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Find vacation by manager status</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<form action="/vacation/findVacationByManagerStatus">
<div class="form-group">
    <label for="status">Choose status:</label>
    <div class="input-group">
        <select name="status" id="status" required>
            <option value="APPROVED">APPROVED</option>
            <option value="DISAPPROVED">DISAPPROVED</option>
            <option value="WAITING_FOR_APPROVAL">WAITING_FOR_APPROVAL</option>
        </select>
    </div>
    <br>
    <%@include file="../errors/errorMap.jsp" %>
    <input type="submit" class="btn btn-primary btn-md" value="Find">
</div>
</form>
</body>
</html>
