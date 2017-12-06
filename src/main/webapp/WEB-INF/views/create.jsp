<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Registration</title>
</head>
<body>
<div align="center">
    <form action="/project/create" method="post" name="/project/create"
               commandName="projectForm">
        <table border="0">
            <tr>
                <h2>CreateProject</h2></td>
            </tr>
            <tr>
                <td>Project Id:</td>
                <td><input type="text" name="projectId" size="70"/></td>
            </tr>
            <tr>
                <td>Project Name:</td>
                <td><input type="text" name="name" size="70"/></td>
            </tr>
            <tr>
                <td>StartDate (mm/dd/yyyy):</td>
                <td><input type="text" name="startDate" size="70"/></td>
            </tr>
            <tr>
                <td>EndDate (mm/dd/yyyy):</td>
                <td><input type="text" name="endDate" size="70"/></td>
            </tr>
            <tr>
                <td>Status:</td>
                <td><input type="text" name="projectStatus" size="70"/></td>
            </tr>
            <tr>
                <td>Project Manager:</td>
                <td><input type="text" name="projectManagerId" size="70"/></td>
            </tr>
            <tr>
                <input type="submit" value="Create"/></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>