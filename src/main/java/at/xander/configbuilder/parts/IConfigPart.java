package at.xander.configbuilder.parts;

public interface IConfigPart<T> {
	char getStartingChar();
	String getComment();
	String getName();
	String getMessage();
	T read(String message);
}
