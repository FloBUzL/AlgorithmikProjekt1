import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

public class MovieData {
	private String day;
	private String time;
	private int length;
	private String title;
	private float rating;
	private Time timeObject;
	
	public MovieData(String day, String time, int length, String title, float rating) {
		this.day = day;
		this.time = time;
		this.length = length;
		this.title = title;
		this.rating = rating;
		this.timeObject = new Time(day, time, length);
	}
	
	public static MovieData createFromInputLine(String line) {
		String[] data = line.split(";");
		
		return new MovieData(data[0], data[1], Integer.parseInt(data[2]), data[3], Float.parseFloat(data[4]));
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public Time getTime() {
		return this.timeObject;
	}
	
	public float getRating() {
		return this.rating;
	}
	
	public String toString() {
		return this.day + ";" + this.time + ";" + String.valueOf(this.length) + ";" + this.title + ";" + String.valueOf(this.rating); 
	}
}


class Time {
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
	
	public TimeStamp getEndTime() {
		return this.end;
	}
	
	public TimeStamp getStartTime() {
		return this.start;
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

	public int compareTo(TimeStamp right) {
		if(this.isEqualTo(right)) {
			return 0;
		}
		
		
		if(this.isGreaterThan(right)) {
			return 1;
		}
		
		return -1;
	}
}

class Wrapper<T> {
	private T value;
	
	public Wrapper(T value) {
		this.value = value;
	}
	
	public Wrapper() {
		this.value = null;
	}
	
	public Wrapper<T> setValue(T value) {
		this.value = value;
		
		return this;
	}
	
	public T getValue() {
		return this.value;
	}
}

class Collission {
	private HashMap<String,MovieData> allVariables;
	private HashMap<String,String> firstOccuranceOfTitle;
	private HashMap<String,ArrayList<String>> titleCollissions;
	private HashMap<String,ArrayList<String>> timeCollissions;
	private int counter;
	
	public Collission() {
		this.allVariables = new HashMap<>();
		this.firstOccuranceOfTitle = new HashMap<>();
		this.titleCollissions = new HashMap<>();
		this.timeCollissions = new HashMap<>();
		this.counter = 1;
	}
	
	public Collission addEntry(String newLPVar, MovieData data) {
		this.allVariables.put(newLPVar, data);
		if(this.firstOccuranceOfTitle.containsKey(data.getTitle())) {
			String lpVar = this.firstOccuranceOfTitle.get(data.getTitle());
			if(!this.titleCollissions.containsKey(lpVar)) {
				this.titleCollissions.put(lpVar, new ArrayList<>());
			}
			this.titleCollissions.get(lpVar).add(newLPVar);
		} else {
			this.firstOccuranceOfTitle.put(data.getTitle(), newLPVar);
		}
		
		this.allVariables.forEach((key,val) -> {
			if(key.equals(newLPVar)) {
				return;
			}
			if(val.getTime().isOverlapping(data.getTime())) {
				if(!this.timeCollissions.containsKey(newLPVar)) {
					this.timeCollissions.put(newLPVar, new ArrayList<>());
				}
				this.timeCollissions.get(newLPVar).add(key);
			}
		});
		
		return this;
	}
	
	public Collission addEntry(MovieData data) {
		String newLPVar = "x" + String.valueOf(this.counter++);
		return this.addEntry(newLPVar, data);
	}
	
	public Stream<String> getAllVariables() {
		return this.allVariables.keySet().stream();
	}
	
	public HashMap<String, ArrayList<String>> getAllTitleCollissions() {
		return this.titleCollissions;
	}
	
	public HashMap<String, ArrayList<String>> getAllTimeCollissions() {
		return this.timeCollissions;
	}
	
	public HashMap<String, MovieData> getMapping() {
		return this.allVariables;
	}
}

