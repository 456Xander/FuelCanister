package at.xander.configbuilder.parts;

public class PartBoolean implements IConfigPart<Boolean>{
	private final String message, name, comment;
	public PartBoolean(String name, boolean value, String... comment) {
		this.name = name;
		this.message = String.valueOf(value);
		StringBuilder b = new StringBuilder();
		for(String line : comment){
			b.append("#" + line + System.lineSeparator());
		}
		this.comment = b.toString();
	}
	
	public PartBoolean(String name, String message) {
		this.comment = "";
		this.name = name;
		this.message = message;
	}

	@Override
	public char getStartingChar() {
		return 'B';
	}

	@Override
	public String getComment() {
		return comment;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public Boolean read(String message) {
		return Boolean.parseBoolean(message.trim());
	}

}
