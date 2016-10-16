package at.xander.configbuilder.parts;

public class PartString implements IConfigPart<String>{
	private final String name, message, comment;
	public PartString(String name, String value, String... comment) {
		this.name = name;
		this.message = value;
		StringBuilder b = new StringBuilder();
		for(String line : comment){
			b.append("#" + line + System.lineSeparator());
		}
		this.comment = b.toString();
	}
	
	public PartString(String name, String message) {
		this.comment = "";
		this.name = name;
		this.message = message;
	}

	@Override
	public char getStartingChar() {
		return 'S';
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
	public String read(String message) {
		return message;
	}
}
