package at.xander.fuelcanister;

import static at.xander.fuelcanister.FuelCanister.MAX_DAMAGE;
import static at.xander.fuelcanister.FuelCanister.emptyCanister;

import net.minecraft.item.ItemStack;

public class ItemFuelCanister extends GenericFuelCanister {

	public ItemFuelCanister(String unlocalizedName) {
		super(unlocalizedName);
		this.setMaxStackSize(1);
		this.setMaxDamage(MAX_DAMAGE);
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		if (itemStack.getMetadata() < MAX_DAMAGE)
			return new ItemStack(itemStack.getItem(), 1, itemStack.getItemDamage()+1);
		else{
			return new ItemStack(emptyCanister, 1);
		}
	}

	@Override
	public boolean hasContainerItem() {
		return true;
	}

}
