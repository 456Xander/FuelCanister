package at.xander.fuelcanister;

import java.util.List;

import net.minecraft.item.Item;

public class ItemHandler {
	public static Item fuelCanister;
	public static Item emptyCanister;

	public static void init(List<Item> registryList) {
		fuelCanister = new ItemFuelCanister("fuel_canister");
		registryList.add(fuelCanister);
		emptyCanister = new ItemFuelCanisterEmpty("empty_fuel_canister");
		registryList.add(emptyCanister);
	}
}
