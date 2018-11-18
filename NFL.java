import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class NFL {
	private Collission collissions;
	
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
		DataStorage.variableIndex = new HashMap<>();
		DataStorage.excludes = new HashSet<>();
		DataStorage.orderedByEndTime = this.collissions.getAllVariables().sorted((str1,str2) -> {
			TimeStamp left = this.collissions.getMapping().get(str1).getTime().getEndTime();
			TimeStamp right = this.collissions.getMapping().get(str2).getTime().getEndTime();
			
			if(left == null) {
				return 1;
			}
			
			return left.compareTo(right);
		}).toArray(size -> new String[size]);
		
		//this.generalBiggestFittingIndex = new int[this.orderedByEndTime.length];
		DataStorage.orderedByEndTimeData = new MovieData[DataStorage.orderedByEndTime.length];
		
		for(int i = 0;i < DataStorage.orderedByEndTime.length;i++) {
			DataStorage.variableIndex.put(DataStorage.orderedByEndTime[i], i);
			DataStorage.orderedByEndTimeData[i] = this.collissions.getMapping().get(DataStorage.orderedByEndTime[i]);
		}
		
		InstanceIterator iterator = new InstanceIterator();
		this.collissions.getAllTitleCollissions().forEach((str, list) -> {
			list.add(str);
			DataStorage.excludes.addAll(list);
			iterator.addChild(list);
		});
		iterator.execute();this.solution = iterator.getOptimalSolution();
		
		return this;
	}
	
	public void printOut() {
		for(int i = 0;i < this.solution.length;i++) {
			System.out.println(this.solution[i].toString());
			this.solution[i].toString();
		}
	}
}
