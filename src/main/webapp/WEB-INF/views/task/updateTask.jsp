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
<div class="container">
    <div class="row">
        <div class="col-lg-6 col-lg-offset-4">
    <form action="/task/updateTask/${taskId}" method="post" commandName=taskForm">


            <div class="form-group">
                <label for="name">Task name:</label>
                <div class="input-group">
                    <input type="text" class="form-control" id="name" name="name"
                           value=${task.name}>
                </div>
            </div>


            <select class="taskType" id="taskType" name="taskType" hidden="false">
                <option value="REQUEST_TASK">REQUEST_TASK</option>
                <option selected="selected" value="PROJECT_TASK">PROJECT_TASK</option>
            </select>


            <div class="form-group">
                <label for="startDate">Start date:</label>
                <div class="input-group">
                    <input type="date" class="form-control" id="startDate"
                           name="startDate" value=${task.startDate}></div>
            </div>



            <input type="date" class="endDate" id="endDate" name="endDate"
                    hidden="false"  value=${task.endDate}>


            <div class="form-group">
                <label for="plannedEndDate">Planned end date:</label>
                <div class="input-group">
                    <input type="date" class="form-control" id="plannedEndDate"
                           name="plannedEndDate" value=${task.plannedEndDate} required>
                </div>
            </div>

        <select class="priority" style="width: 176px; height: 30px;" id="priority" name="priority" hidden="false">
                    <option value="CRITICAL">Critical</option>
                    <option value="HIGH">High</option>
                    <option value="NORMAL">Normal</option>
                    <option value="LOW">Low</option>
        </select>

            <c:if test="${taskUser.userId eq curUser.userId or curUser.jobTitle.name() eq 'PROJECT_MANAGER'}">
                <label for="status">Status of task:</label>
                <div class="form-group">
                    <select class="status" style="width: 176px; height: 30px;" id="status" name="status">
                        <option value="OPENED">Opened</option>
                        <option value="CLOSED">Closed</option>
                        <option value="REOPENED">Reopened</option>
                        <option value="READY_FOR_TESTING">Ready for testing</option>
                    </select>
                </div>
            </c:if>

            <input type="text" class="reopenCounter" id="reopenCounter" name="reopenCounter"
                   hidden = false  value=${task.reopenCounter}>


        <input type="text" class="lastName" id="lastName" name="lastName" hidden="false"
                           value=${taskUser.lastName} >

        <input type="text" class="firstName" id="firstName" name="firstName" hidden="false"
                           value=${taskUser.firstName} >


        <label for="projectNames">Project:</label>
            <div class="form-group">
                <select name="projectNames" style="width: 176px; height: 30px;" id = "projectNames">
                    <c:forEach items="${projectNamesList}" var="projectNames" >
                        <option value="${projectNames}">${projectNames}</option>
                    </c:forEach>
                </select>
            </div>



        <div class="form-group">
            <label for="description">Description of task:</label>
            <div class="input-group">
                <textarea type="text" class="form-control" width="100" id="description" name="description"
                          style = "resize:none;" cols = "40" value=${task.description}>
                </textarea>
            </div>
        </div>


        <br>
            <br>

            <div class="form-group">
                <input onclick="window.history.go(-1); return false;" type="button"
                       class="btn btn-primary btn-md" value="Back"/>

            <c:if test="${taskUser.userId eq curUser.userId or curUser.jobTitle.name() eq 'PROJECT_MANAGER'}">
                <td><input type="submit" value="Update" class="btn btn-primary btn-md"/></td>
            </c:if>

            <%@include file="../errors/errorMap.jsp" %>
            </div>

            </form>
        </div>
    </div>
</div>
</body>
</html>