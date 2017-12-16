<%@ page import="com.netcracker.project.config.Constants.DB" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Size</title>
</head>
<body>
<%--<c:forEach var="inputLine" begin="1" end="4">
        <td><input type="text" name="projectManagerId" size="70" value={count} ></td><br>
</c:forEach>--%>
<form action="/project/create" method="post" name="/project/create"
      commandName="projectSizeForm">
    <tr>
        <td>Count of sprints:</td>
        <td><input type="text" name="countSprints" size="10"
                   value=${countSprints}></td>
    </tr>
    <br>
    <tr>
        <td>Count of workers:</td>
        <td><input type="text" name="countWorkers" size="10"
                   value=${countWorkers}></td>
    </tr>
    <tr>
        <input type="submit" value="Next"/></td>
    </tr>
</form>

</body>
</html>
