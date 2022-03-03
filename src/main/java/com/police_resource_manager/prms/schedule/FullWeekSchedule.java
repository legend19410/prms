package com.police_resource_manager.prms.schedule;

import java.util.List;

class FullWeekSchedule{
	public List<OfficerWeekSchedule> schedule;
}

class OfficerWeekSchedule {
	
	public Integer regNo;
	public String rank;
	public String firstName;
	public String lastName;
	public List<ScheduleItem> weekDays;
	
	@Override
	public String toString() {
		return "WeekSchedule [regNo=" + regNo + ", firstName=" + firstName + ", rank=" + rank +", lastName=" + lastName + ", weekDays="
				+ weekDays + "]";
	}
}

class ScheduleItem{
	
	public Integer regNo;
	public String date;
	public String duty;
	
	@Override
	public String toString() {
		return "ScheduleItem [regNo=" + regNo + ", date=" + date + ", day=" + duty + "]";
	}
}
