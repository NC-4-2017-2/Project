<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit vacation</title>
</head>
<body>

<form action="/vacation/edit={id}" method="post" name="/vacation/edit={id}">
    <table border="0">

        <tr>
            <h2>Create Vacation</h2></td>
        </tr>
        <tr>
            <td><input type="hidden" name="vacationId" size="70" value="${vacationId}">
            </td>
        </tr>
        <tr>
            <td>UserId:</td>
            <td><input type="text" name="userId" value="${userId}" size="70">
            </td>
        </tr>
        <tr>
            Project Name: <select name="projectName">
            <c:forEach items="${projectNamesList}" var="name">
                <option value="${name}">${name}</option>
            </c:forEach>
        </select><br>
        </tr>
        <tr>
            <td>Start Date:</td>
            <td><input type="date" name="startDate" value="${startDate}" size="70">
            </td>
        </tr>
        <tr>
            <td>End Date:</td>
            <td><input type="date" name="endDate" value="${endDate}" size="70">
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
                    <option name="lmApproveStatus" value="${lmApproveStatus}">${lmApproveStatus}
                    </option>
                </select>
        </tr>

    </table>
    <tr>
        <input type="submit" value="Edit"/></td>
    </tr>
</form>
</body>
</html>
