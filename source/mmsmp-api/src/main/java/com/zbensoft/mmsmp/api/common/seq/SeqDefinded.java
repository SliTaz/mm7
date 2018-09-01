package com.zbensoft.mmsmp.api.common.seq;

public class SeqDefinded {

	public static final String MERCHANT_USER_SEQ = "merchant_user_seq";
	public static final Long MERCHANT_USER_SEQ_MIN = 1l;
	public static final Long MERCHANT_USER_SEQ_MAX = 999999999l;
	public static final Long MERCHANT_USER_SEQ_INCREMENT = 1l;
	public static final Integer MERCHANT_USER_SEQ_FORMATLEN = 8;
	
	public static final String MERCHANT_USER_STORE_TYPE_SEQ = "merchant_user_store_type_seq";
	public static final Long MERCHANT_USER_STORE_TYPE_SEQ_MIN = 1l;
	public static final Long MERCHANT_USER_STORE_TYPE_SEQ_MAX = 999999999l;
	public static final Long MERCHANT_USER_STORE_TYPE_SEQ_INCREMENT = 1l;
	public static final Integer MERCHANT_USER_STORE_TYPE_SEQ_FORMATLEN = 8;
	
	public static final String MERCHANT_EMPLOYEE_SEQ = "merchant_employee_seq";
	public static final Long MERCHANT_EMPLOYEE_SEQ_MIN = 1l;
	public static final Long MERCHANT_EMPLOYEE_SEQ_MAX = 999999999l;
	public static final Long MERCHANT_EMPLOYEE_SEQ_INCREMENT = 1l;
	public static final Integer MERCHANT_EMPLOYEE_SEQ_FORMATLEN = 8;
//
//	/**
//	 * 禁止外部实例化
//	 */
//	private SeqDefinded() {
//	}
//
//	/**
//	 * 获取SequenceUtils的单例对象
//	 * 
//	 * @return SequenceUtils的单例对象
//	 */
//	public static SeqDefinded getInstance() {
//		if (null == instance) {
//			synchronized (uniqueLock) {
//				if (null == instance) {
//					instance = new SeqDefinded();
//				}
//			}
//		}
//		return instance;
//	}
//
//	// end
//
//	public SeqDefinded(String keyName, int poolSize) throws SQLException {
//		this.poolSize = poolSize;
//		this.keyName = keyName;
//		retrieveFromDB(keyName);
//	}
//
//	public String getKeyName() {
//		return keyName;
//	}
//
//	public long getMaxKey() {
//		return maxKey;
//	}
//
//	public long getMinKey() {
//		return minKey;
//	}
//
//	public int getPoolSize() {
//		return poolSize;
//	}
//
//	/**
//	 * 获取下一个Sequence值
//	 * 
//	 * @return 下一个Sequence值
//	 * @throws SQLException
//	 */
//	public synchronized long getNextKey(String keyName) throws SQLException {
//		if (nextKey > maxKey) {
//			retrieveFromDB(keyName);
//		}
//		return nextKey++;
//	}
//
//	/**
//	 * 执行Sequence表信息初始化和更新工作
//	 * 
//	 * @throws SQLException
//	 */
//
//	private void retrieveFromDB(String keyName) throws SQLException {
//
//		synchronized (objectLock) {
//			Seqs bean = new Seqs();
//			bean.setSeqsCode(keyName);
//			list = seqsService.selectPage(bean);
//			if (list != null && list.size() > 0) {
//				maxKey = list.get(0).getSeqsCurr() + poolSize;
//				minKey = maxKey - poolSize + 1;
//				nextKey = minKey;
//			} else {
//
//				Seqs insertBean = new Seqs();
//				Long curr = new Long((long) poolSize);
//				insertBean.setSeqsCode(keyName);
//				insertBean.setSeqsCurr(curr);
//				seqsService.insert(insertBean);
//				maxKey = 0 + poolSize;
//				minKey = maxKey - poolSize + 1;
//				nextKey = minKey;
//				return;
//			}
//
//			Seqs updateBean = new Seqs();
//			Long curr = new Long((long) nextKey);
//			updateBean.setSeqsCode(keyName);
//			updateBean.setSeqsCurr(curr);
//			seqsService.updateByPrimaryKey(updateBean);
//
//		}
//
//	}
}