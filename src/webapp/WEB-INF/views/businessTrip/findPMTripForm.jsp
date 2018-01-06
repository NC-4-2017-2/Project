<form action="/businessTrip/viewPMTrip">
    Choose status:<select name="status" required>
    <option value="APPROVED">APPROVED</option>
    <option value="DISAPPROVED">DISAPPROVED</option>
    <option value="WAITING_FOR_APPROVAL">WAITING_FOR_APPROVAL</option>
</select><br>
    <%@include file="../errors/errorMap.jsp" %>

    <input type="submit" value="Find">
</form>
