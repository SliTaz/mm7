package com.zbensoft.mmsmp.sp.ra.spagent;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;

public class FileQueue<E extends Serializable> implements Queue<E> {
	public static final int PAGE_SIZE = 134217728;
	public static final int SIZE_OF_INT = 4;
	public static String FILE_NAME = "data";
	public static String FILE_SUFFIX = ".dat";
	public static String INDEX_NAME = "data.inx";
	public String fileDir;
	private RandomAccessFile readFile;
	private RandomAccessFile writeFile;
	private RandomAccessFile indexFile;
	private FileChannel readChannel;
	private FileChannel writeChannel;
	private FileChannel indexChannel;
	private MappedByteBuffer readMbb;
	private MappedByteBuffer writeMbb;
	private MappedByteBuffer indexMbb;
	private static final int INDEX_SIZE = 8;
	private static final int HEADER_SIZE = 8;
	private static final int ENDER_SIZE = 8;
	private ByteBuffer headerBb = ByteBuffer.allocate(8);
	private int readIndexFile;
	private int writeIndexFile;

	public int size() {
		return 0;
	}

	public boolean isEmpty() {
		return false;
	}

	public boolean contains(Object o) {
		return false;
	}

	public Iterator<E> iterator() {
		return null;
	}

	public Object[] toArray() {
		return null;
	}

	public <T> T[] toArray(T[] a) {
		return null;
	}

	public boolean remove(Object o) {
		return false;
	}

	public boolean containsAll(Collection<?> c) {
		return false;
	}

	public boolean addAll(Collection<? extends E> c) {
		return false;
	}

	public boolean removeAll(Collection<?> c) {
		return false;
	}

	public boolean retainAll(Collection<?> c) {
		return false;
	}

	public void clear() {
	}

	public boolean add(E e) {
		return false;
	}

	public boolean offer(E e) {
		return false;
	}

	public E remove() {
		return null;
	}

	public E poll() {
		return null;
	}

	public E element() {
		return null;
	}

	public E peek() {
		return null;
	}

