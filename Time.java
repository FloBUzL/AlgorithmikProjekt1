
public class Time {
	private int dayStart;
	private int dayEnd;
	
	private int hourStart;
	private int hourEnd;
	
	private int minuteStart;
	private int minuteEnd;
	
	private TimeStamp start;
	private TimeStamp end;
	
	public Time(String day, String time, int length) {
		switch(day) {
			case "Mo":
				this.dayStart = 1;
			break;
			case "Di":
				this.dayStart = 2;
			break;
			case "Mi":
				this.dayStart = 3;
			break;
			case "Do":
				this.dayStart = 4;
			break;
			case "Fr":
				this.dayStart = 5;
			break;
			case "Sa":
				this.dayStart = 6;
			break;
			case "So":
				this.dayStart = 7;
			break;
		}
		
		String[] timeParts = time.split(":");
		this.hourStart = Integer.parseInt(timeParts[0]);
		this.minuteStart = Integer.parseInt(timeParts[1]);
		
		int hours = length / 60;
		int minutes = length % 60;
		
		this.hourEnd = this.hourStart + hours;
		this.minuteEnd = this.minuteEnd + minutes;
		this.dayEnd = this.dayStart;
		if(this.minuteEnd >= 60) {
			this.hourEnd += 1;
			this.minuteEnd = this.minuteEnd % 60;
		}
		
		if(this.hourEnd >= 24) {
			this.hourEnd = this.hourEnd % 24;
			this.dayEnd++;
		}
		
		this.start = new TimeStamp(this.dayStart, this.hourStart, this.minuteStart);
		this.end = new TimeStamp(this.dayEnd, this.hourEnd, this.minuteEnd);
	}
	
	public boolean isOverlapping(Time otherTime) {
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
		if(this.day > otherTimeStamp.day) {
			return true;
		} else if(this.day < otherTimeStamp.day) {
			return false;
		}
		
		if(this.hour > otherTimeStamp.hour) {
			return true;
		} else if(this.hour < otherTimeStamp.hour) {
			return false;
		}
		
		if(this.minute > otherTimeStamp.hour) {
			return true;
		}
		
		return false;
	}
}
