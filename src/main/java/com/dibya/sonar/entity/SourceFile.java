package com.dibya.sonar.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "SOURCE_FILES", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class SourceFile implements Comparable<SourceFile> {
    @Id
    @Column(name = "SOURCE_FILE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "SOURCE_FILE_ISSUES", joinColumns = @JoinColumn(name = "SOURCE_FILE_ID"), inverseJoinColumns = @JoinColumn(name = "ISSUE_KEY"))
    private List<Issue> issues = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "SOURCE_FILE_SCM_DETAILS", joinColumns = @JoinColumn(name = "SOURCE_FILE_ID"), inverseJoinColumns = @JoinColumn(name = "SCM_DETAILS_ID"))
    private List<ScmDetail> scmDetails = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public List<ScmDetail> getScmDetails() {
        return scmDetails;
    }

    public void setScmDetails(List<ScmDetail> scmDetails) {
        this.scmDetails = scmDetails;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((issues == null) ? 0 : issues.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((scmDetails == null) ? 0 : scmDetails.hashCode());
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
        SourceFile other = (SourceFile) obj;
        
        if (issues == null) {
            if (other.issues != null) {
                return false;
            }
        } else if (!CollectionUtils.isEqualCollection(issues, other.issues)) {
            return false;
        }
        
        if (!StringUtils.equals(name, other.name)) {
            return false;
        }

        if (scmDetails == null) {
            if (other.scmDetails != null) {
                return false;
            }
        } else if (!scmDetails.equals(other.scmDetails)) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "SourceFile [id=" + id + ", name=" + name + ", issues=" + issues + ", scmDetails=" + scmDetails + "]";
    }

    @Override
    public int compareTo(SourceFile o) {
        return Integer.valueOf(id).compareTo(Integer.valueOf(o.id));
    }
}
