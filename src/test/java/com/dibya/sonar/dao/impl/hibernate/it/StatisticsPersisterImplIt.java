package com.dibya.sonar.dao.impl.hibernate.it;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dibya.sonar.configuration.PersisterItConfiguration;
import com.dibya.sonar.dao.StatisticsPersister;
import com.dibya.sonar.dao.impl.hibernate.SourceFilePersisterImpl;
import com.dibya.sonar.dao.impl.hibernate.StatisticsPersisterImpl;
import com.dibya.sonar.entity.vo.MostViolatedMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { StatisticsPersisterImpl.class, PersisterItConfiguration.class,
        SourceFilePersisterImpl.class })
public class StatisticsPersisterImplIt {
    @Autowired
    private StatisticsPersister persister;

    @Test
    public void testGetTopViolatedRules() {
        List<MostViolatedMessage> topViolatedRules = persister.getTopViolatedRules();

        Assert.assertEquals("1 violation should be presnt", 1, topViolatedRules.size());
    }
}
