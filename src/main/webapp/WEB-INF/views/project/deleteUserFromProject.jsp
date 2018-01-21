<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>DeleteFromProject</title>
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
            <th scope="col" style="text-align: center;" colspan="6">Workers:
            </th>
        </tr>
        <tr>
            <th style="text-align: center;">Last name:</th>
            <th style="text-align: center;">First name:</th>
            <th style="text-align: center;">Email:</th>
            <th style="text-align: center;">Birth date:</th>
            <th style="text-align: center;">User position:</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${userList}" var="user">
            <tr>
                <td style="text-align: center;">${user.lastName}</td>
                <td style="text-align: center;">${user.firstName}</td>
                <td style="text-align: center;">${user.email}</td>
                <td style="text-align: center;">${user.dateOfBirth}</td>
                <td style="text-align: center;">${user.jobTitle.name()}</td>
                <td>
                    <form action="/project/deleteUserFromProject/project/${projectId}/user/${user.userId}/jobTitle/${user.jobTitle}"
                          method="post">
                        <button type="submit" class="btn btn-primary btn-md"
                                value="Delete">Delete
                        </button>
                    </form>
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