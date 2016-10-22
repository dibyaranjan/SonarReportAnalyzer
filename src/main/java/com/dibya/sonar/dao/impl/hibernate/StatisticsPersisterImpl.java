package com.dibya.sonar.dao.impl.hibernate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dibya.sonar.dao.StatisticsPersister;
import com.dibya.sonar.entity.vo.MostViolatedMessage;
import com.dibya.sonar.entity.vo.MostViolatedResource;

@Component
@SuppressWarnings("unchecked")
public class StatisticsPersisterImpl implements StatisticsPersister {
    private static final String GET_MESSAGES_AND_COUNT = "SELECT COUNT(*) AS COUNT, MESSAGE FROM SONAR_ISSUES GROUP BY MESSAGE ORDER BY COUNT DESC";
    private static final String GET_TOP_VIOLATED_CLASESS = "SELECT SF.NAME, COUNT(*) AS COUNT FROM SOURCE_FILES SF, SOURCE_FILE_ISSUES SFI, SONAR_ISSUES  SI WHERE SF.SOURCE_FILE_ID = SFI.SOURCE_FILE_ID AND SI.ISSUE_KEY = SFI.ISSUE_KEY GROUP BY SF.NAME ORDER BY COUNT DESC";

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<MostViolatedMessage> getTopViolatedRules() {
        Session session = sessionFactory.openSession();
        Query query = session.createSQLQuery(GET_MESSAGES_AND_COUNT).setMaxResults(5);
        List<Object[]> list = query.list();

        List<MostViolatedMessage> messages = new ArrayList<>();
        for (Object[] objects : list) {
            MostViolatedMessage message = new MostViolatedMessage();
            BigInteger count = (BigInteger) objects[0];
            message.setCount(Long.valueOf(count.longValue()));
            message.setMessage((String) objects[1]);

            messages.add(message);
        }

        session.close();

        return messages;
    }

    @Override
    public List<MostViolatedResource> getTopViolatedResourcesWithLimit(int limit) {
        Session session = sessionFactory.openSession();
        Query query = session.createSQLQuery(GET_TOP_VIOLATED_CLASESS).setMaxResults(limit);
        List<Object[]> list = query.list();

        return createViolatedResources(list);
    }

    @Override
    public List<MostViolatedResource> getTopViolatedResourcesWithoutLimit() {
        Session session = sessionFactory.openSession();
        Query query = session.createSQLQuery(GET_TOP_VIOLATED_CLASESS);
        List<Object[]> list = query.list();

        return createViolatedResources(list);
    }

    private List<MostViolatedResource> createViolatedResources(List<Object[]> list) {
        List<MostViolatedResource> violatedResources = new ArrayList<>();
        for (Object[] objects : list) {
            MostViolatedResource message = new MostViolatedResource();
            BigInteger count = (BigInteger) objects[1];
            message.setCount(Integer.valueOf(count.intValue()));
            message.setResourcePath((String) objects[0]);

            violatedResources.add(message);
        }
        return violatedResources;
    }

    @Override
    public void getViolationsByAllDevelopers() {
        Session session = sessionFactory.openSession();
        
        session.close();
    }
}
