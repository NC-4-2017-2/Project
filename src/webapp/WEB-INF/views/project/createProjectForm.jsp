<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Create project form</title>
</head>
<body>
<form action="createProject/${countSprints}/${countWorkers}" method="post">
    Project name : <input type="text" name="projectName"><br>
    Project start date : <input type="text" name="projectStartDate"><br>
    Project end date : <input type="text" name="projectEndDate"><br>
    Choose pm : <select name="pmId" required>
    <c:forEach items="${pmOnTransitList}" var="pm">
        <option value="${pm.userId}">${pm.lastName} ${pm.firstName} ${pm.dateOfBirth}</option>
    </c:forEach>
</select><br>

    <c:forEach begin="1" end="${countSprints}" varStatus="sprint">
        Sprint name: <input type="text"
                            name="sprintName${sprint.count - 1}"><br>
        Start date: <input type="date" name="startDate${sprint.count - 1}"><br>
        End date : <input type="date"
                          name="planedEndDate${sprint.count - 1}"><br><br>
    </c:forEach>

    <c:forEach begin="1" end="${countWorkers}" varStatus="worker">
        User first name: <input type="text"
                                name="userFirstName${worker.count - 1}"><br>
        User last name: <input type="text"
                               name="userLastName${worker.count - 1}"><br>
        Start date : <input type="date" name="startDate${worker.count - 1}"><br><br>
        End date : <input type="date" name="endDate${worker.count - 1}"><br><br>
    </c:forEach>
    <input type="submit" value="Next"/></td>
</form>
</body>
</html>
