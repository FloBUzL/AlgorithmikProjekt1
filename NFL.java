import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class NFL {
	private Collission collissions;
	private String[] orderedByEndTime;
	private HashMap<String, Integer> variableIndex;
	private MovieData[] orderedByEndTimeData;
	private Set<String> excludes;
	private MovieData[] solution;
	
	public static void main(String[] args) throws IOException {
		new NFL().readIn().solve().printOut();
	}
	
	public NFL readIn() throws IOException {
		this.collissions = new Collission();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		in.lines().forEachOrdered((line) -> {
			this.collissions.addEntry(MovieData.createFromInputLine(line));
		});
				
		return this;
	}
	
	public NFL solve() {
		this.variableIndex = new HashMap<>();
		this.excludes = new HashSet<>();
		this.orderedByEndTime = this.collissions.getAllVariables().sorted((str1,str2) -> {
			TimeStamp left = this.collissions.getMapping().get(str1).getTime().getEndTime();
			TimeStamp right = this.collissions.getMapping().get(str2).getTime().getEndTime();
			
			if(left == null) {
				return 1;
			}
			
			return left.compareTo(right);
		}).toArray(size -> new String[size]);
		
		//this.generalBiggestFittingIndex = new int[this.orderedByEndTime.length];
		this.orderedByEndTimeData = new MovieData[this.orderedByEndTime.length];
		
		for(int i = 0;i < this.orderedByEndTime.length;i++) {
			this.variableIndex.put(this.orderedByEndTime[i], i);
			this.orderedByEndTimeData[i] = this.collissions.getMapping().get(this.orderedByEndTime[i]);
		}
		
		InstanceIterator iterator = new InstanceIterator();
		this.collissions.getAllTitleCollissions().forEach((str, list) -> {
			list.add(str);
			this.excludes.addAll(list);
			iterator.addChild(list);
		});
		iterator.execute();
		
		Wrapper<Double> max = new Wrapper<Double>(0.0);
		Wrapper<SchedulingInstance> maxInstance = new Wrapper<SchedulingInstance>(null);
		iterator.getSchedulingInstances().forEach((instance) -> {
			this.variableIndex.forEach((str, i) -> {
				if(this.excludes.contains(str)) {
					return;
				}
				instance.addEntry(str);
			});
			instance.setUpIndexList(this.orderedByEndTime, this.orderedByEndTimeData, this.variableIndex);
			instance.setUpLastFittingIndex();
			//instance.print();
			double currVal = instance.solve();
			if(currVal > max.getValue()) {
				max.setValue(currVal);
				if(maxInstance.getValue() != null) {
					maxInstance.getValue().cleanup();
				}
				maxInstance.setValue(instance);
			}
		});
		
		this.solution = maxInstance.getValue().getSolution();
		
		return this;
	}
	
	public void printOut() {
		for(int i = 0;i < this.solution.length;i++) {
			System.out.println(this.solution[i].toString());
			this.solution[i].toString();
		}
	}
}
