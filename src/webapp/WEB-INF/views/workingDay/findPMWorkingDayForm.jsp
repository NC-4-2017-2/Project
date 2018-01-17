<form action="/workingDay/viewPMWorkingDay">
    <div class="form-group">
        <label for="status">Choose status:</label>
        <div class="input-group">
            <select name="status" id="status" required>
                <option value="APPROVED">APPROVED</option>
                <option value="DISAPPROVED">DISAPPROVED</option>
                <option value="WAITING_FOR_APPROVAL">WAITING_FOR_APPROVAL
                </option>
            </select>
        </div>
        <div class="form-group">
            <input type="submit" class="btn btn-primary btn-md" value="Find">
        </div>
    </div>
</form>