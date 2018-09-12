package com.zbensoft.mmsmp.common.ra.common.pool;

import java.util.Iterator;

public class MobileDataStore {
	private IMobileObj[][] data;
	private int[] dataSize = new int[100000];
	private int initSize;
	private int mobileCount = 0;

	public Iterator iterator() {
		return new Iterator() {
			private int currentIndex1 = 0;
			private int currentIndex2 = 0;

			public boolean hasNext() {
				if (MobileDataStore.this.mobileCount == 0) {
					return false;
				}
				do
					if ((MobileDataStore.this.dataSize[this.currentIndex1] == 0) || (this.currentIndex2 >= MobileDataStore.this.dataSize[this.currentIndex1])) {
						this.currentIndex1 += 1;
						this.currentIndex2 = 0;
					} else {
						return true;
					}
				while (this.currentIndex1 < 100000);

				return false;
			}

			public Object next() {
				if (!hasNext()) {
					return null;
				}
				String mobile = "" + MobileDataStore.this.data[this.currentIndex1][this.currentIndex2].getMobileValue();
				if (this.currentIndex1 < 10)
					mobile = mobile + "0000" + this.currentIndex1;
				else if ((this.currentIndex1 >= 10) && (this.currentIndex1 < 100))
					mobile = mobile + "000" + this.currentIndex1;
				else if ((this.currentIndex1 >= 100) && (this.currentIndex1 < 1000))
					mobile = mobile + "00" + this.currentIndex1;
				else if ((this.currentIndex1 >= 1000) && (this.currentIndex1 < 10000))
					mobile = mobile + "0" + this.currentIndex1;
				else {
					mobile = mobile + this.currentIndex1;
				}
				this.currentIndex2 += 1;
				return mobile;
			}

			public void remove() {
			}
		};
	}

	public int getMobileCount() {
		return this.mobileCount;
	}

	public MobileDataStore(int initSize) {
		this.data = new IMobileObj[100000][initSize];
		this.initSize = initSize;
	}

	public void clear() {
		this.data = new IMobileObj[100000][this.initSize];
		this.mobileCount = 0;
		this.dataSize = new int[100000];
	}

