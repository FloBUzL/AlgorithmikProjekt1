public class MovieData {
	private String day;
	private String time;
	private int length;
	private String title;
	private float rating;
	
	public MovieData(String day, String time, int length, String title, float rating) {
		this.day = day;
		this.time = time;
		this.length = length;
		this.title = title;
		this.rating = rating;
	}
	
	public static MovieData createFromInputLine(String line) {
		String[] data = line.split(";");
		
		return new MovieData(data[0], data[1], Integer.parseInt(data[2]), data[3], Float.parseFloat(data[4]));
	}
	
	public String getTitle() {
		return this.title;
	}
}