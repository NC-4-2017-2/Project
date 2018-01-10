<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create working day</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/highcharts.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<form action="/workingDay/createWorkingDay" method="post">
    <div class="col-lg-6">
        <table border="0">
            <tr>
                <td>Monday:</td>
                <td><input name="mondayStartTime" type="time"></td>
                <td> -</td>
                <td><input name="mondayEndTime" type="time"></td>
            </tr>
            <tr>
                <td>Tuesday:</td>
                <td><input name="tuesdayStartTime" type="time"></td>
                <td> -</td>
                <td><input name="tuesdayEndTime" type="time"></td>
            </tr>
            <tr>
                <td>Wednesday:</td>
                <td><input name="wednesdayStartTime" type="time"></td>
                <td> -</td>
                <td><input name="wednesdayEndTime" type="time"></td>
            </tr>
            <tr>
                <td>Thursday:</td>
                <td><input name="thursdayStartTime" type="time"></td>
                <td> -</td>
                <td><input name="thursdayEndTime" type="time"></td>
            </tr>
            <tr>
                <td>Friday:</td>
                <td><input name="fridayStartTime" type="time"></td>
                <td> -</td>
                <td><input name="fridayEndTime" type="time"></td>
            </tr>
            <tr>
                <td>Saturday:</td>
                <td><input name="saturdayStartTime" type="time"></td>
                <td> -</td>
                <td><input name="saturdayEndTime" type="time"></td>
            </tr>
            <tr>
                <td>Sunday:</td>
                <td><input name="sundayStartTime" type="time"></td>
                <td> -</td>
                <td><input name="sundayEndTime" type="time"></td>
            </tr>
        </table>
        <%@include file="../errors/errorMap.jsp" %>
        <input type="submit" class="btn btn-primary btn-md" value="Create">
    </div>
</form>
</body>
</html>
