package at.xander.fuelcanister;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class CanisterFuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		if (fuel.getItem().equals(FuelCanister.fuelCanister) && fuel.getItemDamage() < FuelCanister.MAX_DAMAGE) {
			return 200;
		}
		return 0;
	}

}
