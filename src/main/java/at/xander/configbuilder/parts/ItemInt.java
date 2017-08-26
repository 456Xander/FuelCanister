package at.xander.configbuilder.parts;

import at.xander.fuelcanister.FuelValue;

public class ItemInt {
	private final String item;
	private final int meta;
	private final int value;
	private final int needed;
	private static final String intChar = "-";

	public ItemInt(String item, int integer) {
		this.value = integer;
		this.item = item;
		meta = 0;
		needed = 1;
	}
	public ItemInt(String item, int meta, int value, int needed) {
		this.item = item;
		this.meta = meta;
		this.value = value;
		this.needed = needed;
	}

	public int getMeta() {
		return meta;
	}

	public ItemInt(String item, int integer, int meta) {
		this.value = integer;
		this.item = item;
		this.meta = meta;
		needed = 1;
	}

	/**
	 * E.g minecraft:coal
	 * 
	 * @return
	 */
	public String getItem() {
		return item;
	}

	/**
	 * the value
	 * 
	 * @return
	 */
	public FuelValue getValue() {
		return new FuelValue(needed, value);
	}

	public static ItemInt getFromString(String s) {
		String[] data = s.split(intChar);
		String item = data[0].trim();
		int meta = 0;
		String[] a;
		if ((a = item.split(":")).length == 3) {
			item = a[0] + ":" + a[1];
			meta = Integer.parseInt(a[2]);
		}
		int needed = 1;
		if(data[1].contains("/")){
			String[] tmp = data[1].split("/");
			data[1] = tmp[0];
			needed = Integer.parseInt(tmp[1]);
		}
		int value = Integer.parseInt(data[1].trim());
		return new ItemInt(item, meta, value, needed);
	}

	@Override
	public String toString() {
		return item + (meta != 0 ? ":" + meta : "") + intChar + value + (needed != 1 ? "/"+needed:"");
	}
	
	public ItemInt setItemsNeeded(int needed){
		return new ItemInt(item, meta, value, needed);
	}
}
