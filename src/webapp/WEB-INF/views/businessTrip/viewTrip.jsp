<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/highcharts.js"></script>
    <title>Show Business Trips</title>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<div class="col-lg-2">
    <table class="table table-hover table-dark" border="3">
        <thead>
        <tr>
            <th scope="col" style="text-align: center;">Business trip:</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${tripList}" var="trip">
            <tr>
                <td>
                    <a href="/businessTrip/showTrip/${trip.businessTripId}">BusinessTrip${trip.businessTripId}</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <%@include file="../errors/errorMap.jsp" %>
</div>
</body>
</html>
