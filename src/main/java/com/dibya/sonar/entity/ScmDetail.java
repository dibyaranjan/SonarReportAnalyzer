package com.dibya.sonar.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.h2.util.StringUtils;

import com.dibya.sonar.entity.util.DateComparator;

@Entity
@Table(name = "SCM_DETAILS")
public class ScmDetail implements Comparable<ScmDetail> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int lineNumber;
    private String email;
    private Date dateIntroduced;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateIntroduced() {
        return dateIntroduced;
    }

    public void setDateIntroduced(Date dateIntroduced) {
        this.dateIntroduced = dateIntroduced;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dateIntroduced == null) ? 0 : dateIntroduced.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + lineNumber;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }
        ScmDetail other = (ScmDetail) obj;

        if (!DateComparator.areDatesEqual(dateIntroduced, other.dateIntroduced)) {
            return false;
        }

        if (!StringUtils.equals(email, other.email)) {
            return false;
        }

        if (lineNumber != other.lineNumber) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "ScmDetail [id=" + id + ", lineNumber=" + lineNumber + ", email=" + email + ", dateIntroduced="
                + dateIntroduced + "]";
    }

    @Override
    public int compareTo(ScmDetail o) {
        return Integer.valueOf(lineNumber).compareTo(o.lineNumber);
    }
}
