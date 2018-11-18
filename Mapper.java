import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Mapper {
	private Collission collissions;

	public static void main(String[] args) {
		Mapper mapper = new Mapper();
		mapper.readIn().writeOut();
	}

	public Mapper readIn() {
		this.collissions = new Collission();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		in.lines().forEachOrdered((line) -> {
			this.collissions.addEntry(MovieData.createFromInputLine(line));
		});
		
		return this;
	}
	
	/**
	 * prints out the variable and the data content
	 */
	public void writeOut() {
		this.collissions.getMapping().forEach((key, val) -> {
			System.out.println(key + "\t" + val.toString());
		});
	}
}
