package at.xander.fuelcanister.proxies;



import java.util.ArrayList;
import java.util.List;

import at.xander.fuelcanister.ITextureHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	protected List<ITextureHandler> allTextureHandlers = new ArrayList<ITextureHandler>();
	
    public void preInit(FMLPreInitializationEvent e) {

    }

    public void init(FMLInitializationEvent e) {

    }

    public void postInit(FMLPostInitializationEvent e) {
    	allTextureHandlers = null;
    }
    
    public void addTextureHandler(ITextureHandler textureHandler){
    	allTextureHandlers.add(textureHandler);
    }
}
