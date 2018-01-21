<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Show sprint list</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<div class="col col-md-8">
    <table class="table table-hover table-dark" border="3">
        <thead>
        <tr>
            <th scope="col" style="text-align: center;" colspan="6">Sprints:
            </th>
        </tr>
        <tr>
            <th style="text-align: center;">Sprint name:</th>
            <th style="text-align: center;">Sprint start date:</th>
            <th style="text-align: center;">Sprint planned end date:</th>
            <th style="text-align: center;">Sprint actual end date:</th>
            <th style="text-align: center;">Sprint status:</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${sprintList}" var="sprint">
            <tr>
                <td style="text-align: center;">${sprint.name}</td>
                <td style="text-align: center;">${sprint.startDate}</td>
                <td style="text-align: center;">${sprint.plannedEndDate}</td>
                <td style="text-align: center;">${sprint.endDate}</td>
                <td style="text-align: center;">${sprint.status}</td>
                <td style="text-align: center;">
                    <c:if test="${sprint.status eq 'OPENED'}">
                        <form action="/project/closeSprint/${sprint.sprintId}"
                              method="post">
                            <button type="submit" class="btn btn-primary btn-md">Close sprint</button>
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="form-group">
        <input onclick="window.history.go(-1); return false;"
               type="button" class="btn btn-primary btn-md"
               value="Back"/>
    </div>
    <%@include file="../errors/errorMap.jsp" %>
</div>
</body>
</html>
