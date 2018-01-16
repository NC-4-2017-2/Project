<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Choose role</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<form action="/user/createUserChooseRole" method="get">
    <div class="col-lg-6">
        Select user role:
        <select name="userRole" required>
            <option value="ROLE_ADMIN">ROLE_ADMIN</option>
            <option value="ROLE_PM">ROLE_PM</option>
            <option value="ROLE_LM">ROLE_LM</option>
            <option selected="selected" value="ROLE_SE">ROLE_SE
            </option>
        </select>
        <input type="submit" class="btn btn-primary btn-md"
               value="Next"/>
    </div>
</form>
</body>
</html>
