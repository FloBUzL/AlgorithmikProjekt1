public class Wrapper<T> {
	private T value;
	
	public Wrapper(T value) {
		this.value = value;
	}
	
	public Wrapper() {
		this.value = null;
	}
	
	public Wrapper<T> setValue(T value) {
		this.value = value;
		
		return this;
	}
	
	public T getValue() {
		return this.value;
	}
}
