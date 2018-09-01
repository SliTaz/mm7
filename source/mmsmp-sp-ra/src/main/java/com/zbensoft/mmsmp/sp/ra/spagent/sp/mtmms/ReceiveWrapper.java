package com.zbensoft.mmsmp.sp.ra.spagent.sp.mtmms;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class ReceiveWrapper extends HttpServletRequestWrapper {
	private byte[] body;

	public ReceiveWrapper(HttpServletRequest request) {
		super(request);

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] b1 = new byte[1024];
		int count = 0;

		InputStream is = null;
		try {
			is = request.getInputStream();
			while ((count = is.read(b1)) > 0) {
				os.write(b1, 0, count);
			}

			this.body = os.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				is.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				os.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(this.body);

		return new ServletInputStream() {
			public int read() throws IOException {
				return bais.read();
			}
		};
	}
}
