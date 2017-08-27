package at.xander.fuelcanister;

import java.util.HashMap;
import java.util.Map.Entry;

import at.xander.configbuilder.parts.ItemInt;
import at.xander.configbuilder.parts.MetaItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class FuelValues {
	private HashMap<MetaItem, FuelValue> stackToValues;
	private HashMap<String, FuelValue> oreDictToValues;

	public FuelValues() {
		stackToValues = new HashMap<MetaItem, FuelValue>();
		oreDictToValues = new HashMap<String, FuelValue>();
	}

	public void addItem(ItemInt item) {
		String[] data = item.getItem().split(":");
		if (data[0].equals("ore")) {
			oreDictToValues.put(data[1], item.getValue());
		} else {
			stackToValues.put(new MetaItem(data[0], data[1], item.getMeta()), item.getValue());
		}
	}

	public FuelValue getValue(ItemStack iStack) {
		FuelValue value;
		if(iStack.isEmpty()) {
			return null;
		}
		value = stackToValues.get(new MetaItem(iStack));
		if (value == null) {
			int[] oreDictNames = OreDictionary.getOreIDs(iStack);
			for (int i : oreDictNames) {
				String name = OreDictionary.getOreName(i);
				value = oreDictToValues.get(name);
				if (value != null)
					break;
			}
		}
		return value != null ? value : null;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Items: ");
		for(Entry<MetaItem, FuelValue> entry : stackToValues.entrySet()){
			sb.append(entry.getKey());
			sb.append("-");
			sb.append(entry.getValue());
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(System.lineSeparator());
		sb.append("OreDict: ");
		for(Entry<String, FuelValue> entry : oreDictToValues.entrySet()){
			sb.append("ore:"+entry.getKey());
			sb.append("-");
			sb.append(entry.getValue());
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
}
