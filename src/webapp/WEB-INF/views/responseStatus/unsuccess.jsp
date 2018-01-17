<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Result</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<div class="col-lg-3">
    <div class="form-group">
        <div class="input-group">
            <%@include file="../errors/errorMap.jsp" %>
        </div>
    </div>
    <div class="form-group">
        <div class="input-group">
            <img src="https://boygeniusreport.files.wordpress.com/2017/10/porg.gif?w=782Q">
        </div>
    </div>
</div>
</body>
</html>
