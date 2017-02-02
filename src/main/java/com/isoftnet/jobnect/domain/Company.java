package com.isoftnet.jobnect.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "company_name", length = 50, nullable = false)
    private String companyName;

    @Column(name = "department")
    private String department;

    @Column(name = "website")
    private String website;

    @NotNull
    @Column(name = "hr_email", nullable = false)
    private String hrEmail;

    @NotNull
    @Column(name = "manager_email", nullable = false)
    private String managerEmail;

    @Column(name = "phone_number")
    private String phoneNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Company companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDepartment() {
        return department;
    }

    public Company department(String department) {
        this.department = department;
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getWebsite() {
        return website;
    }

    public Company website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getHrEmail() {
        return hrEmail;
    }

    public Company hrEmail(String hrEmail) {
        this.hrEmail = hrEmail;
        return this;
    }

    public void setHrEmail(String hrEmail) {
        this.hrEmail = hrEmail;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public Company managerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
        return this;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Company phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Company company = (Company) o;
        if (company.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + id +
            ", companyName='" + companyName + "'" +
            ", department='" + department + "'" +
            ", website='" + website + "'" +
            ", hrEmail='" + hrEmail + "'" +
            ", managerEmail='" + managerEmail + "'" +
            ", phoneNumber='" + phoneNumber + "'" +
            '}';
    }
}
