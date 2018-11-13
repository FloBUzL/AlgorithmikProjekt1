import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SolveToOut {
	private Collission collissions;
	
	public static void main(String[] args) throws IOException {
		SolveToOut Printer = new SolveToOut();
		Printer.readIn().printOut();
	}
	
	public SolveToOut readIn() throws IOException {
		this.collissions = new Collission();
		
		Files.lines(Paths.get("./mapping.txt")).forEach((line) -> {
			String[] splitted = line.split("\t");
			System.out.println(splitted[1]);
			this.collissions.addEntry(splitted[0], MovieData.createFromInputLine(splitted[1]));
		});
		
		return this;
	}
	
	public void printOut() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		in.lines().forEachOrdered((line) -> {
			String key = line.substring(0, 2);
			String value = line.substring(-1);
			System.out.println(key);
			System.out.println(value);
		});
	}
}
