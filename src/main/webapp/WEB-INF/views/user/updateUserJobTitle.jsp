<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update job title</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
    <script>
      $(document).ready(function () {
        $("#admin").change(function () {
          if ($('#admin').prop('checked')) {
            $(".jobTitle").prop("disabled", true);
          }
          else {
            $(".jobTitle").prop("disabled", false);
          }
        });
      });
    </script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<form action="/user/updateJobTitle/${user.userId}"
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
                       value="${user.email}"
                       pattern="^(\S+)@([a-z0-9-]+)(\.)([a-z]{2,4})(\.?)([a-z]{0,4})+$"
                       disabled>
            </div>
        </div>
        <div class="form-group">
            <label for="jobTitle">JobTitle: </label>
            <div class="input-group">
                <select class="jobTitle" id="jobTitle" name="jobTitle">
                    <option value="PROJECT_MANAGER">PROJECT_MANAGER</option>
                    <option value="LINE_MANAGER">LINE_MANAGER</option>
                    <option selected="selected" value="SOFTWARE_ENGINEER">
                        SOFTWARE_ENGINEER
                    </option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="admin">Admin: </label>
            <input type="checkbox"
                   onchange="jobTitle.value='SOFTWARE_ENGINEER';" name="admin"
                   id="admin" value="true"/>
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
