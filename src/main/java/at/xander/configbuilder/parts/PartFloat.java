package at.xander.configbuilder.parts;

public class PartFloat implements IConfigPart<Float>{
	private final String message, name, comment;
	public PartFloat(String name, float value, String... comment) {
		this.name = name;
		this.message = String.valueOf(value);
		StringBuilder b = new StringBuilder();
		for(String line : comment){
			b.append("#" + line + System.lineSeparator());
		}
		this.comment = b.toString();
	}
	
	public PartFloat(String name, String message) {
		this.comment = "";
		this.name = name;
		this.message = message;
	}

	@Override
	public char getStartingChar() {
		return 'F';
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
	public Float read(String message) {
		return Float.parseFloat(message);
	}

}
