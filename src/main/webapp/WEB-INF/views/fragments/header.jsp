<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<sec:authentication var="principal" property="principal.username"/>
<sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin"/>
<sec:authorize access="hasRole('ROLE_PM')" var="isPM"/>
<sec:authorize access="hasRole('ROLE_LM')" var="isLM"/>
<sec:authorize access="hasRole('ROLE_SE')" var="isSE"/>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">ERP System</a>
        </div>
        <c:if test="${isAdmin}">
        <ul class="nav navbar-nav">
            <li class="active"><a href="/">Home</a></li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">User
                    <span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="/user/createUser">Create user</a></li>
                    <li><a href="/user/findUserByLastNameAndFirstName">Find user by name</a></li>
                    <li><a href="/user/findUserByProjectName">Find user by project name</a></li>
                </ul>
            </li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">Task
                    <span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="/task/findOwnTask">Find own task</a></li>
                </ul>
            </li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">Project
                    <span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="/project/createProject">Create project</a></li>
                    <li><a href="/project/findProjectByStartDate">Find project by start date</a></li>
                </ul>
            </li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">Vacation
                    <span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="/vacation/findVacationByStatus">Find vacation by status</a></li>
                </ul>
            </li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">Business trip
                    <span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="/businessTrip/createBusinessTrip">Create business trip</a></li>
                    <li><a href="/businessTrip/findTrip">Find business trip by status</a></li>
                    <li><a href="/businessTrip/findTripByStatusPerPeriod">Find business trip by period</a></li>
                </ul>
            </li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">Working day
                    <span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="/workingDay/createWorkingDay">Create working day</a></li>
                    <li><a href="/workingDay/findUserWorkingDayByStatus">Find working day by status</a></li>
                    <li><a href="/workingDay/findWorkingDayPerPeriod">Find working day per period</a></li>
                </ul>
            </li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">Statistic
                    <span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="/statistic/findOwnTaskStatByPeriod">Task statistic</a></li>
                    <li><a href="/statistic/findOwnHoursStatByPeriod">Working day statistic</a></li>
                </ul>
            </li>
        </ul>
        </c:if>
        <c:if test="${isPM}">
            <ul class="nav navbar-nav">
                <li class="active"><a href="/">Home</a></li>
                <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">User
                    <span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="/user/findUserByLastNameAndFirstName">Find user by name</a></li>
                    <li><a href="/user/findUserByProjectName">Find user by project name</a></li>
                </ul>
            </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Task
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/task/selectTaskType">Create task</a></li>
                        <li><a href="/task/findTaskByPriority">Find task by priority</a></li>
                        <li><a href="/task/findTaskPerPeriodAndStatus">Find task by status</a></li>
                        <li><a href="/task/findTaskByFirstAndLastName">Find task by user name</a></li>
                        <li><a href="/task/findTaskByProject">Find task by project</a></li>
                        <li><a href="/task/findOwnTask">Find own task</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Project
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/project/findProjectByStartDate">Find project by start date</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Vacation
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/vacation/findVacationByStatus">Find vacation by status</a></li>
                        <li><a href="/vacation/findVacationByManagerStatus">Find vacation by manager status</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Business trip
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/businessTrip/createBusinessTrip">Create business trip</a></li>
                        <li><a href="/businessTrip/findTrip">Find business trip by status</a></li>
                        <li><a href="/businessTrip/findTripByStatusPerPeriod">Find business trip by period</a></li>
                        <li><a href="/businessTrip/findPMTrip">Find PM business trip</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Working day
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/workingDay/createWorkingDay">Create working day</a></li>
                        <li><a href="/workingDay/findUserWorkingDayByStatus">Find working day by status</a></li>
                        <li><a href="/workingDay/findWorkingDayPerPeriod">Find working day per period</a></li>
                        <li><a href="/workingDay/findPMWorkingDay">Find PM working day</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Statistic
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/statistic/findOwnTaskStatByPeriod">Task statistic</a></li>
                        <li><a href="/statistic/findOwnHoursStatByPeriod">Working day statistic</a></li>
                        <li><a href="/statistic/viewSprintStat">Sprint statistic</a></li>
                        <li><a href="/statistic/findProjectTaskByPeriod">Project task statistic</a></li>
                        <li><a href="/statistic/findUserTaskByFirstNameAndLastName">User task statistic</a></li>
                        <li><a href="/statistic/findUserHoursStat">User working hours statistic</a></li>
                        <li><a href="/statistic/projectWorkers">Project workers statistic</a></li>
                        <li><a href="/statistic/findVacationProjectStatByPeriod">Project vacation statistic</a></li>
                    </ul>
                </li>
            </ul>
        </c:if>
        <c:if test="${isLM}">
            <ul class="nav navbar-nav">
                <li class="active"><a href="/">Home</a></li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">User
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/user/findUserByLastNameAndFirstName">Find user by name</a></li>
                        <li><a href="/user/findUserByProjectName">Find user by project name</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Task
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/task/findOwnTask">Find own task</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Project
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Find project by start date</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Vacation
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/vacation/findVacationByStatus">Find vacation by status</a></li>
                        <li><a href="/vacation/findVacationByManagerStatus">Find vacation by manager status</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Business trip
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/businessTrip/createBusinessTrip">Create business trip</a></li>
                        <li><a href="/businessTrip/findTrip">Find business trip by status</a></li>
                        <li><a href="/businessTrip/findTripByStatusPerPeriod">Find business trip by period</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Working day
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/workingDay/createWorkingDay">Create working day</a></li>
                        <li><a href="/workingDay/findUserWorkingDayByStatus">Find working day by status</a></li>
                        <li><a href="/workingDay/findWorkingDayPerPeriod">Find working day per period</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Statistic
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/statistic/findOwnTaskStatByPeriod">Task statistic</a></li>
                        <li><a href="/statistic/findOwnHoursStatByPeriod">Working day statistic</a></li>
                    </ul>
                </li>
            </ul>
        </c:if>
        <c:if test="${isSE}">
            <ul class="nav navbar-nav">
                <li class="active"><a href="/">Home</a></li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">User
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/user/findUserByLastNameAndFirstName">Find user by name</a></li>
                        <li><a href="/user/findUserByProjectName">Find user by project name</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Task
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/task/findOwnTask">Find own task</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Project
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Find project by start date</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Vacation
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/vacation/findVacationByStatus">Find vacation by status</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Business trip
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/businessTrip/createBusinessTrip">Create business trip</a></li>
                        <li><a href="/businessTrip/findTrip">Find business trip by status</a></li>
                        <li><a href="/businessTrip/findTripByStatusPerPeriod">Find business trip by period</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Working day
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/workingDay/createWorkingDay">Create working day</a></li>
                        <li><a href="/workingDay/findUserWorkingDayByStatus">Find working day by status</a></li>
                        <li><a href="/workingDay/findWorkingDayPerPeriod">Find working day per period</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Statistic
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/statistic/findOwnTaskStatByPeriod">Task statistic</a></li>
                        <li><a href="/statistic/findOwnHoursStatByPeriod">Working day statistic</a></li>
                    </ul>
                </li>
            </ul>
        </c:if>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="/user/showUser/${principal}"><span
                    class="glyphicon glyphicon-user"></span>${principal}</a>
            </li>
            <li><a href="/logout"><span
                    class="glyphicon glyphicon-log-out"></span>Log out</a></li>
        </ul>
    </div>
</nav>