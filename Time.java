
public class Time {
	private int dayStart;
	private int dayEnd;
	
	private int hourStart;
	private int hourEnd;
	
	private int minuteStart;
	private int minuteEnd;
	
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
	}
	
	public boolean isOverlapping(Time otherTime) {
		if(this.dayStart == this.dayEnd && otherTime.dayStart == otherTime.dayEnd) {
			if(this.dayStart != otherTime.dayStart) {
				return false;
			}
			if(this.hourStart > otherTime.hourEnd) {
				return false;
			}
			if(otherTime.hourStart > this.hourEnd) {
				return false;
			}
			if(this.hourStart == otherTime.hourEnd) {
				if(this.minuteStart >= otherTime.minuteEnd) {
					return false;
				} else {
					return true;
				}
			}
			if(this.hourEnd == otherTime.hourStart) {
				if(otherTime.minuteStart >= this.minuteEnd) {
					return false;
				} else {
					return true;
				}
			}
			if(this.hourStart >= otherTime.hourStart && this.hourStart <= otherTime.hourEnd) {
				return true;
			}
			if(otherTime.hourStart >= this.hourStart && otherTime.hourStart <= otherTime.hourEnd) {
				return true;
			}
		} else {
			System.out.println("Not implemented yet!");
		}
		
		return false;
	}
}
