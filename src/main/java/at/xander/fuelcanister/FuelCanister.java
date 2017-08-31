package at.xander.fuelcanister;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.xander.configbuilder.Config;
import at.xander.configbuilder.parts.ItemInt;
import at.xander.configbuilder.parts.MetaItem;
import at.xander.configbuilder.parts.PartInteger;
import at.xander.configbuilder.parts.PartItemInts;
import at.xander.fuelcanister.proxies.CommonProxy;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid = "fuelcanister", name = "Fuel Canisters", version = "1.1.2")
public class FuelCanister {
	public static final String modid = "fuelcanister";
	/**
	 * 
	 */
	public static FuelValues fuelMap;
	@Instance
	public static FuelCanister instance;
	@SidedProxy(clientSide = "at.xander.fuelcanister.proxies.ClientProxy", serverSide = "at.xander.fuelcanister.proxies.ServerProxy")
	public static CommonProxy proxy;


	public static int MAX_DAMAGE = 8192;
	private static Config cfg;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(RegisterHandler.instance);
		fuelMap = new FuelValues();
		cfg = new Config(e.getSuggestedConfigurationFile(), 1);
		if (cfg.shouldBeCreated()) {
			cfg.write(new PartInteger("MaxSmeltingOfCanister", 8192,
					"Defines how many Items a full canister can smelt in a furnace", "Default is 8192"));
			ItemInt[] items = new ItemInt[] { new ItemInt("minecraft:coal", 8), new ItemInt("minecraft:coal", 8, 1) };
			cfg.write(new PartItemInts("ListFuelItemsForCanister", items,
					"A comma Seperated List of all Items usable for the fuel canister",
					"The first part is the string id, the part after -",
					"is the ammount of Items it can smelt in a furnace", "minecraft:coal:1 is charcoal",
					"You can also use ore: for OreDictionary Names like", "ore:logWood"));
			cfg.closeWrite();
		}
		cfg.read();
		MAX_DAMAGE = cfg.get("MaxSmeltingOfCanister");
		proxy.preInit(e);
	}

	private void registerFuels(Config cfg) {
		ItemInt[] fuels = cfg.get("ListFuelItemsForCanister");
		for (ItemInt fuel : fuels) {
			// MetaItem mItem = fuel.getItemAsMetaItem();
			// fuelMap.put(mItem, fuel.getInteger());
			fuelMap.addItem(fuel);
		}
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		cfg.read();
		registerFuels(cfg);
	}
}
