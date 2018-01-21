<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>No data found</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<div class="col-lg-5">
    <div class="alert alert-info">
        <strong>No data found!</strong> Please choose another conditions to
        search.
    </div>
    <div class="form-group">
        <input onclick="window.history.go(-1); return false;"
               type="button" class="btn btn-primary btn-md"
               value="Back"/>
    </div>
</div>
</body>
</html>
