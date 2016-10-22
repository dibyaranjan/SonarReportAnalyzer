package com.dibya.sonar.dao.impl.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dibya.sonar.dao.SourceFilePersister;
import com.dibya.sonar.entity.SourceFile;

@Component
@SuppressWarnings("unchecked")
public class SourceFilePersisterImpl implements SourceFilePersister {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<SourceFile> getAllSourceFiles() {
        return sessionFactory.openSession().createCriteria(SourceFile.class).list();
    }

    @Override
    public List<SourceFile> loadAllSourceFilesEagerly() {
        Session session = sessionFactory.openSession();
        List<SourceFile> sourceFiles = (List<SourceFile>) session.createCriteria(SourceFile.class).list();
        for (SourceFile sourceFile : sourceFiles) {
            Hibernate.initialize(sourceFile.getScmDetails());
            Hibernate.initialize(sourceFile.getIssues());
        }

        session.close();
        return sourceFiles;
    }

    @Override
    public void save(SourceFile sourceFile) {
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(sourceFile);
        session.flush();
        session.close();
    }

    @Override
    public List<SourceFile> loadSessionLessSourceFilesEagerly() {
        Session session = sessionFactory.openSession();
        List<SourceFile> sourceFiles = (List<SourceFile>) session.createCriteria(SourceFile.class).list();
        for (SourceFile sourceFile : sourceFiles) {
            Hibernate.initialize(sourceFile.getScmDetails());
            Hibernate.initialize(sourceFile.getIssues());
            session.evict(sourceFile);
            sourceFile.setIssues(new ArrayList<>(sourceFile.getIssues()));
            sourceFile.setScmDetails(new ArrayList<>(sourceFile.getScmDetails()));
        }

        session.close();

        return sourceFiles;
    }
}