	public boolean containsMobile(String mobile) {
		try {
			long mobileNumber = Long.parseLong(mobile);

			int index = (int) (mobileNumber % 100000L);
			int value = (int) (mobileNumber / 100000L);

			IMobileObj[] valueArray = this.data[index];

			if (this.dataSize[index] <= 10) {
				for (int i = 0; i < this.dataSize[index]; i++) {
					if (valueArray[i].getMobileValue() == value) {
						return true;
					}
				}

				return false;
			}

			int start = 0;
			int end = this.dataSize[index] - 1;

			while (end >= start) {
				int current = (start + end) / 2;
				if (value == valueArray[current].getMobileValue()) {
					return true;
				}
				if (value > valueArray[current].getMobileValue())
					start = current + 1;
				else {
					end = current - 1;
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public IMobileObj getMobileObj(String mobile) {
		try {
			long mobileNumber = Long.parseLong(mobile);

			int index = (int) (mobileNumber % 100000L);
			int value = (int) (mobileNumber / 100000L);

			IMobileObj[] valueArray = this.data[index];

			if (this.dataSize[index] <= 10) {
				for (int i = 0; i < this.dataSize[index]; i++) {
					if (valueArray[i].getMobileValue() == value) {
						return valueArray[i];
					}
				}

				return null;
			}

			int start = 0;
			int end = this.dataSize[index] - 1;

			while (end >= start) {
				int current = (start + end) / 2;
				if (value == valueArray[current].getMobileValue()) {
					return valueArray[current];
				}
				if (value > valueArray[current].getMobileValue())
					start = current + 1;
				else {
					end = current - 1;
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void addMobile(String mobile, IMobileObj mobileObj) {
		try {
			long mobileNumber = Long.parseLong(mobile);

			int index = (int) (mobileNumber % 100000L);
			int value = (int) (mobileNumber / 100000L);

			IMobileObj[] valueArray = this.data[index];
			if (this.dataSize[index] >= valueArray.length) {
				IMobileObj[] temp = new IMobileObj[this.dataSize[index] + 10];
				System.arraycopy(valueArray, 0, temp, 0, this.dataSize[index]);
				this.data[index] = temp;
				valueArray = temp;
			}

			if (this.dataSize[index] <= 10) {
				for (int i = 0; i < this.dataSize[index]; i++) {
					if (valueArray[i].getMobileValue() == value) {
						mobileObj.setMobileValue(value);
						valueArray[i] = mobileObj;
						return;
					}
					if (valueArray[i].getMobileValue() > value) {
						break;
					}

					if (i < this.dataSize[index]) {
						System.arraycopy(valueArray, i, valueArray, i + 1, this.dataSize[index] - i);
						valueArray[i] = mobileObj;
						valueArray[i].setMobileValue(value);
						this.dataSize[index] += 1;
					} else {
						valueArray[this.dataSize[index]] = mobileObj;
						int tmp254_252 = index;
						int[] tmp254_249 = this.dataSize;
						int tmp256_255 = tmp254_249[tmp254_252];
						tmp254_249[tmp254_252] = (tmp256_255 + 1);
						valueArray[tmp256_255].setMobileValue(value);
					}
					this.mobileCount += 1;
				}
				return;
			}

			int start = 0;
			int end = this.dataSize[index] - 1;

			while (end >= start) {
				int current = (start + end) / 2;
				if (value == valueArray[current].getMobileValue()) {
					mobileObj.setMobileValue(value);
					valueArray[current] = mobileObj;
					return;
				}
				if (value > valueArray[current].getMobileValue())
					start = current + 1;
				else {
					end = current - 1;
				}
			}
			if (end < 0) {
				end = 0;
			} else if (value > valueArray[end].getMobileValue()) {
				end++;
			}

			if (end >= this.dataSize[index]) {
				valueArray[this.dataSize[index]] = mobileObj;
				int tmp432_430 = index;
				int[] tmp432_427 = this.dataSize;
				int tmp434_433 = tmp432_427[tmp432_430];
				tmp432_427[tmp432_430] = (tmp434_433 + 1);
				valueArray[tmp434_433].setMobileValue(value);
			} else {
				System.arraycopy(valueArray, end, valueArray, end + 1, this.dataSize[index] - end);
				valueArray[end] = mobileObj;
				valueArray[end].setMobileValue(value);
				this.dataSize[index] += 1;
			}
			this.mobileCount += 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main2(String[] args) {
		MobileDataStore store = new MobileDataStore(10);

		store.addMobile("13390011111", new Test(1));
		store.addMobile("13380111111", new Test(2));
		store.addMobile("13370211111", new Test(3));
		store.addMobile("13360311111", new Test(4));
		store.addMobile("13390411111", new Test(5));
		store.addMobile("13330511111", new Test(6));
		store.addMobile("13306611111", new Test(7));
		store.addMobile("13300711111", new Test(8));
		store.addMobile("13308811111", new Test(9));
		store.addMobile("13300911111", new Test(10));
		System.out.println(store.containsMobile("13302911111"));
		store.addMobile("13399011111", new Test(11));
		System.out.println(store.containsMobile("13302911111"));
		store.addMobile("13301111111", new Test(12));
		System.out.println(store.containsMobile("13302911111"));
		store.addMobile("13302111111", new Test(13));
		System.out.println(store.containsMobile("13302911111"));
		System.out.println(store.containsMobile("13300911111"));

		store.addMobile("13302111111", new Test(99999));

		Iterator iter = store.iterator();
		while (iter.hasNext()) {
			String mobile = iter.next().toString();
			IMobileObj mobileObj = store.getMobileObj(mobile);
			System.out.println("mobile:" + mobile + " mobileValue:" + mobileObj.getMobileValue() + " num1:" + ((Test) mobileObj).num1);
		}

		System.out.println("mobileCount:" + store.getMobileCount());

		store.clear();

		store.addMobile("13390011111", new Test(1));
		store.addMobile("13380111111", new Test(2));
		store.addMobile("13370211111", new Test(3));
		store.addMobile("13360311111", new Test(4));
		store.addMobile("13390411111", new Test(5));
		store.addMobile("13330511111", new Test(6));
		store.addMobile("13306611111", new Test(7));
		store.addMobile("13300711111", new Test(8));
		store.addMobile("13308811111", new Test(9));
		store.addMobile("13300911111", new Test(10));
		System.out.println(store.containsMobile("13302911111"));
		store.addMobile("13399011111", new Test(11));
		System.out.println(store.containsMobile("13302911111"));
		store.addMobile("13301111111", new Test(12));
		System.out.println(store.containsMobile("13302911111"));
		store.addMobile("13302111111", new Test(13));
		System.out.println(store.containsMobile("13302911111"));
		System.out.println(store.containsMobile("13300911111"));

		store.addMobile("13302111111", new Test(99999));

		store.addMobile("13390022222", new Test(1));
		store.addMobile("13380122222", new Test(2));
		store.addMobile("13370222222", new Test(3));
		store.addMobile("13360322222", new Test(4));
		store.addMobile("13390422222", new Test(5));
		store.addMobile("13330522222", new Test(6));
		store.addMobile("13306622222", new Test(7));
		store.addMobile("13300722222", new Test(8));
		store.addMobile("13308822222", new Test(9));
		store.addMobile("13300922222", new Test(10));
		System.out.println(store.containsMobile("13302922222"));
		store.addMobile("13399022222", new Test(11));
		System.out.println(store.containsMobile("13302922222"));
		store.addMobile("13301122222", new Test(12));
		System.out.println(store.containsMobile("13302922222"));
		store.addMobile("13302122222", new Test(13));
		System.out.println(store.containsMobile("13302922222"));
		System.out.println(store.containsMobile("13300922222"));

		iter = store.iterator();
		while (iter.hasNext()) {
			String mobile = iter.next().toString();
			IMobileObj mobileObj = store.getMobileObj(mobile);
			System.out.println("mobile:" + mobile + " mobileValue:" + mobileObj.getMobileValue() + " num1:" + ((Test) mobileObj).num1);
		}

		System.out.println("mobileCount:" + store.getMobileCount());
	}

	public static void main(String[] args) {
		MobileDataStore store = new MobileDataStore(10);
		for (int i = 100; i >= 0; i--) {
			for (int j = 0; j < 10000; j++) {
				String mobile = "133";
				if (i < 10)
					mobile = mobile + "00" + i;
				else if (i < 100)
					mobile = mobile + "0" + i;
				else {
					mobile = mobile + i;
				}
				if (j < 10)
					mobile = mobile + "0000" + j;
				else if (j < 100)
					mobile = mobile + "000" + j;
				else if (j < 1000)
					mobile = mobile + "00" + j;
				else if (j < 10000)
					mobile = mobile + "0" + j;
				else {
					mobile = mobile + i;
				}

				IntArrayMobileObj testObj = new IntArrayMobileObj();
				testObj.addData(100);
				testObj.addData(200);
				testObj.addData(300);
				store.addMobile(mobile, testObj);
			}
		}

		System.out.println("contains 13301001123:" + store.containsMobile("13301001123"));
		System.out.println("contains 13301001123 100:" + ((IntArrayMobileObj) store.getMobileObj("13301001123")).hasData(100));
		System.out.println("contains 13301001123 200:" + ((IntArrayMobileObj) store.getMobileObj("13301001123")).hasData(200));
		System.out.println("contains 13301001123 300:" + ((IntArrayMobileObj) store.getMobileObj("13301001123")).hasData(300));
		System.out.println("contains 13302001123:" + store.containsMobile("13302001123"));
		System.out.println("contains 13302001123 100:" + ((IntArrayMobileObj) store.getMobileObj("13302001123")).hasData(100));
		System.out.println("contains 13302001123 200:" + ((IntArrayMobileObj) store.getMobileObj("13302001123")).hasData(200));
		System.out.println("contains 13302001123 300:" + ((IntArrayMobileObj) store.getMobileObj("13302001123")).hasData(300));
		System.out.println("contains 13304901123:" + store.containsMobile("13304901123"));
		System.out.println("contains 13304901123 100:" + ((IntArrayMobileObj) store.getMobileObj("13304901123")).hasData(100));
		System.out.println("contains 13304901123 200:" + ((IntArrayMobileObj) store.getMobileObj("13304901123")).hasData(200));
		System.out.println("contains 13304901123 300:" + ((IntArrayMobileObj) store.getMobileObj("13304901123")).hasData(300));
		System.out.println("contains 13305001123:" + store.containsMobile("13305001123"));
		System.out.println("contains 13305001123 100:" + ((IntArrayMobileObj) store.getMobileObj("13305001123")).hasData(100));
		System.out.println("contains 13305001123 200:" + ((IntArrayMobileObj) store.getMobileObj("13305001123")).hasData(200));
		System.out.println("contains 13305001123 300:" + ((IntArrayMobileObj) store.getMobileObj("13305001123")).hasData(300));
		System.out.println("contains 13307001123:" + store.containsMobile("13307001123"));
		System.out.println("contains 13307001123 100:" + ((IntArrayMobileObj) store.getMobileObj("13307001123")).hasData(100));
		System.out.println("contains 13307001123 200:" + ((IntArrayMobileObj) store.getMobileObj("13307001123")).hasData(200));
		System.out.println("contains 13307001123 300:" + ((IntArrayMobileObj) store.getMobileObj("13307001123")).hasData(300));

		System.out.println("contains 13301009999:" + store.containsMobile("13301009999"));
		System.out.println("contains 13301009999 100:" + ((IntArrayMobileObj) store.getMobileObj("13301009999")).hasData(100));
		System.out.println("contains 13301009999 200:" + ((IntArrayMobileObj) store.getMobileObj("13301009999")).hasData(200));
		System.out.println("contains 13301009999 300:" + ((IntArrayMobileObj) store.getMobileObj("13301009999")).hasData(300));
		System.out.println("contains 13302009999:" + store.containsMobile("13302009999"));
		System.out.println("contains 13304909999:" + store.containsMobile("13304909999"));
		System.out.println("contains 13305009999:" + store.containsMobile("13305009999"));
		System.out.println("contains 13307009999:" + store.containsMobile("13307009999"));
		System.out.println("finished");
		try {
			Thread.sleep(10000L);
		} catch (Exception localException) {
		}
	}

	public static abstract interface IMobileObj {
		public abstract int getMobileValue();

		public abstract void setMobileValue(int paramInt);
	}

	public static class IntArrayMobileObj implements MobileDataStore.IMobileObj {
		private int[] data;
		private int mobileValue;

		public boolean hasData(int value) {
			if (this.data == null) {
				return false;
			}
			for (int i = 0; i < this.data.length; i++) {
				if (this.data[i] == value) {
					return true;
				}
			}

			return false;
		}

		public void addData(int value) {
			if (this.data == null) {
				this.data = new int[1];
				this.data[0] = value;
				return;
			}
			for (int i = 0; i < this.data.length; i++) {
				if (this.data[i] == value) {
					return;
				}
			}

			int[] temp = new int[this.data.length + 1];
			System.arraycopy(this.data, 0, temp, 0, this.data.length);
			temp[(temp.length - 1)] = value;

			this.data = temp;
		}

		public void removeData(int value) {
			if (this.data == null) {
				return;
			}
			if ((this.data.length == 1) && (this.data[0] == value)) {
				this.data = null;
				return;
			}
			for (int i = 0; i < this.data.length; i++)
				if (this.data[i] == value) {
					int[] temp = new int[this.data.length - 1];

					System.arraycopy(this.data, 0, temp, 0, i);
					System.arraycopy(this.data, i + 1, temp, i, this.data.length - i - 1);

					this.data = temp;
					return;
				}
		}

		public int[] getData() {
			return this.data;
		}

		public int getMobileValue() {
			return this.mobileValue;
		}

		public void setMobileValue(int value) {
			this.mobileValue = value;
		}
	}

	public static class SimpleMobileObj implements MobileDataStore.IMobileObj {
		private int value;

		public int getMobileValue() {
			return this.value;
		}

		public void setMobileValue(int value) {
			this.value = value;
		}
	}

	public static class Test implements MobileDataStore.IMobileObj {
		public int num1;
		public int num2;
		public int num3;
		public int num4;
		public int num5;
		public int num6;
		private int mobileValue;

		public Test(int num1) {
			this.num1 = num1;
		}

		public int getMobileValue() {
			return this.mobileValue;
		}

		public void setMobileValue(int value) {
			this.mobileValue = value;
		}
	}
}
