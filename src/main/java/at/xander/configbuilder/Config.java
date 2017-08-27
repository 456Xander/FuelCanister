package at.xander.configbuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import at.xander.configbuilder.parts.IConfigPart;

/**
 * This Class represents a config and handles all the things.
 *
 */
public class Config {
	/**
	 * The Version of the Config
	 */
	private final int configVersion;
	/**
	 * The File where everything is written to
	 */
	private final File cfgFile;
	/**
	 * The writer for writing the config, it is lazy loaded
	 */
	private BufferedWriter writer;
	/**
	 * Maps all parts to their name. Values are put in read, and read in get
	 */
	private Map<String, IConfigPart<? extends Object>> parts = new HashMap<String, IConfigPart<? extends Object>>();

	/**
	 * Creates a new Config with the File file
	 * 
	 * @param file
	 */
	public Config(File file, int version) {
		this.cfgFile = file;
		this.configVersion = version;
	}

	public boolean shouldBeCreated() {
		if (!cfgFile.exists()) {
			return true;
		}
		BufferedReader reader = null;
		int versionOfFile = 1;
		boolean rewriteVersion = true;
		try {
			reader = new BufferedReader(new FileReader(cfgFile));
			String firstLine = reader.readLine();
			if (firstLine.contains("@Version")) {
				firstLine = firstLine.replaceAll("@Version", "");
				versionOfFile = Integer.parseInt(firstLine.trim());
				rewriteVersion = false;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (NumberFormatException e) {
			versionOfFile = 1;
			rewriteVersion = true;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (versionOfFile < configVersion)
			return true;
		if (rewriteVersion) {
			try {
				writeVersion();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		boolean create = !cfgFile.exists();
		try {
			if (create)
				cfgFile.createNewFile();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return create;
	}

	private void writeVersion() throws IOException {
		ReaderIterator reader = new ReaderIterator(cfgFile);
		BufferedWriter writer = null;
		try {
			StringBuilder sb = new StringBuilder();
			for (String line : reader) {
				if (line.contains("@Version"))
					continue;
				sb.append(line);
				sb.append(System.lineSeparator());
			}
			writer = new BufferedWriter(new FileWriter(cfgFile));
			sb.insert(0, getVersion() + System.lineSeparator());
			writer.write(sb.toString());
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (writer != null) {
				writer.close();
			}
		}
	}

	public void closeWrite() {
		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		writer = null;
	}

	public void write(IConfigPart<? extends Object> part) {
		try {
			if (writer == null)
				writer = new BufferedWriter(new FileWriter(cfgFile));
			writer.write(part.getComment());
			writer.write(part.getStartingChar() + ":\t\t" + part.getName() + '=' + part.getMessage()
					+ System.lineSeparator());
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void read() {
		ReaderIterator reader = null;
		try {
			reader = new ReaderIterator(cfgFile);
			StringBuilder multiLineData = new StringBuilder();
			boolean multiLine = false;
			for (String line : reader) {
				line = line.trim();
				if (line.equals(""))
					continue;
				if (line.charAt(0) == '#' || line.charAt(0) == '@')
					continue;
				line = line.replace("\t", "");
				if (line.contains("[")) {
					// [ represents MultiLine
					multiLineData.append(line);
					multiLine = true;
					continue;
				}
				if (multiLine) {
					multiLineData.append(line);
					continue;
				}
				if (line.contains("]") && multiLine) {
					multiLine = false;
					multiLineData.append(line);

					continue;
				}
				PartFactory assembler = new PartFactory(line);
				parts.put(assembler.getName(), assembler.assemble());
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public <T> T get(String name) {
		IConfigPart<T> part = (IConfigPart<T>) parts.get(name);
		if (part == null) {
			return null;
		}
		return part.read(part.getMessage());
	}

	public String getVersion() {
		return "@Version" + configVersion;
	}
}
