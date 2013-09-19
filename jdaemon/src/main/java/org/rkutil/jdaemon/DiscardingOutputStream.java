package org.rkutil.jdaemon;

import java.io.IOException;
import java.io.OutputStream;

public class DiscardingOutputStream extends OutputStream {

	@Override
	public void write(int b) throws IOException {
	}

	@Override
	public void write(byte[] b) throws IOException {
	}

	public void write(byte[] b, int off, int len) throws IOException {
	}

}
