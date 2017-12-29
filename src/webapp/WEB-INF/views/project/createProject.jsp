<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Choose sprints size</title>
</head>
<body>
<form action="/project/createProject" method="get">
    <tr>
        <td>Count of sprints:</td>
        <td><input type="number" name="countSprints"></td>
    </tr>
    <br>
    <tr>
        <td>Count of workers:</td>
        <td><input type="number" name="countWorkers"></td>
    </tr>
    <br>
    <tr>
        <input type="submit" value="Next"/></td>
    </tr>
</form>

</body>
</html>
