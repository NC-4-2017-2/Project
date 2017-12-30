<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Projects</title>
</head>
<body>
<c:forEach items="${projectList}" var="project">
    <a href="/project/showProject/${project.projectId}">${project.name}</a> <br>
    <br>
</c:forEach>
</body>
</html>
