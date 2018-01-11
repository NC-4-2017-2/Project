<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show working days</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>

<div class="col-lg-2">
    <table class="table table-hover table-dark" border="3">
        <thead>
        <tr>
            <th scope="col" style="text-align: center;">Working days:</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${workingDays}" var="workingDay">
            <tr>
                <td>
                    <a href="/workingDay/showWorkingDay/${workingDay.workingDayId}">${workingDay.date}</a><br>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <%@include file="../errors/errorMap.jsp" %>
</div>
</body>
</html>
