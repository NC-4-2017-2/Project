<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Find PM Working Day</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/highcharts.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<div class="col-lg-6">
    <%@include file="findPMTripForm.jsp" %>
    <%@include file="../errors/errorMap.jsp" %>
</div>
</body>
</html>
