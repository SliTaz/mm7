package com.zbensoft.mmsmp.common.cxf;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zbensoft.mmsmp.common.config.SystemConfigKey;

/**
 * 
 * @author xieqiang
 *
 */
public enum SOAPResultCode {
	OK(1, "OK", "succ", "succ"), //
	
	PAY_LIMIT_DAY_AMOUNT_BANK_RECHARGE(6, "PAY_LIMIT_DAY_AMOUNT_BANK_RECHARGE", "Recharge amount is reach the day limit", "Recharge amount is reach the day limit"), //

	BANK_RECHARGE_REQ_BODY_IS_NULL(15, "BANK_RECHARGE_REQ_BODY_IS_NULL", "BankRechargeReqBody is null", "BankRechargeReqBody is null"), //
	BANK_PAYMENT_INFO_IS_NULL(16, "BANK_PAYMENT_INFO_IS_NULL", "BankPaymentInfo is null", "BankPaymentInfo is null"), //
	REFERENCE_NUMBER_IS_EMPTY(17, "REFERENCE_NUMBER_IS_EMPTY", "RefNo is empty", "RefNo is empty"), //
	VERSION_NUMBER_IS_EMPTY(18, "VERSION_NUMBER_IS_EMPTY", "VersionNo is empty", "VersionNo is empty"), //
	PAYMENT_TIME_IS_EMPTY(19, "VERSION_NUMBER_IS_EMPTY", "Payment time is empty", "Payment time is empty"), //

	REQUEST_IS_NULL(20, "REQUEST_IS_NULL", "Request is null", "Request is null"), //
	SERVICE_CAN_NOT_CONNECT(21, "SERVICE_CAN_NOT_CONNECT", "Service can not connect", "Service can not connect"), //
	SERVICE_RESPONSE_ERROR(22, "SERVICE_RESPONSE_ERROR", "Service response error", "Service response error"), //
	SERVICE_RESPONSE_IS_EMPTY(23, "SERVICE_RESPONSE_IS_EMPTY", "Service response is empty", "Service response is empty"), //

	PAYMENT_TIME_FORMATE_INCORRECT(24, "PAYMENT_TIME_FORMATE_INCORRECT", "payment time is incorrect", "payment time is incorrect"), //
	NEGATIVE_AMOUNT(25, "NEGATIVE_AMOUNT", "Amount can not be less than 0", "Amount can not be less than 0"), //
	VID_IS_EMPTY(26, "VID_IS_EMPTY", "VID is empty", "VID is empty"), //
	VID_FORMATE_INCORRECT(13, "VID_FORMATE_INCORRECT", "VID formate is incorrect", "VID formate is incorrect"), //
	VID_LENGTH_INCORRECT(14, "VID_LENGTH_INCORRECT", "VID length is incorrect,should 8", "VID length is incorrect,should 8"), //
	REVERSE_REQ_BODY_IS_NULL(27, "REVERSE_REQ_BODY_IS_NULL", "Reverse req body is null", "Reverse req body is null"), //
	SIGMSG_IS_EMPTY(28, "SIGMSG_IS_EMPTY", "sigMsg is empty", "sigMsg is empty"), //
	SIGMSG_IS_ERROR(29, "SIGMSG_IS_ERROR", "sigMsg is error", "sigMsg is error"), //
	AMOUNT_IS_EMPTY(30, "AMOUNT_IS_EMPTY", "Amount is empty", "Amount is empty"), //
	AMOUNT_FORMATE_INCORRECT(31, "AMOUNT_FORMATE_INCORRECT", "Amount formate incorrect", "Amount formate incorrect"), //

