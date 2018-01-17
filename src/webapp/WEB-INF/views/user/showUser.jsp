<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>User</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"/>
<sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin"/>
<div class="col-lg-6">
    <table class="table table-hover table-dark" border="3">
        <thead>
        <tr>
            <th scope="col" colspan="2" style="text-align: center;">User
                info:
            </th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th scope="row">User Name:</th>
            <td>${user.firstName} ${user.lastName}</td>
        </tr>
        <tr>
            <th scope="row">Email:</th>
            <td>${user.email}</td>
        </tr>
        <tr>
            <th scope="row">Birth date:</th>
            <td>${user.dateOfBirth}</td>
        </tr>
        <tr>
            <th scope="row">Hire date:</th>
            <td>${user.hireDate}</td>
        </tr>
        <tr>
            <th scope="row">Phone Number:</th>
            <td>${user.phoneNumber}</td>
        </tr>
        <tr>
            <th scope="row">Job Title:</th>
            <td>${user.jobTitle}</td>
        </tr>
        <tr>
            <th scope="row">User's status on project:</th>
            <td>${user.projectStatus}</td>
        </tr>
        <tr>
            <th scope="row">User status:</th>
            <td>${user.userStatus}</td>
        </tr>
        </tbody>
    </table>
    <c:if test="${isAdmin}">
        <form action="/user/updateUserEmail/${user.userId}">
            <button type="submit" class="btn btn-primary btn-md">Update
                email
            </button>
        </form>
        <form action="/user/updatePassword/${user.userId}/${user.login}" method="post">
            <button type="submit" class="btn btn-primary btn-md">Refresh password
            </button>
        </form>
        <form action="/user/updateJobTitle/${user.userId}"">
            <button type="submit" class="btn btn-primary btn-md">Update job title
            </button>
        </form>
    </c:if>
    <c:if test="${currentUser.userId eq user.userId || isAdmin}">
        <form action="/user/updateUserPhoneNumber/${user.userId}">
            <button type="submit" class="btn btn-primary btn-md">Update
                phone number
            </button>
        </form>
    </c:if>
    <br>
</div>
</body>
</html>
