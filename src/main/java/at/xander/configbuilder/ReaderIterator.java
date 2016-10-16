package at.xander.configbuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class ReaderIterator implements Iterable<String> {
	private BufferedReader reader;

	public ReaderIterator(File f) throws IOException {
		reader = new BufferedReader(new FileReader(f));
	}

	public void close() throws IOException {
		reader.close();
	}

	@Override
	public Iterator<String> iterator() {
		return new Iterator<String>() {
			private String line = null;

			@Override
			public boolean hasNext() {
				if (line == null) {
					try {
						line = reader.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return line != null;
			}

			@Override
			public String next() {
				String s;
				if (line != null) {
					s = line;
					line = null;
					return s;
				}
				try {
					return reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
					return "";
				}
			}

		};
	}
}
