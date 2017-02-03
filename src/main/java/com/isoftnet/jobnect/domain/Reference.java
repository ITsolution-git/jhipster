package com.isoftnet.jobnect.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.isoftnet.jobnect.domain.enumeration.JobReferenceType;

/**
 * A Reference.
 */
@Entity
@Table(name = "reference")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Size(max = 50)
    @Column(name = "full_name", length = 50, nullable = false)
    private String fullName;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "reference_type")
    private JobReferenceType referenceType;

    @Size(max = 50)
    @Column(name = "company", length = 50)
    private String company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public Reference userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public Reference fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Reference phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Reference email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public JobReferenceType getReferenceType() {
        return referenceType;
    }

    public Reference referenceType(JobReferenceType referenceType) {
        this.referenceType = referenceType;
        return this;
    }

    public void setReferenceType(JobReferenceType referenceType) {
        this.referenceType = referenceType;
    }

    public String getCompany() {
        return company;
    }

    public Reference company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reference reference = (Reference) o;
        if (reference.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, reference.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Reference{" +
            "id=" + id +
            ", userId='" + userId + "'" +
            ", fullName='" + fullName + "'" +
            ", phoneNumber='" + phoneNumber + "'" +
            ", email='" + email + "'" +
            ", referenceType='" + referenceType + "'" +
            ", company='" + company + "'" +
            '}';
    }
}
