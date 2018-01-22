<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>Update task</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<jsp:include page="../fragments/header.jsp"></jsp:include>
    <form action="/task/updateTask/${taskId}" method="post" commandName=taskForm">
        <div class="col-lg-6">

            <div class="form-group">
                <label for="name">Task name:</label>
                <div class="input-group">
                    <input type="text" class="form-control" id="name" name="name"
                           value=${task.name} required>
                </div>
            </div>

            <label for="taskType">Type of task:</label>
            <div class="form-group">
                <select name="taskType" id="taskType" name="taskType">
                    <option value="REQUEST_TASK">REQUEST_TASK</option>
                    <option value="PROJECT_TASK">PROJECT_TASK</option>
                </select>
            </div>

            <div class="form-group">
                <label for="startDate">Start date:</label>
                <div class="input-group">
                    <input type="date" class="form-control" id="startDate"
                           name="startDate" value=${task.startDate}
                           required></div>
            </div>


            <div class="form-group">
                    <label for="endDate">End date:</label>
                    <div class="input-group">
                        <input type="date" class="form-control" id="endDate" name="endDate"
                               value=${task.endDate} required>
                    </div>
            </div>

            <div class="form-group">
                <label for="plannedEndDate">Planned end date:</label>
                <div class="input-group">
                    <input type="date" class="form-control" id="plannedEndDate" name="plannedEndDate" value=${task.plannedEndDate} required>
                </div>
            </div>

            <label for="priority">Priority of task:</label>
            <div class="form-group">
                <select class="priority" id="priority" name="priority">
                    <option value="CRITICAL">CRITICAL</option>
                    <option value="HIGH">HIGH</option>
                    <option value="NORMAL">NORMAL</option>
                    <option value="LOW">LOW</option>
                </select>
            </div>

            <c:if test="${taskUser.userId eq curUser.userId}">
                <label for="status">Status of task:</label>
                <div class="form-group">
                    <select class="status" id="status" name="status">
                        <option value="OPENED">OPENED</option>
                        <option value="CLOSED">CLOSED</option>
                        <option value="REOPENED">REOPENED</option>
                        <option value="READY_FOR_TESTING">READY_FOR_TESTING</option>
                    </select>
                </div>
            </c:if>

            <div class="form-group">
                <label for="description">Description of task:</label>
                <div class="input-group">
                    <input type="text" class="form-control" width="100" id="description" name="description"
                           value=${task.description} required>
                </div>
            </div>

            <input type="text" class="reopenCounter" id="reopenCounter" name="reopenCounter"
                   hidden = false  value=${task.reopenCounter}>


            <div class="form-group">
                <label for="lastName">User last name:</label>
                <div class="input-group">
                    <input type="text" class="form-control" id="lastName" name="lastName"
                           value=${taskUser.lastName} required>
                </div>
            </div>

            <div class="form-group">
                <label for="firstName">User first name:</label>
                <div class="input-group">
                    <input type="text" class="form-control" id="firstName" name="firstName"
                           value=${taskUser.firstName} required>
                </div>
            </div>

            <label for="projectNames">Project:</label>
            <div class="form-group">
                <select name="projectNames" id = "projectNames">
                    <c:forEach items="${projectNamesList}" var="projectNames" >
                        <option value="${projectNames}">${projectNames}</option>
                    </c:forEach>
                </select>
            </div>
            <br>
            <br>

            <div class="form-group">
                <input onclick="window.history.go(-1); return false;" type="button"
                       class="btn btn-primary btn-md" value="Back"/>
            <c:if test="${taskUser.userId eq curUser.userId}">
                <td><input type="submit" value="Update" class="btn btn-primary btn-md"/></td>
            </c:if>

            <%@include file="../errors/errorMap.jsp" %>

        </div>
        </div>
    </form>
</body>
</html>