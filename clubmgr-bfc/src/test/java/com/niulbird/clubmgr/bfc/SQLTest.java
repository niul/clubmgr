package com.niulbird.clubmgr.bfc;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;

public class SQLTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date d1 = Date.valueOf("2022-08-27");
		Time t1 = Time.valueOf(LocalTime.parse("14:00:00"));

		System.out.println("Date Long: " + d1.getTime());
		System.out.println("Date Time: " + d1);
		System.out.println("Time Long: " + t1.getTime());
		System.out.println("Time Time: " + t1);

		Timestamp ts1 = new Timestamp(d1.getTime() + t1.getTime());

		System.out.println("Timestamp Long: " + ts1.getTime());
		System.out.println("Timestamp Time: " + ts1);

		long now = System.currentTimeMillis();
        Time sqlTime = new Time(now);
        System.out.println("currentTimeMillis: " + now);
        System.out.println("SqlTime          : " + sqlTime);
        System.out.println("SqlTime.getTime(): " + sqlTime.getTime());
	}

}
