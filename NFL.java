import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NFL {
	private Collission collissions;
	private String[] orderedByEndTime;
	private MovieData[] orderedByEndTimeData;
	private int[] biggestFittingIndex;
	private int[][] instances;
	
	public static void main(String[] args) throws IOException {
		new NFL().readIn().prepare().solve().printOut();
	}
	
	public NFL readIn() throws IOException {
		this.collissions = new Collission();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		in.lines().forEachOrdered((line) -> {
			this.collissions.addEntry(MovieData.createFromInputLine(line));
		});
				
		return this;
	}
	
	public NFL prepare() {
		this.orderedByEndTime = this.collissions.getAllVariables().sorted((str1,str2) -> {
			TimeStamp left = this.collissions.getMapping().get(str1).getTime().getEndTime();
			TimeStamp right = this.collissions.getMapping().get(str2).getTime().getEndTime();
			
			if(left == null) {
				return 1;
			}
			
			return left.compareTo(right);
		}).toArray(size -> new String[size]);
		
		this.biggestFittingIndex = new int[this.orderedByEndTime.length];
		this.orderedByEndTimeData = new MovieData[this.orderedByEndTime.length];
		
		for(int i = 0;i < this.orderedByEndTime.length;i++) {
			this.orderedByEndTimeData[i] = this.collissions.getMapping().get(this.orderedByEndTime[i]);
			int currVal = -1;
			for(int j = 0;j < i;j++) {
				if(this.orderedByEndTimeData[i].getTime().getStartTime().isGreaterThanOrEquals(this.orderedByEndTimeData[j].getTime().getEndTime())) {
					currVal = j;
				}
			}
			this.biggestFittingIndex[i] = currVal;
		}
		
		return this;
	}
	
	public NFL solve() {
		
		
		return this;
	}
	
	public void printOut() {
		
	}
}
