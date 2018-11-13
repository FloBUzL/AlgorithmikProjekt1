import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InToLP {
	private Collission collissions;
	
	public static void main(String[] args) {
		InToLP LPMaker = new InToLP();
		LPMaker.readIn().printOut();
	}
	
	public InToLP readIn() {
		this.collissions = new Collission();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		in.lines().forEachOrdered((line) -> {
			this.collissions.addEntry(MovieData.createFromInputLine(line));
		});
		
		return this;
	}
	
	public void printOut() {
		this.collissions.getAllVariables().forEach((variable) -> {
			System.out.println(variable + " <= 1");
		});
		
		System.out.println();
		
		this.collissions.getAllTitleCollissions().keySet().forEach((key) -> {
			StringBuilder out = new StringBuilder(key);
			this.collissions.getAllTitleCollissions().get(key).forEach((value) -> {
				out.append(" + " + value);
			});
			out.append(" <= 1");
			System.out.println(out.toString());
		});
		
		System.out.println();
		
		this.collissions.getAllTimeCollissions().keySet().forEach((key) -> {
			StringBuilder out = new StringBuilder(key);
			this.collissions.getAllTimeCollissions().get(key).forEach((value) -> {
				out.append(" + " + value);
			});
			out.append(" <= 1");
			System.out.println(out.toString());
		});
	}
}