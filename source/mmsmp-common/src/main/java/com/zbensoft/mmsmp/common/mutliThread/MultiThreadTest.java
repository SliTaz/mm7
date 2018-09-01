package com.zbensoft.mmsmp.common.mutliThread;

public class MultiThreadTest extends MultiThread {

	public MultiThreadTest(String name) {
		super(name);
	}

	@Override
	public boolean process() {
		return false;

	}

	public static void main(String[] args) throws InterruptedException {
		// MultiThreadManage.getInstance().addThread(MultiThreadTest.class, "", 4, "", 5, "", 1000);
		Thread.sleep(10 * 1000);
		// MultiThreadManage.getInstance().addThread(MultiThreadTest.class, "", 3, "", 5, "", 1000);
	}
}
