<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Find user by project name</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<form action="/user/findUserByProjectName">
    <div class="col col-md-8">
        <table border="0">
            <tr>
                <td>Project name:</td>
                <td><input type="text" name="projectName" size="20"
                           required placeholder="Project name"></td>
            </tr>

            <tr>
                <td><input type="submit" class="btn btn-primary btn-md"
                           value="Find"/></td>
            </tr>
        </table>
    </div>
</form>
</body>
</html>
