package at.xander.fuelcanister;

import static at.xander.fuelcanister.FuelCanister.MAX_DAMAGE;
import static at.xander.fuelcanister.FuelCanister.fuelMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GenericFuelCanister extends Item implements ITextureHandler {

	public GenericFuelCanister(String unlocalizedName) {
		setUnlocalizedName(unlocalizedName);
		FuelCanister.proxy.addTextureHandler(this);
		setCreativeTab(CreativeTabs.tabMisc);
		this.setRegistryName(unlocalizedName);
		GameRegistry.registerItem(this);
	}

	@Override
	public void registerTexture() {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation(
				FuelCanister.modid + ":" + this.getUnlocalizedName().substring(5), "inventory"));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		if (worldIn.isRemote) {
			return itemStackIn;
		}
		if (playerIn.capabilities.isCreativeMode) {
			ItemStack s = new ItemStack(FuelCanister.fuelCanister, 1, 0);
			playerIn.inventory.mainInventory[playerIn.inventory.currentItem] = s.copy();
			return s;
		}
		if (itemStackIn.stackSize != 1) {
			return itemStackIn;
		}
		ItemStack modifyStack;
		if (itemStackIn.getItem() == FuelCanister.emptyCanister)
			modifyStack = new ItemStack(FuelCanister.fuelCanister, 1, MAX_DAMAGE);
		else
			modifyStack = new ItemStack(FuelCanister.fuelCanister, 1, itemStackIn.getItemDamage());
		boolean succes = false;
		for (int i = 0; i < 36; i++) {
			ItemStack stack = playerIn.inventory.mainInventory[i];
			if (stack != null) {
				FuelValue check;
				if ((check = fuelMap.getValue(stack)) != null) {
					int fuelLevel = check.getValue();
					int maxRefill = numMaxRefill(modifyStack);
					if (maxRefill == 0)
						break;
					int used = Math.min(stack.stackSize / check.getNeeded(), maxRefill / fuelLevel);
					if (used == 0)
						continue;
					succes = true;
					if (stack.getItem().hasContainerItem(stack)) {
						playerIn.inventory.addItemStackToInventory(stack.getItem().getContainerItem(stack));
					}
					if (used * check.getNeeded() == stack.stackSize) {
						playerIn.inventory.removeStackFromSlot(i);
					} else {
						stack.stackSize -= used * check.getNeeded();
					}
					modifyStack = new ItemStack(FuelCanister.fuelCanister, 1,
							modifyStack.getItemDamage() - used * fuelLevel);
				}
			}
		}
		return succes ? modifyStack : itemStackIn;
	}

	private int numMaxRefill(ItemStack stack) {
		if (stack.getItem() == FuelCanister.emptyCanister)
			return MAX_DAMAGE;
		return stack.getItemDamage();
	}
}