	PROCESS_RETURN_ERROR(32, "PROCESS_RETURN_ERROR", "process is error", "process is error"), //
	REFERENCE_NUMBER_IS_SAME_DURING_CONFIG_TIME(33, "REFERENCE_NUMBER_IS_SAME_DURING_CONFIG_TIME", "RefNo is same during config time", "RefNo is same during config time"), //
	PAY_APP_NOT_AUTH(34, "PAY_APP_NOT_AUTH", "pay App is a channel not Auth", "pay App is a channel not Auth"), //
	REFERENCE_NUMBER_LENGTH(35, "REFERENCE_NUMBER_LENGTH", "RefNo length is out limit", "RefNo length is out limit"), //
	PAY_TRADE_TYPE_NOT_EXIST(36, "PAY_TRADE_TYPE_NOT_EXIST", "trade type not exist", "trade type not exist"), //
	PAY_GATEWAY_NOTEXIST(37, "PAY_GATEWAY_NOTEXIST", "pay getway not exist", "pay getway not exist"), //
	VID_IS_NOTEXIST(38, "VID_IS_NOTEXIST", "VID is not exist", "VID is not exist"), //
	PAY_PAY_USER_DISABLE(39, "PAY_PAY_USER_DISABLE", "pay user is disable", "pay user is disable"), //
	PAY_PAY_USER_LOCKED(40, "PAY_PAY_USER_LOCKED", "pay user is locked", "pay user is locked"), //
	PAY_PAY_AMOUNT_GREATER_THEN_ZERO(41, "PAY_PAY_AMOUNT_GREATER_THEN_ZERO", "amount greater then 0", "amount greater then 0"), //
	PAY_ERROR(42, "PAY_ERROR", "pay error", "pay error"), //
	REVERSE_REFERENCE_NUMBER_IS_EMPTY(43, "REVERSE_REFERENCE_NUMBER_IS_EMPTY", "reverse RefNo is empty", "reverse RefNo is empty"), //
	REVERSE_REFERENCE_NUMBER_NOTEXIST(44, "REVERSE_REFERENCE_NUMBER_NOTEXIST", "reverse RefNo not exist", "reverse RefNo not exist"), //
	PAY_PAY_USER_NOEXIST(45, "PAY_PAY_USER_NOEXIST", "pay user not exist", "pay user not exist"), //
	PAY_ACCOUNT_AMOUNT_NOT_ENOUGH(46, "PAY_ACCOUNT_AMOUNT_NOT_ENOUGH", "pay user amount not enough", "pay user amount not enough"), //
	PAY_WEBSERVICE_REVERSE_REFNO_ISREVERSEED(47, "PAY_WEBSERVICE_REVERSE_REFNO_ISREVERSEED", "reverse refNo is reversed", "reverse refNo is reversed"), //
	PAY_WEBSERVICE_REVERSE_REFNO_ONLY_BANK_RECHARGE(48, "PAY_WEBSERVICE_REVERSE_REFNO_ONLY_BANK_RECHARGE", "reverse refNo not bank recharge", "reverse refNo not bank recharge"), //
	PAY_PAY_USER_NOTACTIVE(49, "PAY_PAY_USER_NOTACTIVE", "pay user not active", "pay user not active"), //
	PAY_PAY_USER_DEFAULT_LOGIN_PASSWORD(50, "PAY_PAY_USER_DEFAULT_LOGIN_PASSWORD", "pay user not modify login passord", "pay user not modify login passord"), //
	PAY_PAY_USER_DEFAULT_PAY_PASSWORD(51, "PAY_PAY_USER_DEFAULT_PAY_PASSWORD", "pay user not modify pay passord", "pay user not modify pay passord"), //
	BANK_ID_NOTEMPTY(52, "BANK_ID_NOTEMPTY", "bank id not empty", "bank id not empty"), //
	BANK_ID_LENGTH(53, "BANK_ID_LENGTH", "bank id length must <=4", "bank id length must <=4"), //
	VERSION_NUMBER_NOT_MATCH(54, "VERSION_NUMBER_NOT_MATCH", "VersionNo must 1.0", "VersionNo must 1.0"), //
	FRAUD_WARNING(55, "FRAUD_WARNING", "FRAUD_WARNING", "FRAUD_WARNING"), //
	FRAUD_ERROR(56, "FRAUD_ERROR", "FRAUD_ERROR", "FRAUD_ERROR"), //
	PAY_LIMIT_MONTH_AMOUNT_BANK_RECHARGE(57, "PAY_LIMIT_MONTH_AMOUNT_BANK_RECHARGE", "Recharge amount is reach the month limit", "Recharge amount is reach the month limit"), //
	PAY_ORDER_NO_CHECK_ERROR(58, "PAY_ORDER_NO_CHECK_ERROR", "check RefNo error.please try again.", "check RefNo error.please try again."), //
	PATRIMONY_CARD_CODE_NOTEMPTY(59, "PATRIMONY_CARD_CODE_NOTEMPTY", "patrimony card code not empty", "clap card code not empty"), //
	PATRIMONY_CARD_CODE_LENGTH(60, "PATRIMONY_CARD_CODE_LENGTH", "patrimony card code length must <=15", "clap card code length must <=15"), //
	PAY_PATRIMONY_CARD_CODE_NOT_MATCH(61, "PAY_PATRIMONY_CARD_CODE_NOT_MATCH", "patrimony card code not match", "clap card code not match"), //
	
