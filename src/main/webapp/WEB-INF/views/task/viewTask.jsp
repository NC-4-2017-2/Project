<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Show Task</title>
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
            <th scope="col" style="text-align: center;">Tasks:</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${taskList}" var="task">
            <tr>
                <td>
                <a href="/task/showTask/${task.taskId}">Task${task.taskId}</a>
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
