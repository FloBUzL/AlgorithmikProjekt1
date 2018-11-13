import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.util.Iterator;

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
	
	public void writeOut() {
		this.collissions.getMapping().forEach((key, val) -> {
			System.out.println(key + "\t" + val.toString());
		});
	}
}
