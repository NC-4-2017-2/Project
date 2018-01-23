<form action="/workingDay/viewPMWorkingDay">
    <div class="form-group">
        <label for="status">Choose status:</label>
        <div class="input-group">
            <select name="status" id="status" required>
                <option value="APPROVED">Approved</option>
                <option value="DISAPPROVED">Disapproved</option>
                <option value="WAITING_FOR_APPROVAL">Waiting for approval
                </option>
            </select>
        </div>
        <br>
        <div class="form-group">
            <input type="submit" class="btn btn-primary btn-md" value="Find">
        </div>
    </div>
</form>