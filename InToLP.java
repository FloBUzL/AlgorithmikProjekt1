import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InToLP {
	private Collission collissions;
	
	public static void main(String[] args) {
		new InToLP().readIn().printOut();
	}
	
	public InToLP readIn() {
		this.collissions = new Collission();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		in.lines().forEachOrdered((line) -> {
			this.collissions.addEntry(MovieData.createFromInputLine(line));
		});
		
		return this;
	}
	
	/**
	 * prints out the lp
	 */
	public void printOut() {
		StringBuilder max = new StringBuilder("max: ");
		Wrapper<Boolean> following = new Wrapper<>(false);
		
		this.collissions.getMapping().forEach((variable, data) -> {
			if(following.getValue()) {
				max.append(" + ");
			}
			max.append(String.valueOf(data.getRating()) + variable);
			following.setValue(true);
		});
		
		max.append(";");
		System.out.println(max);
		
		/**
		 * the conditions for all variables
		 */
		this.collissions.getAllVariables().forEach((variable) -> {
			System.out.println(variable + " <= 1;");
			System.out.println(variable + " >= 0;");
		});
		
		/**
		 * the conditions for the title overlappings
		 * each title can be chosen not more than once
		 */
		this.collissions.getAllTitleCollissions().keySet().forEach((key) -> {
			StringBuilder out = new StringBuilder(key);
			this.collissions.getAllTitleCollissions().get(key).forEach((value) -> {
				out.append(" + " + value);
			});
			out.append(" <= 1;");
			System.out.println(out.toString());
		});
		
		/**
		 * the conditions for the time overlapping
		 * one cannot watch more than one move at time
		 */
		this.collissions.getAllTimeCollissions().keySet().forEach((key) -> {
			StringBuilder out = new StringBuilder(key);
			this.collissions.getAllTimeCollissions().get(key).forEach((value) -> {
				out.append(" + " + value);
			});
			out.append(" <= 1;");
			System.out.println(out.toString());
		});
		
		StringBuilder type = new StringBuilder("int ");
		following.setValue(false);
		this.collissions.getAllVariables().forEach((variable) -> {
			if(following.getValue()) {
				type.append(", ");
			}
			type.append(variable);
			following.setValue(true);
		});
		type.append(";");
		System.out.println(type);
	}
}