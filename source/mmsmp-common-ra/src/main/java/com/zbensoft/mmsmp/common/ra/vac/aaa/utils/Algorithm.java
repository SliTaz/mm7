package com.zbensoft.mmsmp.common.ra.vac.aaa.utils;

public class Algorithm {
	protected byte[] short2ByteArray(short ori) {
		byte[] tmpByte = new byte[2];
		tmpByte[1] = ((byte) (ori & 0xFF));
		tmpByte[0] = ((byte) ((ori & 0xFF00) >> 8));
		return tmpByte;
	}

	protected short byteArray2Short(byte[] ori) {
		int length = ori.length;
		short result = 0;
		for (int i = 0; i < length; i++) {
			int tmpb = ori[i] < 0 ? 256 + ori[i] : ori[i];
			result = (short) (result | tmpb << 8 * (length - 1 - i));
		}
		return result;
	}

	protected byte[] int2ByteArray(int ori) {
		byte[] tmpByte = new byte[4];
		tmpByte[3] = ((byte) (ori & 0xFF));
		tmpByte[2] = ((byte) ((ori & 0xFF00) >> 8));
		tmpByte[1] = ((byte) ((ori & 0xFF0000) >> 16));
		tmpByte[0] = ((byte) ((ori & 0xFF000000) >> 24));
		return tmpByte;
	}

	protected int byteArray2Int(byte[] ori) {
		int length = ori.length;
		int result = 0;
		for (int i = 0; i < length; i++) {
			int tmpb = ori[i] < 0 ? 256 + ori[i] : ori[i];
			result |= tmpb << 8 * (length - 1 - i);
		}
		return result;
	}

	protected long byteArray2Long(byte[] ori) {
		int length = ori.length;
		long result = 0L;
		for (int i = 0; i < length; i++) {
			int tmpb = ori[i] < 0 ? 256 + ori[i] : ori[i];
			result |= tmpb << 8 * (length - 1 - i);
		}
		return result;
	}

	protected byte[] long2ByteArray(long ori) {
		byte[] tmpByte = new byte[8];
		tmpByte[7] = ((byte) (int) (ori & 0xFF));
		tmpByte[6] = ((byte) (int) ((ori & 0xFF00) >> 8));
		tmpByte[5] = ((byte) (int) ((ori & 0xFF0000) >> 16));
		tmpByte[4] = ((byte) (int) ((ori & 0xFF000000) >> 24));
		tmpByte[3] = ((byte) (int) ((ori & 0x0) >> 32));
		tmpByte[2] = ((byte) (int) ((ori & 0x0) >> 40));
		tmpByte[1] = ((byte) (int) ((ori & 0x0) >> 48));
		tmpByte[0] = ((byte) (int) ((ori & 0x0) >> 56));
		return tmpByte;
	}
}
