import java.security.SecureRandom;

class TestGoKartPlus {
	private static final int TEST_LAP_TARGET = 200;
	private static final int KART_COUNT = 60;
	private static final int STATION_COUNT = 20;

	private static ChargeStation[] chargeStations = new ChargeStation[STATION_COUNT];
	private static GoKart[] goKarts = new GoKart[KART_COUNT];
	private static Thread[] kartThreads = new Thread[KART_COUNT];

	private static SecureRandom random = new SecureRandom();

	private static int PRO_SPEED_MIN = 60;
	private static int PRO_SPEED_MAX = 75;
	private static int INTER_SPEED_MIN = 50;
	private static int INTER_SPEED_MAX = 60;
	private static int BEGINNER_SPEED_MIN = 30;
	private static int BEGINNER_SPEED_MAX = 40;

	public static void main(String args[]) {

		for (int i = 0; i < STATION_COUNT; ++i) {
			chargeStations[i] = new ChargeStation("Charge Station " + (i + 1), true);
		}

		int count = 0;

		for (int i = 0; i < (KART_COUNT / 3); ++i) {
			int randomSpeed = random.nextInt(PRO_SPEED_MAX - PRO_SPEED_MIN + 1) + PRO_SPEED_MIN;
			goKarts[count] = new ProKart("ProKart " + (i + 1), randomSpeed, TEST_LAP_TARGET,
					chargeStations);
			count++;
		}

		for (int i = 0; i < (KART_COUNT / 3); ++i) {
			int randomSpeed = random.nextInt(INTER_SPEED_MAX - INTER_SPEED_MIN + 1) + INTER_SPEED_MIN;
			goKarts[count] = new IntermediateKart("InterKart " + (i + 1), randomSpeed, TEST_LAP_TARGET,
					chargeStations);
			count++;
		}

		for (int i = 0; i < (KART_COUNT / 3); ++i) {
			int randomSpeed = random.nextInt(BEGINNER_SPEED_MAX - BEGINNER_SPEED_MIN + 1)
					+ BEGINNER_SPEED_MIN;
			goKarts[count] = new BeginnerKart("BeginKart " + (i + 1), randomSpeed, TEST_LAP_TARGET,
					chargeStations);
			count++;
		}

		for (int i = 0; i < KART_COUNT; ++i) {
			kartThreads[i] = new Thread(goKarts[i]);
		}

		for (int i = 0; i < KART_COUNT; ++i) {
			kartThreads[i].start();
		}
	}
}
