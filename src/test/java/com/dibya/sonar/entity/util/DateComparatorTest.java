package com.dibya.sonar.entity.util;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

public class DateComparatorTest {

	@Test
	public void testAreDatesEqualWithFirstArugumentNull() {
		Date date = new Date(1477797717190L);

		Assert.assertFalse("Should be false", DateComparator.areDatesEqual(null, date));
	}

	@Test
	public void testAreDatesEqualWithSecondArgumentNull() {
		Date date = new Date(1477797717190L);

		Assert.assertFalse("Should be false", DateComparator.areDatesEqual(date, null));
	}

	@Test
	public void testAreDatesEqualWithDifferentDates() {
		DateTime dateTime = new DateTime(1477797717190L);
		Date date = new Date(1477797717191L);

		Assert.assertFalse("Should be false", DateComparator.areDatesEqual(dateTime.toDate(), date));
	}

	@Test
	public void testAreDatesEqualWithSameDates() {
		DateTime dateTime = new DateTime(1477797717190L);
		Date date = new Date(1477797717190L);

		Assert.assertTrue("Should be true, else util class is buggy",
				DateComparator.areDatesEqual(dateTime.toDate(), date));
	}
}
