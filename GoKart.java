
abstract class GoKart implements Runnable {
	private String name;
	protected double speed; // kmh
	private double speedMetersPerSecond;
	private double distanceTravelled;
	private int lapCount;
	private int lapTarget;
	private double batteryPercentage;
	private double batteryReductionPerLap;
	private int metersBeforeFirstCharge;
	private int metersBeforeSubsequentCharges;
	private int lapsTillRecharged;
	protected int CHARGE_PERCENT_TO_REJOIN;
	private ChargeStation[] trackChargeStations;

	// Construtor
	GoKart(String name, int lapTarget, ChargeStation[] trackChargeStations) {
		setName(name);
		setDistanceTravelled(0);
		setLapCount(0);
		setLapTarget(lapTarget);
		setBatteryPercentage(100);
		// No need for setters / gettters (this is a constant value)
		this.CHARGE_PERCENT_TO_REJOIN = 80;
		this.trackChargeStations = trackChargeStations;
	}

	// Overrides
	@Override
	public void run() {
		int prevLapCount = -1;
		int distanceNotTravelled;

		System.out.println(name + " starts race!");
		while (lapCount <= lapTarget) {
			if ((lapCount - prevLapCount) >= 1) {
				System.out.println(name + " hit a new lap");
				batteryPercentage -= batteryReductionPerLap;

				if (batteryPercentage < 20) {
					System.out.println(name + " needs to recharge!");
					boolean foundAStation = false;
					int i;
					outerLoop: for (i = 0; i < trackChargeStations.length; ++i) {
						if (trackChargeStations[i].getIsAvailable() == true) {
							foundAStation = true;
							System.out.println(name + " charging!");
							trackChargeStations[i].setIsAvailable(false);
							distanceNotTravelled = 0;

							// Inner loop to show charging over time
							while (distanceNotTravelled < (lapsTillRecharged * 1000)) {
								distanceNotTravelled += speedMetersPerSecond;
								try {
									Thread.sleep(1);
								} catch (InterruptedException e) {
									return;
								}
							}

							System.out.println(name + " finished charging!");
							batteryPercentage = 80;
							trackChargeStations[i].setIsAvailable(true);
							break outerLoop;
						}

						if (i == (trackChargeStations.length - 1) && foundAStation == false) {
							System.out.println(name
									+ " can't charge! Reducing speed by half!");
							speedMetersPerSecond /= 2;

							if (speedMetersPerSecond > kmhToMps(5)) {
								System.out.println(
										name + " is too slow! Time to retire!");
								return;
							}
						}
					}

				}
			}

			distanceTravelled += speedMetersPerSecond;
			prevLapCount = lapCount;
			lapCount = (int) Math.floor(distanceTravelled / 1000);

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				return;
			}
		}

