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
    <title>Edit Project</title>
</head>
<body>
<div class="project">
    <form action="/project/edit={id}" method="post">
        <table border="0">
            <tr>
                <h2>Edit Project</h2>
            </tr>
            <tr>
                <td>Project Id:</td>
                <td><input type="text" name="projectId" size="70" readonly="readonly"
                           value=${projectId}></td>
            </tr>
            <tr>
                <td>Project Name:</td>
                <td><input type="text" name="name" size="70" readonly="readonly"
                           value=${projectName}></td>
            </tr>
            <tr>
                <td>StartDate (mm/dd/yyyy):</td>
                <td><input type="text" name="startDate" size="70" readonly="readonly"
                           value=${startDate}></td>
            </tr>
            <tr>
                <td>EndDate (mm/dd/yyyy):</td>
                <td><input type="text" name="endDate" size="70"
                           value=${endDate}></td>
            </tr>
            <tr>
                <td>Status:</td>
                <td> <select name="projectStatus" >
                    <option value="OPENED">OPENED</option>
                        <option value="CLOSED">CLOSED</option>
                </select></td>
            </tr>
            <tr>
                <td>Project Manager:</td>
                <td><input type="text" name="projectManagerId" size="70"
                           value=${pmId}></td>
            </tr>

            <tr>
                <td>Sprints:</td>
                <%--@elvariable id="modelSprint" type="com.netcracker.project.controllers.ProjectController"--%>
                <form:form modelAttribute="modelSprint" method="post">
                <c:forEach items="${modelSprint.sprints}" var="sprint" varStatus="status">
                    <input type="hidden" name="sprints[${status.index}].id" value="${sprint.id}"><br>
                    Sprint ${status.index} :<br>
                    Name: <input typ
                                 e="text" name="sprints[${status.index}].name" value="${sprint.name}" readonly="readonly"><br>
                    Planned end date: <input type="text" name="sprints[${status.index}].plannedEndDate" value="${sprint.plannedEndDate}"></td><br>
                    <select name="sprints[${status.index}].sprintStatus" >
                        <option value="OPENED">OPENED</option>
                        <option value="CLOSED">CLOSED</option>
                    </select><br>
                    <br>
                </c:forEach>
            </tr>
            <tr>
                <td>Workers:</td>
                <form modelAttribute="modelWork" >
                    <c:forEach items="${modelWork.workers}" var="worker" varStatus="status">
                        <input type="hidden" name="workers[${status.index}].workPeriodId" value="${worker.workPeriodId}"><br>
                        Worker ${status.index} :<br>
                        User id:    <input type="text" name="workers[${status.index}].userId" value="${worker.userId}" placeholder="User id"  readonly="readonly"><br>
                        Start date: <input type="text" name="workers[${status.index}].startWorkDate" value="${worker.startWorkDate}" placeholder="Start Work Date" readonly="readonly"><br>
                        End date:   <input type="text" name="workers[${status.index}].endWorkDate" value="${worker.endWorkDate}" placeholder="End Work Date"><br>
                        <select name="workers[${status.index}].workPeriodStatus" >
                            <option value="WORKING">WORKING</option>
                            <option value="FIRED">FIRED</option>
                        </select><br>
                        <br>
                    </c:forEach>
                </form>
            </tr>
            </form:form>

            <tr>
                <input type="submit" value="Edit"/>
            </tr>
        </table>
    </form>
</div>
</body>
</html>