	UNKNOWN(99, "SYSTEM_ERROR", "System Error", "System Error");//

	int code;
	String key;
	String reason;
	String reason_es;

	private SOAPResultCode(int code, String key, String reason, String reason_es) {
		this.code = code;
		this.key = key;
		this.reason = reason;
		this.reason_es = reason_es;
	}

	public String getReason() {
		return reason;
	}

	public String getReason_es() {
		return reason_es;
	}

	public int getCode() {
		return code;
	}

	public String getKey() {
		return key;
	}

	public String toString() {
		return String.valueOf(code);
	}

	public static SOAPResultCode getResponseStatusCode(int value) {
		for (SOAPResultCode status : SOAPResultCode.values()) {
			if (status.getCode() == value) {
				return status;
			}
		}
		return UNKNOWN;
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		List<SOAPResultCodeHelp> list = getList(SOAPResultCode.class, null);
		for (SOAPResultCodeHelp codeHelp : list) {
			if (!codeHelp.getCode().equals(SOAPResultCode.getResponseStatusCode(Integer.parseInt(codeHelp.getCode())).toString().toLowerCase())) {
				System.out.println("[WARN][CODE NOT SAME]" + codeHelp.getCode());
			}
			if (codeHelp.getKey() == null || "".equals(codeHelp.getKey())) {
				System.out.println("[WARN][KEY IS NULL]" + codeHelp.getKey());
			}
			if (codeHelp.getReason() == null || "".equals(codeHelp.getReason())) {
				System.out.println("[WARN][REASON IS NULL]" + codeHelp.getReason());
			}
			if (codeHelp.getReason_es() == null || "".equals(codeHelp.getReason_es())) {
				System.out.println("[WARN][REASON_ES IS NULL]" + codeHelp.getReason_es());
			}
		}
		System.out.println("===End==");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<SOAPResultCodeHelp> getList(Class clazz, String code) {
		List<SOAPResultCodeHelp> list = new ArrayList<SOAPResultCodeHelp>();
		try {

			if (clazz.isEnum()) {
				Object[] obs = clazz.getEnumConstants();
				for (Object ob : obs) {
					Method m = clazz.getMethod("getCode");
					Method m2 = clazz.getMethod("getKey");
					Method m3 = clazz.getMethod("getReason");
					Method m4 = clazz.getMethod("getReason_es");
					Object codeOb = m.invoke(ob);
					if (code == null || "".equals(code) || code.equals(String.valueOf(codeOb))) {
						SOAPResultCodeHelp codeHelp = new SOAPResultCodeHelp();
						codeHelp.setCode(String.valueOf(m.invoke(ob)));
						codeHelp.setKey(String.valueOf(m2.invoke(ob)));
						codeHelp.setReason(String.valueOf(m3.invoke(ob)));
						codeHelp.setReason_es(String.valueOf(m4.invoke(ob)));
						list.add(codeHelp);
					}
				}
			}
		} catch (Exception e) {
			Logger log = LoggerFactory.getLogger(SystemConfigKey.class);
			log.error("", e);
		}
		return list;
	}

}
