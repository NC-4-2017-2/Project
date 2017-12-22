<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Project</title>
</head>
<body>
<c:forEach items="${projectCollection}" var="project">
    ${project}
    <br>
</c:forEach>
</body>
</html>
