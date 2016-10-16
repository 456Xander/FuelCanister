package at.xander.fuelcanister;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFuelCanisterEmpty extends GenericFuelCanister{
	public ItemFuelCanisterEmpty(String unlocalizedName) {
		super(unlocalizedName);
		setMaxStackSize(16);
	}
}
