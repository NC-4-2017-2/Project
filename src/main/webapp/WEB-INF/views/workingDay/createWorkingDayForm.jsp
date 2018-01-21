<form action="/workingDay/createWorkingDay" method="post">
    <table border="0">
        <tr>
            <td>Monday:</td>
            <td><input name="monday" type="time"></td>
            <c:choose>
                <c:when test="${MondayTime.status.name() eq 'APPROVED'}">
                    <td style="color: #0000FF; font-size:17pt;">    ${MondayTime.workingHours}</td>
                </c:when>
                <c:when test="${MondayTime.status.name() eq 'DISAPPROVED'}">
                    <td style="color: #8B0000; font-size:17pt;">    ${MondayTime.workingHours}</td>
                </c:when>
                <c:when test="${MondayTime.status.name() eq 'WAITING_FOR_APPROVAL'}">
                    <td style="color: #FF4500; font-size:17pt;">    ${MondayTime.workingHours}</td>
                </c:when>
            </c:choose>
        </tr>
        <tr>
            <td>Tuesday:</td>
            <td><input name="tuesday" type="time"></td>
            <c:choose>
                <c:when test="${TuesdayTime.status.name() eq 'APPROVED'}">
                    <td style="color: #0000FF; font-size:17pt">     ${TuesdayTime.workingHours}</td>
                </c:when>
                <c:when test="${TuesdayTime.status.name() eq 'DISAPPROVED'}">
                    <td style="color: #8B0000; font-size:17pt">     ${TuesdayTime.workingHours}</td>
                </c:when>
                <c:when test="${TuesdayTime.status.name() eq 'WAITING_FOR_APPROVAL'}">
                    <td style="color: #FF4500; font-size:17pt">     ${TuesdayTime.workingHours}</td>
                </c:when>
            </c:choose>
        </tr>
        <tr>
            <td>Wednesday:</td>
            <td><input name="wednesday" type="time"></td>
            <c:choose>
                <c:when test="${WednesdayTime.status.name() eq 'APPROVED'}">
                    <td style="color: #0000FF; font-size:17pt">     ${WednesdayTime.workingHours}</td>
                </c:when>
                <c:when test="${WednesdayTime.status.name() eq 'DISAPPROVED'}">
                    <td style="color: #8B0000; font-size:17pt">     ${WednesdayTime.workingHours}</td>
                </c:when>
                <c:when test="${WednesdayTime.status.name() eq 'WAITING_FOR_APPROVAL'}">
                    <td style="color: #FF4500; font-size:17pt">     ${WednesdayTime.workingHours}</td>
                </c:when>
            </c:choose>
        </tr>
        <tr>
            <td>Thursday:</td>
            <td><input name="thursday" type="time"></td>
            <c:choose>
                <c:when test="${ThursdayTime.status.name() eq 'APPROVED'}">
                    <td style="color: #0000FF; font-size:17pt">     ${ThursdayTime.workingHours}</td>
                </c:when>
                <c:when test="${ThursdayTime.status.name() eq 'DISAPPROVED'}">
                    <td style="color: #8B0000; font-size:17pt">     ${ThursdayTime.workingHours}</td>
                </c:when>
                <c:when test="${ThursdayTime.status.name() eq 'WAITING_FOR_APPROVAL'}">
                    <td style="color: #FF4500; font-size:17pt">     ${ThursdayTime.workingHours}</td>
                </c:when>
            </c:choose>
        </tr>
        <tr>
            <td>Friday:</td>
            <td><input name="friday" type="time"></td>
            <c:choose>
                <c:when test="${FridayTime.status.name() eq 'APPROVED'}">
                    <td style="color: #0000FF; font-size:17pt">     ${FridayTime.workingHours}</td>
                </c:when>
                <c:when test="${FridayTime.status.name() eq 'DISAPPROVED'}">
                    <td style="color: #8B0000; font-size:17pt">     ${FridayTime.workingHours}</td>
                </c:when>
                <c:when test="${FridayTime.status.name() eq 'WAITING_FOR_APPROVAL'}">
                    <td style="color: #FF4500; font-size:17pt">     ${FridayTime.workingHours}</td>
                </c:when>
            </c:choose>
        </tr>
        <tr>
            <td>Saturday:</td>
            <td><input name="saturday" type="time"></td>
            <c:choose>
                <c:when test="${SaturdayTime.status.name() eq 'APPROVED'}">
                    <td style="color: #0000FF; font-size:17pt">     ${SaturdayTime.workingHours}</td>
                </c:when>
                <c:when test="${SaturdayTime.status.name() eq 'DISAPPROVED'}">
                    <td style="color: #8B0000; font-size:17pt">     ${SaturdayTime.workingHours}</td>
                </c:when>
                <c:when test="${SaturdayTime.status.name() eq 'WAITING_FOR_APPROVAL'}">
                    <td style="color: #FF4500; font-size:17pt">     ${SaturdayTime.workingHours}</td>
                </c:when>
            </c:choose>
        </tr>
        <tr>
            <td>Sunday:</td>
            <td><input name="sunday" type="time"></td>
            <c:choose>
                <c:when test="${SundayTime.status.name() eq 'APPROVED'}">
                    <td style="color: #0000FF; font-size:17pt">     ${SundayTime.workingHours}</td>
                </c:when>
                <c:when test="${SundayTime.status.name() eq 'DISAPPROVED'}">
                    <td style="color: #8B0000; font-size:17pt">     ${SundayTime.workingHours}</td>
                </c:when>
                <c:when test="${SundayTime.status.name() eq 'WAITING_FOR_APPROVAL'}">
                    <td style="color: #FF4500; font-size:17pt">     ${SundayTime.workingHours}</td>
                </c:when>
            </c:choose>
        </tr>
        <tr>
            <td style="color: #0000FF; font-size:10pt" colspan="3">
                approved
            </td>
        </tr>
        <tr>
            <td style="color: #8B0000; font-size:10pt" colspan="3">
                disapproved
            </td>
        </tr>
        <tr>
            <td style="color: #FF4500; font-size:10pt" colspan="3">
                waiting for approval
            </td>
        </tr>
    </table>
    <br>
    <div class="form-group">
        <input type="submit" class="btn btn-primary btn-md" value="Create">
    </div>
</form>