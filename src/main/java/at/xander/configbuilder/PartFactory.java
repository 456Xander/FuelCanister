package at.xander.configbuilder;

import at.xander.configbuilder.parts.IConfigPart;
import at.xander.configbuilder.parts.PartBoolean;
import at.xander.configbuilder.parts.PartFloat;
import at.xander.configbuilder.parts.PartInteger;
import at.xander.configbuilder.parts.PartItemInts;
import at.xander.configbuilder.parts.PartString;

public class PartFactory {
	private final String name, message;
	private final char start;
	public PartFactory(String name, String message, char start) {
		this.name = name;
		this.message = message;
		this.start = start;
	}
	public IConfigPart<? extends Object> assemble() {
		if (start == 'B') {
			return new PartBoolean(name, message);
		}
		if(start == 'I'){
			return new PartInteger(name, message);
		}
		if(start == 'F'){
			return new PartFloat(name, message);
		}
		if(start == 'S'){
			return new PartString(name, message);
		}
		if(start == 'D'){
			return new PartItemInts(name, message);
		}
		return null;
	}

	public PartFactory(String line) {
		line = line.trim().replace("\t", "");
		String[] data = line.split("=");
		if(data.length != 2){
			throw new IllegalArgumentException("Malformed String line passed to Part factory. Config part cannot be read");
		}
		String name = data[0];
		name = name.substring(name.indexOf(':') + 1, name.length());
		this.name = name.trim();
		message = data[1];
		start = line.charAt(0);
	}
	
	public String getName(){
		return name;
	}
	
	public static PartFactory createMultiLineFactory(String multiLineData){
		char start;
		String name, message;
		multiLineData = multiLineData.trim().replace("\t", "");
		start = multiLineData.charAt(0);
		name = multiLineData.substring(0, multiLineData.indexOf("="));
		name = name.trim();
		message = multiLineData.substring(multiLineData.indexOf("[") + 1, multiLineData.indexOf("]"));
		return new PartFactory(name, message, start);
	}
}
