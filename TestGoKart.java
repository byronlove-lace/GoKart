class TestGoKart {

	private static final int TEST_LAP_TARGET = 100;

	public static void main(String args[]) {
		ChargeStation chargeStation = new ChargeStation("Harry's Charging Station", true);
		ProKart pro1 = new ProKart("BillHickock", 70, TEST_LAP_TARGET, chargeStation);
		ProKart pro2 = new ProKart("RodneyDangerfield", 65, TEST_LAP_TARGET, chargeStation);
		ProKart pro3 = new ProKart("NormanOsborne", 72, TEST_LAP_TARGET, chargeStation);
		ProKart pro4 = new ProKart("BigClydeBurroughs", 67, TEST_LAP_TARGET, chargeStation);
		IntermediateKart inter1 = new IntermediateKart("LuckyLucciano", 55, TEST_LAP_TARGET, chargeStation);
		IntermediateKart inter2 = new IntermediateKart("BennySegal", 60, TEST_LAP_TARGET, chargeStation);
		IntermediateKart inter3 = new IntermediateKart("AlCapone", 58, TEST_LAP_TARGET, chargeStation);
		IntermediateKart inter4 = new IntermediateKart("TonySoprano", 59, TEST_LAP_TARGET, chargeStation);
		BeginnerKart beg1 = new BeginnerKart("BettyDavis", 40, TEST_LAP_TARGET, chargeStation);
		BeginnerKart beg2 = new BeginnerKart("Cher", 30, TEST_LAP_TARGET, chargeStation);
		BeginnerKart beg3 = new BeginnerKart("SammyDavis", 39, TEST_LAP_TARGET, chargeStation);
		BeginnerKart beg4 = new BeginnerKart("FrankSinatra", 38, TEST_LAP_TARGET, chargeStation);

		Thread proT1 = new Thread(pro1);
		Thread proT2 = new Thread(pro2);
		Thread proT3 = new Thread(pro3);
		Thread proT4 = new Thread(pro4);
		Thread interT1 = new Thread(inter1);
		Thread interT2 = new Thread(inter2);
		Thread interT3 = new Thread(inter3);
		Thread interT4 = new Thread(inter4);
		Thread begT1 = new Thread(beg1);
		Thread begT2 = new Thread(beg2);
		Thread begT3 = new Thread(beg3);
		Thread begT4 = new Thread(beg4);

		proT1.start();
		proT2.start();
		proT3.start();
		proT4.start();
		interT1.start();
		interT2.start();
		interT3.start();
		interT4.start();
		begT1.start();
		begT2.start();
		begT3.start();
		begT4.start();
	}

}
