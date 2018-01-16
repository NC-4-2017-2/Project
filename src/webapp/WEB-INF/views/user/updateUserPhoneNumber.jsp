<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update phone number</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<form action="/user/updateUserPhoneNumber/${user.userId}"
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
            <label for="phoneNumber">Phone number: </label>
            <div class="input-group">
                <input type="text" name="phoneNumber" id="phoneNumber" size="20"
                       value="${user.phoneNumber}" pattern="\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})"
                       required>
            </div>
        </div>
        <div class="form-group">
            <%@include file="../errors/errorMap.jsp" %>
        </div>
        <div class="form-group">
            <input type="submit" class="btn btn-primary btn-md" value="Update">
        </div>
    </div>
</form>
</body>
</html>
