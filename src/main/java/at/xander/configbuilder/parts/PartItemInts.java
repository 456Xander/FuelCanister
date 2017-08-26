package at.xander.configbuilder.parts;

import java.util.ArrayList;

public class PartItemInts implements IConfigPart<ItemInt[]> {
	private final String message, name, comment;

	public PartItemInts(String name, ItemInt[] value, String... comment) {
		this.name = name;
		StringBuilder messageBuilder = new StringBuilder(15 * value.length);
		if (value.length == 0) {
			messageBuilder.append("nothing");
		} else {
			messageBuilder.append(value[0].toString());
			for (int i = 1; i < value.length; i++) {
				messageBuilder.append(",");
				messageBuilder.append(value[i].toString());
			}
		}
		this.message = messageBuilder.toString();
		StringBuilder b = new StringBuilder();
		for (String line : comment) {
			b.append("#" + line + System.lineSeparator());
		}
		this.comment = b.toString();
	}

	public PartItemInts(String name, String message) {
		this.comment = "";
		this.name = name;
		this.message = message;
	}

	@Override
	public char getStartingChar() {
		return 'D';
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
	public ItemInt[] read(String message) {
		if (message.equals("nothing")) {
			return new ItemInt[0];
		}
		String[] items = message.split(",");
		ArrayList<ItemInt> results = new ArrayList<ItemInt>();
		for (String i : items) {
			try {
				results.add(ItemInt.getFromString(i));
			} catch (NumberFormatException e) {
				System.out.println("Couldn't read " + i + ", skipping Item");
			}
		}
		return results.toArray(new ItemInt[results.size()]);
	}

}
