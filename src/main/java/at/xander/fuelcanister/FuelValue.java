package at.xander.fuelcanister;

public class FuelValue {
	private final int needed, value;

	public FuelValue(int needed, int value) {
		this.needed = needed;
		this.value = value;
	}

	public int getNeeded() {
		return needed;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value + "/" + needed;
	}
}