	public FileQueue(String fileDir) throws IOException {
		if ((fileDir == null) || (fileDir.trim().length() == 0)) {
			throw new IllegalArgumentException("filename illegal");
		}

		if (!fileDir.endsWith("/")) {
			fileDir = fileDir + File.separator;
		}

		File dir = new File(fileDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		this.fileDir = fileDir;

		this.indexFile = new RandomAccessFile(fileDir + INDEX_NAME, "rw");
		this.indexChannel = this.indexFile.getChannel();
		this.indexMbb = this.indexChannel.map(FileChannel.MapMode.READ_WRITE, 0L, 8L);

		this.readIndexFile = this.indexMbb.getInt();
		this.writeIndexFile = this.indexMbb.getInt();

		this.readFile = new RandomAccessFile(fileDir + FILE_NAME + this.readIndexFile + FILE_SUFFIX, "rw");
		this.readChannel = this.readFile.getChannel();

		this.writeFile = new RandomAccessFile(fileDir + FILE_NAME + this.writeIndexFile + FILE_SUFFIX, "rw");
		this.writeChannel = this.writeFile.getChannel();

		this.readMbb = this.readChannel.map(FileChannel.MapMode.READ_WRITE, 0L, 134217728L);
		this.writeMbb = this.writeChannel.map(FileChannel.MapMode.READ_WRITE, 0L, 134217728L);

		initWriteMbb();
		initReadMbb();
	}

	private void initReadMbb() {
		int currentPos = this.readMbb.position();
		int type = this.readMbb.getInt();
		int length = this.readMbb.getInt();

		while ((type == ITEM_TYPE.EMPTY.ordinal()) && (length > 0)) {
			this.readMbb.position(currentPos + 8 + length);
			currentPos = this.readMbb.position();
			type = this.readMbb.getInt();
			length = this.readMbb.getInt();
		}

		this.readMbb.position(currentPos);
	}

	private void initWriteMbb() {
		int currentPos = this.writeMbb.position();
		int type = this.writeMbb.getInt();
		int length = this.writeMbb.getInt();

		while (length > 0) {
			this.writeMbb.position(currentPos + 8 + length);
			currentPos = this.writeMbb.position();
			type = this.writeMbb.getInt();
			length = this.writeMbb.getInt();
		}

		this.writeMbb.position(currentPos);
	}

	public synchronized void product(E item) throws Exception {
		if (item == null) {
			throw new IllegalArgumentException("item is null");
		}

		byte[] contents = toBytes(item);
		int length = contents.length;
		int writePos = this.writeMbb.position();

		if (writePos + length + 8 + 8 >= 134217728) {
			this.writeIndexFile += 1;
			this.writeMbb.putInt(ITEM_TYPE.ROTATE.ordinal());
			this.writeMbb.force();

			unmap(this.writeMbb);
			closeResource(this.writeChannel);
			closeResource(this.writeFile);

			this.writeFile = new RandomAccessFile(this.fileDir + FILE_NAME + this.writeIndexFile + FILE_SUFFIX, "rw");
			this.writeChannel = this.writeFile.getChannel();
			this.writeMbb = this.writeChannel.map(FileChannel.MapMode.READ_WRITE, 0L, 134217728L);

			this.indexMbb.putInt(32, this.writeIndexFile);
		}

		this.headerBb.clear();
		this.headerBb.putInt(ITEM_TYPE.FILL.ordinal());
		this.headerBb.putInt(length);
		this.headerBb.flip();

		this.writeMbb.put(this.headerBb);
		this.writeMbb.put(contents);
	}

	private byte[] toBytes(E item) throws IOException {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);

			oos.writeObject(item);
			oos.flush();
			return baos.toByteArray();
		} finally {
			closeResource(baos);
			closeResource(oos);
		}
	}

	public synchronized E comsume() throws Exception {
		int readPos = this.readMbb.position();

		int type = this.readMbb.getInt();
		int length = this.readMbb.getInt();

		if (type == ITEM_TYPE.ROTATE.ordinal()) {
			this.readIndexFile += 1;

			this.readMbb.putInt(ITEM_TYPE.ROTATE.ordinal());
			this.readMbb.force();

			unmap(this.readMbb);
			closeResource(this.readChannel);
			closeResource(this.readFile);

			this.readFile = new RandomAccessFile(this.fileDir + FILE_NAME + this.readIndexFile + FILE_SUFFIX, "rw");
			this.readChannel = this.readFile.getChannel();
			this.readMbb = this.readChannel.map(FileChannel.MapMode.READ_WRITE, 0L, 134217728L);

			this.indexMbb.putInt(0, this.readIndexFile);
			type = this.readMbb.getInt();
			length = this.readMbb.getInt();
		}

		if ((type == ITEM_TYPE.EMPTY.ordinal()) || (length <= 0)) {
			this.readMbb.position(readPos);
			return null;
		}

		byte[] contents = new byte[length];
		this.readMbb.get(contents);
		this.readMbb.putInt(readPos, ITEM_TYPE.EMPTY.ordinal());

		Serializable object = toObject(contents);

		return (E) object;
	}

	private E toObject(byte[] content) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			bais = new ByteArrayInputStream(content);
			ois = new ObjectInputStream(bais);

			return (E) ois.readObject();
		} finally {
			closeResource(bais);
			closeResource(ois);
		}
	}

	private void closeResource(Closeable c) throws IOException {
		if (c != null)
			c.close();
	}

	private static void unmap(MappedByteBuffer buffer) {
		if (buffer == null)
			return;
		Cleaner cleaner = ((DirectBuffer) buffer).cleaner();
		if (cleaner != null)
			cleaner.clean();
	}

	public void shutdown() throws IOException {
		if (this.writeMbb != null) {
			this.writeMbb.force();
			unmap(this.writeMbb);
		}
		if (this.readMbb != null) {
			this.readMbb.force();
			unmap(this.readMbb);
		}
		if (this.indexMbb != null) {
			this.indexMbb.force();
			unmap(this.indexMbb);
		}

		closeResource(this.readChannel);
		closeResource(this.readFile);
		closeResource(this.writeChannel);
		closeResource(this.writeFile);
		closeResource(this.indexChannel);
		closeResource(this.indexFile);
	}

	private static enum ITEM_TYPE {
		EMPTY, FILL, ROTATE;
	}
}
