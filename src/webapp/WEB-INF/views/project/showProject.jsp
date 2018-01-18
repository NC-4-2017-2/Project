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
    <%@include file="showProjectForm.jsp" %>
    <c:if test="${currentUser.userId eq projectManager.userId && project.projectStatus ne 'CLOSED'}">
    <form id="closeProject" action="/project/closeProject/${project.projectId}" method="post">
    </form>
        <div class="btn-toolbar" role="toolbar">
            <div class="btn-group mr-2" role="group">
                <a href="/project/showProjectUsersToDelete/${project.projectId}"
                   class="btn btn-primary btn-md">Show users</a>
                <a href="/project/userToAdd/${project.projectId}"
                   class="btn btn-primary btn-md">Add user</a>
                <a href="javascript:;" onclick="document.getElementById('closeProject').submit();"
                   class="btn btn-primary btn-md">Close project</a>
                <a href="/project/viewSprints/${project.projectId}"
                   class="btn btn-primary btn-md">Show sprint list</a>
                <a href="/project/createSprint/${project.projectId}"
                   class="btn btn-primary btn-md">Add sprint</a>
            </div>
        </div>
    </c:if>
    <%@include file="../errors/errorMap.jsp" %>
</div>
</body>
</html>
