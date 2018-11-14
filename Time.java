
public class Time {
	private TimeStamp start;
	private TimeStamp end;
	
	public Time(String day, String time, int length) {
		int dayStart = 0;
		int dayEnd = 0;
		int hourStart = 0;
		int hourEnd = 0;
		int minuteStart = 0;
		int minuteEnd = 0;
		
		switch(day) {
			case "Mo":
				dayStart = 1;
			break;
			case "Di":
				dayStart = 2;
			break;
			case "Mi":
				dayStart = 3;
			break;
			case "Do":
				dayStart = 4;
			break;
			case "Fr":
				dayStart = 5;
			break;
			case "Sa":
				dayStart = 6;
			break;
			case "So":
				dayStart = 7;
			break;
		}
		
		String[] timeParts = time.split(":");
		hourStart = Integer.parseInt(timeParts[0]);
		minuteStart = Integer.parseInt(timeParts[1]);
		
		int hours = length / 60;
		int minutes = length % 60;
		
		hourEnd = hourStart + hours;
		minuteEnd = minuteEnd + minutes;
		dayEnd = dayStart;
		if(minuteEnd >= 60) {
			hourEnd += 1;
			minuteEnd = minuteEnd % 60;
		}
		
		if(hourEnd >= 24) {
			hourEnd = hourEnd % 24;
			dayEnd++;
		}
		
		this.start = new TimeStamp(dayStart, hourStart, minuteStart);
		this.end = new TimeStamp(dayEnd, hourEnd, minuteEnd);
	}
	
	public boolean isOverlapping(Time otherTime) {
		if(this.start.isEqualTo(otherTime.start)) {
			return true;
		}
		
		if(this.end.isEqualTo(otherTime.end)) {
			return true;
		}
		
		if(this.start.isGreaterThan(otherTime.start) && !this.start.isGreaterThan(otherTime.end)) {
			return true;
		}
		
		if(this.end.isGreaterThan(otherTime.start) && !this.end.isGreaterThan(otherTime.end)) {
			return true;
		}
		
		if(otherTime.start.isGreaterThan(this.start) && !otherTime.start.isGreaterThan(this.end)) {
			return true;
		}
		
		if(otherTime.end.isGreaterThan(this.start) && !otherTime.end.isGreaterThan(this.end)) {
			return true;
		}
		
		return false;
	}
}

class TimeStamp {
	private int day;
	private int hour;
	private int minute;
	
	public TimeStamp(int day, int hour, int minute) {
		this.day = day;
		this.hour = hour;
		this.minute = minute;
	}
	
	public boolean isGreaterThan(TimeStamp otherTimeStamp) {
		return this.isGreaterThan(otherTimeStamp, false);
	}
	
	public boolean isGreaterThanOrEquals(TimeStamp otherTimeStamp) {
		return this.isGreaterThan(otherTimeStamp, true);
	}
	
	public boolean isEqualTo(TimeStamp otherTimeStamp) {
		return (this.day == otherTimeStamp.day && this.hour == otherTimeStamp.hour && this.minute == otherTimeStamp.hour);
	}
	
	public boolean isGreaterThan(TimeStamp otherTimeStamp, boolean isGreaterOrEqual) {
		if(this.day > otherTimeStamp.day) {
			return true;
		} else if(this.day < otherTimeStamp.day) {
			return false;
		}
		
		if(this.hour > otherTimeStamp.hour) {
			return true;
		} else if(this.hour < otherTimeStamp.minute) {
			return false;
		}
		
		if(this.minute > otherTimeStamp.hour) {
			return true;
		} else if(this.minute < otherTimeStamp.minute) {
			return false;
		}
		
		return isGreaterOrEqual;
	}
}
