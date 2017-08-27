package at.xander.configbuilder.parts;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class MetaItem {
	private final Item item;
	private final int meta;

	public MetaItem(String modid, String itemName, int meta) {
		ResourceLocation l = new ResourceLocation(modid, itemName);
		// First check for block, so I don't get the Block's Item
		Item item = Item.REGISTRY.getObject(l);
		if (item == null) {
			throw new IllegalArgumentException("Item named by name does not exist");
		}
		this.meta = meta;
		this.item = item;
	}

	public boolean matchesItemStack(ItemStack stack) {
		return stack.getItemDamage() == meta && stack.getItem().equals(item);
	}

	public boolean matches(Item item, int meta) {
		return this.meta == meta && this.item.equals(item);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.getUnlocalizedName().hashCode());
		result = prime * result + meta;
		return result;
	}

	public MetaItem(ItemStack stack) {
		this.item = stack.getItem();
		this.meta = stack.getItemDamage();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MetaItem other = (MetaItem) obj;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (meta != other.meta)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return item.getRegistryName().toString() + (meta != 0 ? ":" + meta : "");
	}
}
