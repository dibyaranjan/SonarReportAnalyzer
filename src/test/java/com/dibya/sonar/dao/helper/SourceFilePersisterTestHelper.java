package com.dibya.sonar.dao.helper;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.dibya.sonar.entity.Issue;
import com.dibya.sonar.entity.ScmDetail;
import com.dibya.sonar.entity.SourceFile;

/**
 * A helper class for SourceFilePersisterImplIt. This would help the IT to
 * create recreate the seed data every time before a test is run, clear the
 * tables after each execution so that we don't leave any data breaking other
 * testcases.
 * 
 * @author Dibya Ranjan
 */
@Component
public class SourceFilePersisterTestHelper extends GenericHibernateTestHelper<SourceFile> {

    @Override
    public void executeBeforeTest() {
        Session session = sessionFactory.openSession();
        SQLQuery query = session.createSQLQuery("INSERT INTO SOURCE_FILES (SOURCE_FILE_ID, NAME) values (1, 'AuditTool:AuditTool:src/main/java/com/sita/audit/tool/controller/EntitiesController.java')");
        query.executeUpdate();
        
        
        query = session.createSQLQuery("INSERT INTO SCM_DETAILS (ID, DATEINTRODUCED, EMAIL, LINENUMBER) VALUES (1, '2016-09-26 12:12:20.0', 'dibya.panda@mindtree.com', 10)");
        query.executeUpdate();
        
        
        query = session.createSQLQuery("INSERT INTO SONAR_ISSUES (message, severity, status, line, ISSUE_KEY, project) values ('Bad message', 0, 0, 10, 'garbage', 'Bad one')");
        query.executeUpdate();
        
        query = session.createSQLQuery("INSERT INTO SOURCE_FILE_SCM_DETAILS (SOURCE_FILE_ID, SCM_DETAILS_ID) values (1, 1)");
        query.executeUpdate();
        
        query = session.createSQLQuery("insert into SOURCE_FILE_ISSUES (SOURCE_FILE_ID, ISSUE_KEY) values (1, 'garbage')");
        query.executeUpdate();
        
        session.flush();
        session.close();
    }

    @Override
    public void executeAfterTest() {
        truncateTableByName("SOURCE_FILE_SCM_DETAILS");
        truncateTableByName("SOURCE_FILE_ISSUES");
        truncateTable(ScmDetail.class);
        truncateTable(Issue.class);
        truncateTable(SourceFile.class);
    }

}