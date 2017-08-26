package at.xander.configbuilder.parts;

public class PartInteger implements IConfigPart<Integer>{
	private final String message, name, comment;
	public PartInteger(String name, int value, String... comment) {
		this.name = name;
		this.message = String.valueOf(value);
		StringBuilder b = new StringBuilder();
		for(String line : comment){
			b.append("#" + line + System.lineSeparator());
		}
		this.comment = b.toString();
	}
	
	public PartInteger(String name, String message) {
		this.comment = "";
		this.name = name;
		this.message = message;
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
	public char getStartingChar() {
		return 'I';
	}

	@Override
	public Integer read(String message) {
		return Integer.parseInt(message);
	}

}
