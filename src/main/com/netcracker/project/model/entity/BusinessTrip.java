package main.com.netcracker.project.model.entity;

import java.math.BigInteger;
import java.util.Date;

public class BusinessTrip {

    private BigInteger businessTripId;
    private BigInteger projectId;
    private BigInteger userId;
    private BigInteger authorId;
    private BigInteger pmId;
    private String country;
    private Date startDate;
    private Date endDate;
    private Status status;

    private BusinessTrip(BusinessTripBuilder builder) {
        this.businessTripId = builder.businessTripId;
        this.projectId = builder.projectId;
        this.userId = builder.userId;
        this.authorId = builder.authorId;
        this.pmId = builder.pmId;
        this.country = builder.country;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.status = builder.status;
    }

    public BigInteger getBusinessTripId() {
        return businessTripId;
    }

    public BigInteger getProjectId() {
        return projectId;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public BigInteger getAuthorId() {
        return authorId;
    }

    public String getCountry() {
        return country;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Status getStatus() {
        return status;
    }

    public BigInteger getPmId() {
        return pmId;
    }

    @Override
    public String toString() {
        return "BusinessTrip{" +
            "businessTripId=" + businessTripId +
            ", projectId=" + projectId +
            ", userId=" + userId +
            ", authorId=" + authorId +
            ", pmId=" + pmId +
            ", country='" + country + '\'' +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", status=" + status +
            '}';
    }

    public static class BusinessTripBuilder {
        private BigInteger businessTripId;
        private BigInteger projectId;
        private BigInteger userId;
        private BigInteger authorId;
        private BigInteger pmId;
        private String country;
        private Date startDate;
        private Date endDate;
        private Status status;

        public BusinessTripBuilder() {

        }

        public BusinessTripBuilder businessTripId(BigInteger businessTripId) {
            this.businessTripId = businessTripId;
            return this;
        }

        public BusinessTripBuilder pmId(BigInteger pmId) {
            this.pmId = pmId;
            return this;
        }

        public BusinessTripBuilder projectId(BigInteger projectId) {
            this.projectId = projectId;
            return this;
        }

        public BusinessTripBuilder userId(BigInteger userId) {
            this.userId = userId;
            return this;
        }

        public BusinessTripBuilder authorId(BigInteger authorId) {
            this.authorId = authorId;
            return this;
        }

        public BusinessTripBuilder country(String country) {
            this.country = country;
            return this;
        }

        public BusinessTripBuilder startDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public BusinessTripBuilder endDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }

        public BusinessTripBuilder status(Status status) {
            this.status = status;
            return this;
        }

        public BusinessTrip build() {
            return new BusinessTrip(this);
        }
    }
}