		System.out.println(name + " finishes race!");
	}

	// Abstracts
	public abstract void setSpeed(double speed);

	// Methods
	public double kmhToMps(double kmh) {
		return (kmh * 1000) / 3600;
	}

	public void setSpeedMetersPerSecond() {
		this.speedMetersPerSecond = kmhToMps(speed);
	}

	// Setters
	public void setName(String name) {
		this.name = name;
	}

	public void setDistanceTravelled(double distanceTravelled) {
		if (distanceTravelled >= 0.00) {
			this.distanceTravelled = distanceTravelled;
		} else {
			throw new IllegalArgumentException("distanceTravelled must be a postive value.");
		}
	}

	public void setLapCount(int lapCount) {
		if (lapCount >= 0) {
			this.lapCount = lapCount;
		} else {
			throw new IllegalArgumentException("lapCount must be a postive value.");
		}
	}

	public void setLapTarget(int lapTarget) {
		// First Lap is Lap 1; Minimum Target
		if (lapTarget >= 1) {
			this.lapTarget = lapTarget;
		} else {
			throw new IllegalArgumentException("lapTarget must be a postive value.");
		}
	}

	public void setBatteryPercentage(double batteryPercentage) {
		if (batteryPercentage >= 0 && batteryPercentage <= 100) {
			this.batteryPercentage = batteryPercentage;
		} else {
			throw new IllegalArgumentException("batteryPercentage must be a postive value.");
		}
	}

	public void setMetersBeforeFirstCharge(int metersBeforeFirstCharge) {
		if (metersBeforeFirstCharge > 0) {
			this.metersBeforeFirstCharge = metersBeforeFirstCharge;
		} else {
			throw new IllegalArgumentException("metersBeforeFirstCharge must be a postive value.");
		}
	}

	public void setSubsequentLapsMetersBeforeCharged() {
		this.metersBeforeSubsequentCharges = (metersBeforeFirstCharge / 80) * 60;
	}

	public void setLapsTillRecharged(int lapsTillRecharged) {
		this.lapsTillRecharged = lapsTillRecharged;
	}

	public void setBatteryReductionPerLap() {
		batteryReductionPerLap = 80 / (metersBeforeFirstCharge / 1000);
	}

	// Getters
	public String getName() {
		return name;
	}

	public double getSpeed() {
		return speed;
	}

	public double getDistanceTravelled() {
		return distanceTravelled;
	}

	public int getLapCount() {
		return lapCount;
	}

	public int getLapTarget() {
		return lapTarget;
	}

	public double getBatteryPercentage() {
		return batteryPercentage;
	}

	public int getMetersBeforeFirstCharge() {
		return metersBeforeFirstCharge;
	}

	public int getLapsTillRecharged() {
		return lapsTillRecharged;
	}

	public int getMetersBeforeSubsequentCharges() {
		return metersBeforeSubsequentCharges;
	}
}

class ProKart extends GoKart {

	public ProKart(String name, double speed, int lapTarget, ChargeStation[] chargeStations) {
		super(name, lapTarget, chargeStations);
		setSpeed(speed);
		setSpeedMetersPerSecond();
		setMetersBeforeFirstCharge(5000);
		setSubsequentLapsMetersBeforeCharged();
		setBatteryReductionPerLap();
		setLapsTillRecharged(3);
	}

	// Overrides
	@Override
	public void setSpeed(double speed) {
		if (speed >= 60.00 && speed <= 75.00) {
			this.speed = speed;
		} else {
			throw new IllegalArgumentException("speed of prokart must be betwee 60 and 75 (inclusive).");
		}
	}
}

class IntermediateKart extends GoKart {

	public IntermediateKart(String name, double speed, int lapTarget, ChargeStation[] chargeStations) {
		super(name, lapTarget, chargeStations);
		setSpeed(speed);
		setSpeedMetersPerSecond();
		setMetersBeforeFirstCharge(8000);
		setSubsequentLapsMetersBeforeCharged();
		setBatteryReductionPerLap();
		setLapsTillRecharged(2);
	}

	@Override
	public void setSpeed(double speed) {
		if (speed >= 50.00 && speed <= 60.00) {
			this.speed = speed;
		} else {
			throw new IllegalArgumentException(
					"speed of intermediatekart must be betwee 50 and 60 (inclusive).");
		}
	}

}

class BeginnerKart extends GoKart {

	public BeginnerKart(String name, double speed, int lapTarget, ChargeStation[] chargeStations) {
		super(name, lapTarget, chargeStations);
		setSpeed(speed);
		setSpeedMetersPerSecond();
		setMetersBeforeFirstCharge(10000);
		setSubsequentLapsMetersBeforeCharged();
		setBatteryReductionPerLap();
		setLapsTillRecharged(1);
	}

	@Override
	public void setSpeed(double speed) {
		if (speed >= 30.00 && speed <= 40.00) {
			this.speed = speed;
		} else {
			throw new IllegalArgumentException(
					"speed of beginnerkart must be betwee 30 and 40 (inclusive).");
		}
	}
}

class ChargeStation {
	private String name;
	private boolean isAvailable;

	ChargeStation(String name, boolean isAvailable) {
		setName(name);
		setIsAvailable(isAvailable);
	}

	// Setters
	public void setName(String name) {
		this.name = name;
	}

	public synchronized void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	// Getters
	public String getName() {
		return name;
	}

	public synchronized boolean getIsAvailable() {
		return isAvailable;
	}
}
