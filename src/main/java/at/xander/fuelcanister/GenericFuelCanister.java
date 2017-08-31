package at.xander.fuelcanister;

import static at.xander.fuelcanister.FuelCanister.MAX_DAMAGE;
import static at.xander.fuelcanister.FuelCanister.fuelMap;
import static at.xander.fuelcanister.ItemHandler.emptyCanister;
import static at.xander.fuelcanister.ItemHandler.fuelCanister;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class GenericFuelCanister extends Item implements ITextureHandler {

	public GenericFuelCanister(String unlocalizedName) {
		setUnlocalizedName(unlocalizedName);
		FuelCanister.proxy.addTextureHandler(this);
		setCreativeTab(CreativeTabs.MISC);
		this.setRegistryName(unlocalizedName);
	}

	@Override
	public void registerTexture() {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation(
				FuelCanister.modid + ":" + this.getUnlocalizedName().substring(5), "inventory"));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemStackIn = playerIn.getHeldItem(handIn);
		if (worldIn.isRemote) {
			return ActionResult.<ItemStack>newResult(EnumActionResult.PASS, itemStackIn);
		}
		if (playerIn.capabilities.isCreativeMode) {
			ItemStack s = new ItemStack(fuelCanister, 1, 0);
			playerIn.inventory.mainInventory.set(playerIn.inventory.currentItem, s.copy());
			return ActionResult.<ItemStack>newResult(EnumActionResult.SUCCESS, s);
		}
		if (itemStackIn.getCount() != 1) {
			return ActionResult.<ItemStack>newResult(EnumActionResult.PASS, itemStackIn);
		}
		ItemStack modifyStack;
		if (itemStackIn.getItem() == emptyCanister)
			modifyStack = new ItemStack(fuelCanister, 1, MAX_DAMAGE);
		else
			modifyStack = new ItemStack(fuelCanister, 1, itemStackIn.getItemDamage());
		boolean succes = false;
		for (int i = 0; i < 36; i++) {
			ItemStack stack = playerIn.inventory.mainInventory.get(i);
			if (stack != null) {
				FuelValue check;
				if ((check = fuelMap.getValue(stack)) != null) {
					int fuelLevel = check.getValue();
					int maxRefill = numMaxRefill(modifyStack);
					if (maxRefill == 0)
						break;
					int used = Math.min(stack.getCount() / check.getNeeded(),
							maxRefill / fuelLevel / check.getNeeded());
					if (used == 0)
						continue;
					succes = true;
					if (stack.getItem().hasContainerItem(stack)) {
						playerIn.inventory.addItemStackToInventory(stack.getItem().getContainerItem(stack));
					}
					if (used * check.getNeeded() == stack.getCount()) {
						playerIn.inventory.removeStackFromSlot(i);
					} else {
						stack.setCount(stack.getCount() - used * check.getNeeded());
					}
					modifyStack = new ItemStack(fuelCanister, 1, modifyStack.getItemDamage() - used * fuelLevel);
				}
			}
		}
		System.out.println("finished");
		return ActionResult.<ItemStack>newResult(succes ? EnumActionResult.SUCCESS : EnumActionResult.PASS,
				succes ? modifyStack : itemStackIn);
	}

	private int numMaxRefill(ItemStack stack) {
		if (stack.getItem() == emptyCanister)
			return MAX_DAMAGE;
		return stack.getItemDamage();
	}
}
