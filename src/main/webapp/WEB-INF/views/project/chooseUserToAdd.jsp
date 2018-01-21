<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Chose user</title>
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
            <th scope="col" style="text-align: center;" colspan="6">Choose
                worker:
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${usersList}" var="user">
            <tr>
                <td style="text-align: center;">
                        ${user.lastName}
                </td>
                <td style="text-align: center;">
                        ${user.firstName}
                </td>
                <td style="text-align: center;">
                        ${user.dateOfBirth}
                </td>
                <td style="text-align: center;">
                        ${user.jobTitle}
                </td>
                <td style="text-align: center;">
                        ${user.projectStatus}
                </td>
                <td style="text-align: center;">
                    <form action="/project/addUserFromDuplicate/${projectId}/${startDate}/${endDate}/${user.userId}"
                          method="post">
                        <button type="submit" class="btn btn-primary btn-md"
                                value="Add">Add
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
