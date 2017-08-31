package at.xander.fuelcanister;

import static at.xander.fuelcanister.FuelCanister.MAX_DAMAGE;
import static at.xander.fuelcanister.ItemHandler.emptyCanister;

import net.minecraft.item.ItemStack;

public class ItemFuelCanister extends GenericFuelCanister {

	public ItemFuelCanister(String unlocalizedName) {
		super(unlocalizedName);
		this.setMaxStackSize(1);
		this.setMaxDamage(MAX_DAMAGE);
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		if (itemStack.getMetadata() < MAX_DAMAGE - 1)
			return new ItemStack(this, 1, itemStack.getItemDamage()+1);
		else{
			return new ItemStack(emptyCanister, 1);
		}
	}

	@Override
	public boolean hasContainerItem() {
		return true;
	}
	
	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return 200;
	}

}
