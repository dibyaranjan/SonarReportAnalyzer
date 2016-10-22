package com.dibya.sonar.dao.helper;

import javax.persistence.Table;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A generic helper for truncating any table. This can be reused by multiple DAO
 * to clear the tables so that testcases don't leave any side effects.
 * 
 * The concrete implementation of this type must define what should be done
 * before and after each test execution.
 * 
 * @author Dibya Ranjan
 */
public abstract class GenericHibernateTestHelper<T> {

    @Autowired
    protected SessionFactory sessionFactory;

    /**
     * Expects a class which is annotated with @Table, truncates the respective
     * table. Use with caution, no validation are done. May throw RTEs
     * 
     * @param clazz
     *            The annotated class
     */
    public void truncateTable(Class<?> clazz) {
        String tableName = getTableName(clazz);
        truncateTableByName(tableName);
    }

    public void truncateTableByName(String tableName) {
        StatelessSession statelessSession = sessionFactory.openStatelessSession();
        SQLQuery createSQLQuery = statelessSession.createSQLQuery("DELETE FROM " + tableName);
        createSQLQuery.executeUpdate();
    }

    private String getTableName(Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        return table.name();
    }

    /**
     * Gets the object for implemented class by using primary key.
     * 
     * @param clazz
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public T getObjectByPrimaryKey(Class<?> clazz, int key) {
        Session session = sessionFactory.openSession();
        return (T) session.get(clazz, key);
    }

    /**
     * Gets the object for implemented class by using primary key.
     * 
     * @param clazz
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public T getObjectByPrimaryKey(Class<?> clazz, String key) {
        Session session = sessionFactory.openSession();
        return (T) session.get(clazz, key);
    }

    public abstract void executeBeforeTest();

    public abstract void executeAfterTest();
}
