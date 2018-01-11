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
<div class="col-lg-4">
    <%@include file="showProjectForm.jsp" %>
    <%@include file="../errors/errorMap.jsp" %>
    <c:if test="${currentUser.userId eq projectManager.userId && project.projectStatus ne 'CLOSED'}">
        <form action="/project/showProjectUsersToDelete/${project.projectId}">
            <button type="submit" class="btn btn-primary btn-md">Show users
            </button>
        </form>
        <form action="/project/userToAdd/${project.projectId}">
            <button type="submit" class="btn btn-primary btn-md">Add user
            </button>
        </form>
        <form action="/project/closeProject/${project.projectId}" method="post">
            <button type="submit" class="btn btn-primary btn-md">Close project
            </button>
        </form>
        <form action="/project/viewSprints/${project.projectId}">
            <button type="submit" class="btn btn-primary btn-md">Show sprint
                list
            </button>
        </form>
        <form action="/project/createSprint/${project.projectId}">
            <button type="submit" class="btn btn-primary btn-md">Add sprint
            </button>
        </form>
    </c:if>
</div>
</body>
</html>
