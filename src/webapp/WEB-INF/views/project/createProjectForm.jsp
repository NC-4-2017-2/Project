<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>Create project form</title>
</head>
<body>
<form action="createProject/${countSprints}/${countWorkers}" method="post">
    <table border="0">
        <tr>
            <th>
                Project
            </th>
        </tr>
        <tr>
            <td>
                Project name:
            </td>
            <td>
                <input type="text" name="projectName">
            </td>
        </tr>
        <tr>
            <td>
                Project start date:
            </td>
            <td>
                <input type="date" name="projectStartDate">
            </td>
        </tr>
        <tr>
            <td>
                Project end date:
            </td>
            <td>
                <input type="date" name="projectEndDate">
            </td>
        </tr>
        <tr>
            <td>
                Choose pm:
            </td>
            <td>
                <select name="pmId" required>
                    <c:forEach items="${pmOnTransitList}" var="pm">
                        <option value="${pm.userId}">${pm.lastName} ${pm.firstName} ${pm.dateOfBirth}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
    </table>
    <table border="0">
        <tr>
            <th>
                Sprints
            </th>
        </tr>
        <c:forEach begin="1" end="${countSprints}" varStatus="sprint">
            <tr>
                <td>
                    Sprint ${sprint.count}
                </td>
            </tr>
            <tr>
                <td>
                    Sprint name:
                </td>
                <td>
                    <input type="text"
                           name="sprintName${sprint.count - 1}">
                </td>
            </tr>
            <tr>
                <td>
                    Start date:
                </td>
                <td>
                    <input type="date"
                           name="startDate${sprint.count - 1}">
                </td>
            </tr>
            <tr>
                <td>
                    End date:
                </td>
                <td>
                    <input type="date"
                           name="planedEndDate${sprint.count - 1}">
                </td>
            </tr>
            <br><br>
        </c:forEach>
    </table>
    <table border="0">
        <tr>
            <th>
                Workers
            </th>
        </tr>
        <c:forEach begin="1" end="${countWorkers}" varStatus="worker">
            <tr>
                <td>
                    Worker ${worker.count}
                </td>
            </tr>
            <tr>
                <td>
                    User first name:
                </td>
                <td>
                    <input type="text"
                           name="userFirstName${worker.count - 1}">
                </td>
            </tr>
            <tr>
                <td>
                    User last name:
                </td>
                <td>
                    <input type="text"
                           name="userLastName${worker.count - 1}">
                </td>
            </tr>
            <tr>
                <td>
                    Start date:
                </td>
                <td>
                    <input type="date"
                           name="startDate${worker.count - 1}">
                </td>
            </tr>
            <tr>
                <td>
                    End date:
                </td>
                <td>
                    <input type="date" name="endDate${worker.count - 1}">
                </td>
            </tr>
            <br><br>
        </c:forEach>
    </table>
    <%@include file="../errors/errorMap.jsp" %>
    <input type="submit" value="Create"/></td>
</form>
</body>
</html>
