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
			this.collissions.addEntry(splitted[0], MovieData.createFromInputLine(splitted[1]));
		});
		
		return this;
	}
	
	public void printOut() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		in.lines().forEachOrdered((line) -> {
			if(line.length() == 0 || line.charAt(0) != 'x') {
				return;
			}
			
			int firstSpace = line.indexOf(' ');
			
			String key = line.substring(0, firstSpace);
			int value = Integer.parseInt(line.substring(line.length() - 1));
			
			if(value == 1) {
				System.out.println(this.collissions.getMapping().get(key).toString());
			}
		});
	}
}
