
public class TestInherit {

	public static void main(String[] args) {
		Color cl = new Color(250, 0, 0);
		Color rc = new RedColor(250, 0, 0);
		System.out.println(rc.noteMe());
	}

}

class Color {
	int red;
	int green;
	int blue;

	public Color(int r, int g, int b) {
		this.red = r;
		this.green = g;
		this.blue = b;
	}

	String noteMe() {
		return this.red + " : " + this.green + " : " + this.blue;
	}
}

class RedColor extends Color {
	public RedColor(int r, int g, int b) {
		super(r, g, b);
	}
}