<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>Create a new task</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
    <form action="/task/createTask" method="post">
       <div class="col-lg-6">
        <div class="form-group">
            <label for="name">Enter task name:</label>
            <div class="input-group">
                <input type="text" class="form-control" id="name" name="name" placeholder="Enter name" required>
            </div>
        </div>


        <select class="taskType" id="taskType" name="taskType" hidden="false">
            <option value="REQUEST_TASK">REQUEST_TASK</option>
            <option selected="selected" value="PROJECT_TASK">PROJECT_TASK</option>
        </select>

        <div class="form-group">
            <label for="startDate">Choose start date:</label>
            <div class="input-group">
                <input type="date" class="form-control" id="startDate" name="startDate" placeholder="Enter start date" required></div>
        </div>

        <div class="form-group">
            <label for="plannedEndDate">Choose planned end date:</label>
            <div class="input-group">
                <input type="date" class="form-control" id="plannedEndDate" name="plannedEndDate" placeholder="Enter planned end date" required></div>
        </div>

           <label for="priority">Choose priority your task:</label>
           <div class="form-group">
                <select class="priority" id="priority" name="priority">
                    <option value="CRITICAL">Critical</option>
                    <option value="HIGH">High</option>
                    <option value="NORMAL">Normal</option>
                    <option selected="selected" value="LOW">Low</option>
                </select>
           </div>

        <div class="form-group">
            <label for="description">Enter description of task:</label>
            <div class="input-group">
                <input type="text" class="form-control" width="100" id="description" name="description" placeholder="Enter description" required></div>
        </div>

        <div class="form-group">
            <label for="lastName">Enter user last name:</label>
            <div class="input-group">
                <input type="text" class="form-control" id="lastName" name="lastName" placeholder="Enter last name" required></div>
        </div>

        <div class="form-group">
            <label for="firstName">Enter user first name:</label>
            <div class="input-group">
                <input type="text" class="form-control" id="firstName" name="firstName" placeholder="Enter first name" required></div>
        </div>

           <label for="projectNames">Choose project for your task:</label>
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
                <input type="submit" value="Create" class="btn btn-primary btn-md"/>
            </div>

           <%@include file="../errors/errorMap.jsp" %>
       </div>
    </form>
</body>
</html>