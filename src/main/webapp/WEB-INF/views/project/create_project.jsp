<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<html>
<head>
    <style type="text/css">
        div.project{
            align: center;
            text-align: center;
        }
    </style>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create Project</title>
</head>
<body>
<div class="project">
    <form action="/project/create_project" method="post" name="/project/create_project"
          commandName="projectForm">
        <table border="0">
            <tr>
                <h2>CreateProject</h2></td>
            </tr>
            <tr>
                <td>Project Id:</td>
                <td><input type="text" name="projectId" size="70"
                           value=${projectId}></td>
            </tr>
            <tr>
                <td>Project Name:</td>
                <td><input type="text" name="name" size="70"
                           value=${projectName}></td>
            </tr>
            <tr>
                <td>StartDate (mm/dd/yyyy):</td>
                <td><input type="text" name="startDate" size="70"
                           value=${startDate}></td>
            </tr>
            <tr>
                <td>EndDate (mm/dd/yyyy):</td>
                <td><input type="text" name="endDate" size="70"
                           value=${endDate}></td>
            </tr>
            <tr>
                <td>Status:</td>
                <td>
                    <select name="projectStatus" >
                        <option value="OPENED">OPENED</option>
                        <option value="CLOSED">CLOSED</option>
                    </select>
            </tr>
            <tr>
                <td>Project Manager:</td>
                <td><input type="text" name="projectManagerId" size="70"
                           value=${pmId}></td>
            </tr>
            <tr>
                <td>Sprints</td>
                <form:form modelAttribute="modelSprint" method="post">
                    <c:forEach items="${modelSprint.sprints}" var="sprint" varStatus="status">
                        Sprint ${status.index} :<br>
                        <input type="text" name="sprints[${status.index}].name" value="${sprint.name}" placeholder="Name"></td><br>
                        <input type="text" name="sprints[${status.index}].startDate" value="${sprint.startDate}" placeholder="Start Date"><br>
                        <input type="text" name="sprints[${status.index}].plannedEndDate" value="${sprint.plannedEndDate}" placeholder="End date"><br>
                        <br>
                    </c:forEach>
            </tr>
            <tr>
                <td>Workers:</td>
                <form modelAttribute="modelWork" >
                    <c:forEach items="${modelWork.workers}" var="worker" varStatus="status">
                        Worker ${status.index} :<br>
                        <input type="text" name="workers[${status.index}].name" value="${worker.name}" placeholder="Name"></td><br>
                        <br>
                    </c:forEach>
                </form>
            </tr>
            </form:form>
            <tr>
                <input type="submit" value="Create"/></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>