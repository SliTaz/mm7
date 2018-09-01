package com.zbensoft.mmsmp.api.common.seq;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zbensoft.mmsmp.api.common.SpringBeanUtil;
import com.zbensoft.mmsmp.api.common.SystemConfigFactory;
import com.zbensoft.mmsmp.api.service.api.SeqsService;
import com.zbensoft.mmsmp.common.config.SystemConfigKey;
import com.zbensoft.mmsmp.db.domain.Seqs;

public class SequenceUtils {

	private static final Logger log = LoggerFactory.getLogger(SequenceUtils.class);

	public static ConcurrentHashMap<String, SeqVo> map = new ConcurrentHashMap<>();

	private static SeqsService seqsService = SpringBeanUtil.getBean(SeqsService.class);

	static {

		map.put(SeqDefinded.MERCHANT_USER_SEQ,
				new SeqVo(SeqDefinded.MERCHANT_USER_SEQ_MIN, SeqDefinded.MERCHANT_USER_SEQ_MAX, SeqDefinded.MERCHANT_USER_SEQ_INCREMENT, SeqDefinded.MERCHANT_USER_SEQ_FORMATLEN));

		map.put(SeqDefinded.MERCHANT_USER_STORE_TYPE_SEQ, new SeqVo(SeqDefinded.MERCHANT_USER_STORE_TYPE_SEQ_MIN, SeqDefinded.MERCHANT_USER_STORE_TYPE_SEQ_MAX, SeqDefinded.MERCHANT_USER_STORE_TYPE_SEQ_INCREMENT,
				SeqDefinded.MERCHANT_USER_STORE_TYPE_SEQ_FORMATLEN));

		map.put(SeqDefinded.MERCHANT_EMPLOYEE_SEQ,
				new SeqVo(SeqDefinded.MERCHANT_EMPLOYEE_SEQ_MIN, SeqDefinded.MERCHANT_EMPLOYEE_SEQ_MAX, SeqDefinded.MERCHANT_EMPLOYEE_SEQ_INCREMENT, SeqDefinded.MERCHANT_EMPLOYEE_SEQ_FORMATLEN));
	}

	/**
	 * 获取下一个值
	 * 
	 * @param seqName
	 *            Sequence名称
	 * @return 下一个值
	 * @throws Exception
	 */
	public synchronized static long getNextValue(String seqName) throws Exception {
		int SEQ_GET_COUNT_LIMIT = SystemConfigFactory.getInstance().getSystemConfigInt(SystemConfigKey.SEQ_GET_COUNT_LIMIT);
		int count = SEQ_GET_COUNT_LIMIT;
		long nextValue = -1;
		while (count > 0) {
			count--;
			long currValue = getCurrentValue(seqName);
			nextValue = currValue + map.get(seqName).getIncrement();
			if (nextValue > map.get(seqName).getMaxValue()) {
				nextValue = map.get(seqName).getMinValue();
			}
			
			Seqs seqs = new Seqs();
			seqs.setSeqsCode(seqName);
			seqs.setSeqsCurr(nextValue);
			seqs.setSeqsNow(currValue);

			int recordInt = seqsService.updateBySeqNumber(seqs);
			if (recordInt == 1) {
				return nextValue;
			}
		}
		throw new Exception("more than SEQ_GET_COUNT_LIMIT " + SEQ_GET_COUNT_LIMIT + " try to get seq,please check");
	}

	/**
	 * 获取下一个值
	 * 
	 * @param seqName
	 *            Sequence名称
	 * @return
	 * @throws Exception
	 */
	public synchronized static String getNextValueFormatLen(String seqName) throws Exception {
		return getFormatSeq(getNextValue(seqName), map.get(seqName).getFormatLen());
	}

	/**
	 * 获取当前值
	 * 
	 * @param seqName
	 *            Sequence名称
	 * @return 当前值
	 * @throws Exception
	 */
	public synchronized static long getCurrentValue(String seqName) throws Exception {
		int SEQ_GET_COUNT_LIMIT = SystemConfigFactory.getInstance().getSystemConfigInt(SystemConfigKey.SEQ_GET_COUNT_LIMIT);
		int count = SEQ_GET_COUNT_LIMIT;
		while (count > 0) {
			count--;
			Seqs seqs = seqsService.selectByPrimaryKey(seqName);
			if (seqs == null) {
				try {
					seqs = new Seqs();
					seqs.setSeqsCode(seqName);
					seqs.setSeqsCurr(map.get(seqName).getMinValue() - 1);
					seqsService.insert(seqs);
				} catch (Exception e) {
					log.error("", e);
				}
			} else {
				return seqs.getSeqsCurr();
			}
		}
		throw new Exception("more than SEQ_GET_COUNT_LIMIT " + SEQ_GET_COUNT_LIMIT + " try to get seq,please check");
	}

	/**
	 * 获取当前值
	 * 
	 * @param seqName
	 *            Sequence名称
	 * @return
	 * @throws Exception
	 */
	public synchronized static String getCurrentValueFormatLen(String seqName) throws Exception {
		return getFormatSeq(getCurrentValue(seqName), map.get(seqName).getFormatLen());
	}

	/**
	 * 
	 * @param value
	 * @param num
	 * @return
	 */
	private static String getFormatSeq(long value, int num) {
		String result = "";
		result = String.format("%0" + num + "d", value);
		return result;
	}

}