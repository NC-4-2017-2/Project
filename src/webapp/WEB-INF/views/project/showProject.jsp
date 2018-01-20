<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Project</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<div class="col-lg-6">
    <div class="form-group">
        <table class="table table-hover table-dark" border="3">
            <thead>
            <tr>
                <th scope="col" colspan="2" style="text-align: center;">Project
                    info:
                </th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <th scope="row">Name:</th>
                <td>${project.name}</td>
            </tr>
            <tr>
                <th scope="row">Start date:</th>
                <td>${project.startDate}</td>
            </tr>
            <tr>
                <th scope="row">End date:</th>
                <td>${project.endDate}</td>
            </tr>
            <tr>
                <th scope="row">Project manager:</th>
                <td>${projectManager.lastName} ${projectManager.firstName}</td>
            </tr>
            <tr>
                <th scope="row">Project status:</th>
                <td>${project.projectStatus}</td>
            </tr>
            </tbody>
        </table>
        <br>
    </div>
    <div class="form-group">
        <c:choose>
            <c:when test="${currentUser.userId eq projectManager.userId && project.projectStatus ne 'CLOSED'}">
                <form id="closeProject"
                      action="/project/closeProject/${project.projectId}"
                      method="post">
                </form>
                <div class="btn-toolbar" role="toolbar">
                    <div class="btn-group mr-2" role="group">
                        <input onclick="window.history.go(-1); return false;"
                               type="button" class="btn btn-primary btn-md"
                               value="Back"/>
                        <a href="/project/showProjectUsersToDelete/${project.projectId}"
                           class="btn btn-primary btn-md">Show users</a>
                        <a href="/project/userToAdd/${project.projectId}"
                           class="btn btn-primary btn-md">Add user</a>
                        <a href="javascript:;"
                           onclick="document.getElementById('closeProject').submit();"
                           class="btn btn-primary btn-md">Close project</a>
                        <a href="/project/viewSprints/${project.projectId}"
                           class="btn btn-primary btn-md">Show sprint list</a>
                        <a href="/project/createSprint/${project.projectId}"
                           class="btn btn-primary btn-md">Add sprint</a>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <input onclick="window.history.go(-1); return false;"
                       type="button" class="btn btn-primary btn-md"
                       value="Back"/>
            </c:otherwise>
        </c:choose>
    </div>
    <%@include file="../errors/errorMap.jsp" %>
</div>
</body>
</html>
