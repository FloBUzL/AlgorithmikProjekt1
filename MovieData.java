import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
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
		minuteEnd = minuteStart + minutes;
		dayEnd = dayStart;
		if(minuteEnd >= 60) {
			hourEnd += minuteEnd / 60;
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
	
	public String toString() {
		return "Start: " + this.start.toString() + ", Ende: " + this.end.toString();
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
	
	public String toString() {
		return "Tag: " + this.day + ", Stunde: " + this.hour + ", Minute: " + this.minute;
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
			//System.out.println(this.toString() + ">" + otherTimeStamp.toString());
			return true;
		} else if(this.day < otherTimeStamp.day) {
			return false;
		}
		
		if(this.hour > otherTimeStamp.hour) {
			return true;
		} else if(this.hour < otherTimeStamp.hour) {
			return false;
		}
		
		if(this.minute > otherTimeStamp.minute) {
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

class SchedulingInstance {
	private ArrayList<String> includes;
	private int[] entries;
	private MovieData[] datas;
	private int[] lastFittingIndex;
	private float[] opt;
	private ArrayList<MovieData> solution;
	
	public SchedulingInstance() {
		this.includes = new ArrayList<>();
		this.entries = null;
		this.lastFittingIndex = null;
		this.opt = null;
		this.solution = null;
	}
	
	private SchedulingInstance(ArrayList<String> includes) {
		this.includes = new ArrayList<>(includes);
		this.entries = null;
		this.lastFittingIndex = null;
		this.opt = null;
		this.solution = null;
	}
	
	public void cleanup() {
		this.includes = null;
		this.entries = null;
		this.datas = null;
		this.lastFittingIndex = null;
		this.opt = null;
		this.solution = null;
	}
	
	public void addEntry(String entry) {
		this.includes.add(entry);
	}
	
	public SchedulingInstance clone() {
		ArrayList<String> includes = new ArrayList<>();
		this.includes.forEach((str) -> {
			includes.add(str);
		});
		return new SchedulingInstance(includes);
	}
	
	public void print() {
		StringBuilder out = new StringBuilder();
		if(this.entries == null) {
			this.includes.forEach((str) -> {
				out.append(str + ", ");
			});
		} else {
			for(int i = 0;i < this.entries.length;i++) {
				out.append(String.valueOf(this.entries[i]) + ", ");
			}
		}
		if(this.lastFittingIndex != null) {
			out.append(" -> ");
			for(int i = 0;i < this.lastFittingIndex.length;i++) {
				out.append(String.valueOf(this.lastFittingIndex[i]) + ", ");
			}
		}
		
		System.out.println(out);
	}

	public void setUpIndexList() {
		this.entries = new int[this.includes.size()];
		this.datas = new MovieData[this.includes.size()];
		int i = 0;
		for(int j = 0;j < DataStorage.orderedByEndTime.length;j++) {
			if(this.includes.contains(DataStorage.orderedByEndTime[j])) {
				this.entries[i] = DataStorage.variableIndex.get(DataStorage.orderedByEndTime[j]);
				this.datas[i++] = DataStorage.orderedByEndTimeData[j];
			}
		}
	}
	
	public void setUpLastFittingIndex() {
		this.lastFittingIndex = new int[this.entries.length];
		this.opt = new float[this.entries.length];
		
		for(int i = 0;i < this.entries.length;i++) {
			int currVal = -1;
			for(int j = 0;j < i;j++) {
				if(this.datas[i].getTime().getStartTime().isGreaterThanOrEquals(this.datas[j].getTime().getEndTime())) {
					currVal = j;
				}
			}
			if(currVal < -1) {
				System.out.println("******");
				System.out.println(this.datas[i].toString());
				System.out.println(this.datas[currVal].toString());
				System.out.println("******");
			}
			this.lastFittingIndex[i] = currVal;
		}
	}
	
	private float getOpt(int index) {
		return index == -1 ? 0 : this.opt[index];
	}
	
	public float solve() {
		for(int i = 0;i < this.entries.length;i++) {
			this.opt[i] = (float) Math.max(this.datas[i].getRating() + this.getOpt(this.lastFittingIndex[i]), this.getOpt(i-1));
		}
		
		return this.getOpt(this.entries.length-1);
	}
	
	public MovieData[] getSolution() {
		this.solution = new ArrayList<>();
		this.calcSolution(this.entries.length-1);
		
		MovieData[] ret = new MovieData[this.solution.size()];
		return this.solution.toArray(ret);
	}
	
	private void calcSolution(int index) {
		if(index == -1) {
			return;
		} else if(this.datas[index].getRating() + this.getOpt(this.lastFittingIndex[index]) > this.getOpt(index-1)) {
			this.solution.add(this.datas[index]);
			this.calcSolution(this.lastFittingIndex[index]);
		} else {
			this.calcSolution(index-1);
		}
	}
}

class InstanceIterator {
	private ArrayList<String> list;
	private ArrayList<SchedulingInstance> instanceList;
	private InstanceIterator child;
	
	private InstanceIterator(ArrayList<String> list, ArrayList<SchedulingInstance> instanceList) {
		this.list = list;
		this.instanceList = instanceList;
		this.child = null;
	}
	
	public void addChild(ArrayList<String> list) {
		if(this.child == null) {
			this.child = new InstanceIterator(list, this.instanceList);
		} else {
			this.child.addChild(list);
		}
	}
	
	public InstanceIterator() {
		this.list = null;
		this.instanceList = new ArrayList<>();
		this.child = null;
	}
	
	private void execute(SchedulingInstance instance) {
		if(this.list == null) {
			if(this.child == null) {
				this.executeInstance(instance);
				return;
			}
			this.child.execute();
			return;
		}
		
		this.list.forEach((str) -> {
			SchedulingInstance newInstance = instance.clone();
			newInstance.addEntry(str);
			if(this.child == null) {
				this.executeInstance(newInstance);
			} else {
				this.child.execute(newInstance);
			}
		});
	}
	
	public void execute() {
		this.execute(new SchedulingInstance());
	}
	
	private void executeInstance(SchedulingInstance instance) {
		DataStorage.variableIndex.forEach((str, i) -> {
			if(DataStorage.excludes.contains(str)) {
				return;
			}
			instance.addEntry(str);
		});
		
		instance.setUpIndexList();
		instance.setUpLastFittingIndex();
		
		float val = instance.solve();
		if(val > DataStorage.maxValue) {
			DataStorage.maxValue = val;
			DataStorage.maxInstance = instance;
		}
	}
	
	public MovieData[] getOptimalSolution() {
		return DataStorage.maxInstance.getSolution();
	}
	
	public ArrayList<SchedulingInstance> getSchedulingInstances() {
		return this.instanceList;
	}
}

class DataStorage {
	public static String[] orderedByEndTime;
	public static HashMap<String, Integer> variableIndex;
	public static MovieData[] orderedByEndTimeData;
	public static Set<String> excludes;
	public static SchedulingInstance maxInstance;
	public static float maxValue = 0;
}