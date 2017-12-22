<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Size</title>
</head>
<body>

<form action="/vacation/create" method="post" name="/vacation/create">
    <table border="0">

        <tr>
            <h2>Create Vacation</h2></td>
        </tr>
        <tr>
            <td>UserId:</td>
            <td><input type="text" name="userId" size="70">
            </td>
        </tr>
        <tr>
            Project Name: <select name="projectName">
                <c:forEach items="${projectNamesList}" var="projectName">
                    <option value="${projectName}">${projectName}</option>
                </c:forEach>
            </select><br>
        </tr>
        <tr>
            <td>Start Date:</td>
            <td><input type="date" name="startDate" size="70">
            </td>
        </tr>
        <tr>
            <td>End Date:</td>
            <td><input type="date" name="endDate" size="70">
            </td>
        </tr>
        <tr>
            <td>PM approve status:</td>
            <td>
                <select name="pmApproveStatus">
                    <option value="APPROVED">APPROVED</option>
                    <option value="DISAPPROVED">DISAPPROVED</option>
                    <option value="WAITING_FOR_APPROVAL">WAITING FOR APPROVAL</option>
                </select>
        </tr>
        <tr>
            <td>LM approve status:</td>
            <td>
                <select name="lmApproveStatus">
                    <option name="lmApproveStatus" value="WAITING_FOR_APPROVAL">WAITING FOR APPROVAL
                    </option>
                </select>
        </tr>
        <tr>
            <td>PM id:</td>
            <td><input type="text" name="pmId" size="70">
            </td>
        </tr>
        <tr>
            <td>LM id:</td>
            <td><input type="text" name="lmId" size="70">
            </td>
        </tr>
    </table>
    <tr>
        <input type="submit" value="Create"/></td>
    </tr>
</form>
</body>
</html>
