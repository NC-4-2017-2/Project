package main.com.netcracker.project.model.entity;

import java.math.BigInteger;
import java.util.Date;

public class Vacation {

    private BigInteger vacationId;
    private BigInteger userId;
    private BigInteger projectId;
    private Date startDate;
    private Date endDate;
    private Status pmStatus;
    private Status lmStatus;
    private BigInteger pmId;
    private BigInteger lmId;

    private Vacation(VacationBuilder builder) {
        this.vacationId = builder.vacationId;
        this.userId = builder.userId;
        this.projectId = builder.projectId;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.pmStatus = builder.pmStatus;
        this.lmStatus = builder.lmStatus;
        this.pmId = builder.pmId;
        this.lmId = builder.lmId;
    }

    public BigInteger getVacationId() {
        return vacationId;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public BigInteger getProjectId() {
        return projectId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Status getPmStatus() {
        return pmStatus;
    }

    public Status getLmStatus() {
        return lmStatus;
    }

    public BigInteger getPmId() {
        return pmId;
    }

    public BigInteger getLmId() {
        return lmId;
    }

    public static class VacationBuilder {
        private BigInteger vacationId;
        private BigInteger userId;
        private BigInteger projectId;
        private Date startDate;
        private Date endDate;
        private Status pmStatus;
        private Status lmStatus;
        private BigInteger pmId;
        private BigInteger lmId;

        public VacationBuilder() {
        }

        public VacationBuilder vacationId(BigInteger vacationId) {
            this.vacationId = vacationId;
            return this;
        }


        public VacationBuilder userId(BigInteger userId) {
            this.userId = userId;
            return this;
        }

        public VacationBuilder projectId(BigInteger projectId) {
            this.projectId = projectId;
            return this;
        }

        public VacationBuilder startDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public VacationBuilder endDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }

        public VacationBuilder pmStatus(Status pmStatus) {
            this.pmStatus = pmStatus;
            return this;
        }

        public VacationBuilder lmStatus(Status lmStatus) {
            this.lmStatus = lmStatus;
            return this;
        }

        public VacationBuilder pmId(BigInteger pmId) {
            this.pmId = pmId;
            return this;
        }

        public VacationBuilder lmId(BigInteger lmId) {
            this.lmId = lmId;
            return this;
        }

        public Vacation build() {
            return new Vacation(this);
        }

    }
}
