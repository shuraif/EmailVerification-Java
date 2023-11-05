package com.ms.emailconfirmation.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {

	public static LocalDateTime addHoursToCurrentTime(Integer hoursToAdd) {

		LocalDateTime currentDateTime = LocalDateTime.now();

		LocalDateTime newDateTime = currentDateTime.plus(hoursToAdd, ChronoUnit.MINUTES);

		return newDateTime;
	}

}
