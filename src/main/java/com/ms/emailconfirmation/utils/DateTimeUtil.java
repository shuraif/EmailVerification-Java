package com.ms.emailconfirmation.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {

    public static LocalDateTime addHoursToCurrentTime(Integer hoursToAdd) {
       
        LocalDateTime currentDateTime = LocalDateTime.now();

        LocalDateTime newDateTime = currentDateTime.plus(hoursToAdd, ChronoUnit.MINUTES);

        System.out.println("Current Date and Time: " + currentDateTime);
        System.out.println("Date and Time Plus " + hoursToAdd + " Hours: " + newDateTime);
        
        return newDateTime;
    }
    
}
