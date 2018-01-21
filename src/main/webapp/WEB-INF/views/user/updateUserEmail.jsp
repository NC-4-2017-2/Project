<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update user email</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<form action="/user/updateUserEmail/${user.userId}"
      method="post">
    <div class="col-lg-4">
        <div class="form-group">
            <label for="firstName">First name: </label>
            <div class="input-group">
            <input type="text" name="firstName" id="firstName" size="20"
                              value="${user.firstName}"
                              required disabled>
            </div>
        </div>
        <div class="form-group">
            <label for="lastName">Last name: </label>
            <div class="input-group">
            <input type="text" name="lastName" id="lastName" size="20"
                             value="${user.lastName}"
                             required disabled>
            </div>
        </div>
        <div class="form-group">
            <label for="birthDay">Birth day: </label>
            <div class="input-group">
            <input type="date" name="birthDay" id="birthDay" size="20"
                             value="${user.dateOfBirth}"
                             required disabled>
            </div>
        </div>
        <div class="form-group">
            <label for="email">Email: </label>
            <div class="input-group">
            <input type="email" name="email" id="email" size="20"
                         value="${user.email}" pattern="^(\S+)@([a-z0-9-]+)(\.)([a-z]{2,4})(\.?)([a-z]{0,4})+$"
                         required>
            </div>
        </div>
        <div class="form-group">
            <input onclick="window.history.go(-1); return false;"
                   type="button" class="btn btn-primary btn-md"
                   value="Back"/>
            <input type="submit" class="btn btn-primary btn-md" value="Update">
        </div>
        <%@include file="../errors/errorMap.jsp" %>
    </div>
</form>
</body>
</html>
