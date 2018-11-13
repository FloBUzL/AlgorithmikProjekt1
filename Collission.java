import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

public class Collission {
	private HashMap<String,MovieData> allVariables;
	private HashMap<String,String> firstOccuranceOfTitle;
	private HashMap<String,ArrayList<String>> titleCollissions;
	private int counter;
	
	public Collission() {
		this.allVariables = new HashMap<>();
		this.firstOccuranceOfTitle = new HashMap<>();
		this.titleCollissions = new HashMap<>();
		this.counter = 1;
	}
	
	public Collission addEntry(MovieData data) {
		String newLPVar = "x" + String.valueOf(this.counter++);
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
		
		return this;
	}
	
	public Stream<String> getAllVariables() {
		return this.allVariables.keySet().stream();
	}
	
	public HashMap<String, ArrayList<String>> getAllTitleCollissions() {
		return this.titleCollissions;
	}
}
