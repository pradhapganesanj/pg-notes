
public class MinuteTest {

	public static void main(String[] args) {

		for (int interv = 1; interv < 59; interv++) {
			System.out.println("INTERVAL "+interv);
			for (int i = 0; i < 60; i++) {
				if (i % interv == 0) {
					System.out.println(interv + " MET " + i);
				}
			}
		}

	}

}
