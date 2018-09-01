package com.zbensoft.mmsmp.sp.ra.spagent.utils;

import java.util.HashMap;
import java.util.Map;

public class Common {
	public static Map<Integer, String> ret_code = new HashMap();

	static {
		ret_code.put(Integer.valueOf(1), "PDU长度无效（大于30K或者小于0）");
		ret_code.put(Integer.valueOf(2), "解码PDU失败（缺少域或域类型无效）");
		ret_code.put(Integer.valueOf(3), "绑定的帐号无效");
		ret_code.put(Integer.valueOf(4), "绑定的密码不匹配");
		ret_code.put(Integer.valueOf(5), "业务引擎已经被绑定");
		ret_code.put(Integer.valueOf(6), "业务引擎还没有被绑定");
		ret_code.put(Integer.valueOf(7), "等待响应消息超时");
		ret_code.put(Integer.valueOf(8), "等待确认消息超时");
		ret_code.put(Integer.valueOf(100), "域值无效(not");
		ret_code.put(Integer.valueOf(101), "源端设备类型超出范围");
		ret_code.put(Integer.valueOf(102), "源端设备ID不正确");
		ret_code.put(Integer.valueOf(103), "目的端设备类型超出范围");
		ret_code.put(Integer.valueOf(104), "目的端设备ID不正确");
		ret_code.put(Integer.valueOf(105), "时间戳格式不正确");
		ret_code.put(Integer.valueOf(106), "订购用户ID无效");
		ret_code.put(Integer.valueOf(107), "MSISDN无效");
		ret_code.put(Integer.valueOf(108), "SP");
		ret_code.put(Integer.valueOf(109), "业务标识无效");
		ret_code.put(Integer.valueOf(110), "业务接入码无效");
		ret_code.put(Integer.valueOf(500), "硬盘读写错误");
		ret_code.put(Integer.valueOf(501), "网络连接不正常");
		ret_code.put(Integer.valueOf(502), "网络错误");
		ret_code.put(Integer.valueOf(503), "LICENSE文件不合法");
		ret_code.put(Integer.valueOf(504), "注册用户数目超出LICENSE限制");
		ret_code.put(Integer.valueOf(505), "系统内部错误");
		ret_code.put(Integer.valueOf(506), "数据库错误");
		ret_code.put(Integer.valueOf(600), "运营商不存在");
		ret_code.put(Integer.valueOf(601), "角色不存在");
		ret_code.put(Integer.valueOf(602), "雇员不存在");
		ret_code.put(Integer.valueOf(603), "管理员不存在");
		ret_code.put(Integer.valueOf(604), "运营商没有足够权限");
		ret_code.put(Integer.valueOf(1001), "订购用户不存在");
		ret_code.put(Integer.valueOf(1002), "订购用户状态被停止");
		ret_code.put(Integer.valueOf(1003), "订购用户欠费");
		ret_code.put(Integer.valueOf(1004), "订购用户在黑名单");
		ret_code.put(Integer.valueOf(1005), "无效用户，用户不再当前平台终");
		ret_code.put(Integer.valueOf(1100), "指定的业务不对用户开放");
		ret_code.put(Integer.valueOf(1103), "用户已注册");
		ret_code.put(Integer.valueOf(1104), "用户不存在");
		ret_code.put(Integer.valueOf(1105), "用户状态不正常");
		ret_code.put(Integer.valueOf(1106), "用户密码检验错误");
		ret_code.put(Integer.valueOf(1107), "产生伪码失败");
		ret_code.put(Integer.valueOf(1200), "订购关系已存在");
		ret_code.put(Integer.valueOf(1201), "订购关系不存在");
		ret_code.put(Integer.valueOf(1202), "订购关系状态不正常");
		ret_code.put(Integer.valueOf(1203), "同步订购关系给SP失败");
		ret_code.put(Integer.valueOf(1204), "用户不能订购两个此类业务");
		ret_code.put(Integer.valueOf(1205), "剩余的订购关系数目不足");
		ret_code.put(Integer.valueOf(1206), "ALS/Fax");
		ret_code.put(Integer.valueOf(2000), "SP不存在");
		ret_code.put(Integer.valueOf(2001), "SP状态不正常");
		ret_code.put(Integer.valueOf(2002), "SP信用度低");
		ret_code.put(Integer.valueOf(2100), "业务不存在");
		ret_code.put(Integer.valueOf(2101), "业务不开放（状态不正常）");
		ret_code.put(Integer.valueOf(2200), "没有此类型业务(SMS,");
		ret_code.put(Integer.valueOf(2201), "业务不能订购");
		ret_code.put(Integer.valueOf(2202), "剩余可用帐户不足");
		ret_code.put(Integer.valueOf(2203), "业务信用度低");
		ret_code.put(Integer.valueOf(2204), "业务URL检验失败");
		ret_code.put(Integer.valueOf(3000), "CDR格式错误");
		ret_code.put(Integer.valueOf(3001), "价格为负");
		ret_code.put(Integer.valueOf(3002), "价格格式错误");
		ret_code.put(Integer.valueOf(3003), "价格超出范围");
		ret_code.put(Integer.valueOf(3100), "用户不是一个预付费用户");
		ret_code.put(Integer.valueOf(3101), "用户余额不足");
		ret_code.put(Integer.valueOf(3102), "压缩余额失败");
		ret_code.put(Integer.valueOf(3103), "没有需要的计费信息");
		ret_code.put(Integer.valueOf(3104), "写CDR失败");
		ret_code.put(Integer.valueOf(3105), "CDR被复制");
		ret_code.put(Integer.valueOf(3106), "插入CDR进数据库失败");
		ret_code.put(Integer.valueOf(3107), "CDR价格太高");
		ret_code.put(Integer.valueOf(3108), "等候SCP响应失败");
		ret_code.put(Integer.valueOf(3109), "重新载入计费矩阵进内存失败");
		ret_code.put(Integer.valueOf(4001), "BSS内没有用户信息");
		ret_code.put(Integer.valueOf(4002), "从BSS同步用户信息失败");
		ret_code.put(Integer.valueOf(4003), "从其他管理平台同步用户信息失败");
		ret_code.put(Integer.valueOf(4004), "同步SP给其他管理平台失败");
		ret_code.put(Integer.valueOf(4005), "同步业务数据给其他管理平台失败");
		ret_code.put(Integer.valueOf(4100), "同步订购关系给SP失败");
		ret_code.put(Integer.valueOf(4200), "删除MSISDN失败");
		ret_code.put(Integer.valueOf(4201), "改变MSISDN失败");
	}

	public static class Network_ID {
		public static final String WCDMA = "WCDMA";
		public static final String GSM = "GSM";
	}

	public static class OA_DA_Type {
		public static final Integer MSISDN = Integer.valueOf(1);
		public static final Integer Pseudo_Code = Integer.valueOf(2);
		public static final Integer IMSI = Integer.valueOf(3);
		public static final Integer INVALID = Integer.valueOf(-1);
		public static final Integer SERVICE = Integer.valueOf(0);
	}

	public static class TAG {
		public static final Short LinkID = Short.valueOf((short) 1);
		public static final Short MediaType = Short.valueOf((short) 2);
		public static final Short ClientIP = Short.valueOf((short) 3);
		public static final Short Amount = Short.valueOf((short) 4);
		public static final Short Pk_total = Short.valueOf((short) 5);
		public static final Short Pk_number = Short.valueOf((short) 6);
		public static final Short SMSFormat = Short.valueOf((short) 7);
		public static final Short SMSContentLen = Short.valueOf((short) 8);
		public static final Short SMSContent = Short.valueOf((short) 9);
		public static final Short SPDealResult = Short.valueOf((short) 10);
		public static final Short SessionID = Short.valueOf((short) 4609);
		public static final Short FeeType = Short.valueOf((short) 4610);
		public static final Short FeeCode = Short.valueOf((short) 5123);
		public static final Short FixedFee = Short.valueOf((short) 4612);
		public static final Short SessionEnd = Short.valueOf((short) 4613);
		public static final Short ServiceType = Short.valueOf((short) 4615);
		public static final Short NeedToNextNode = Short.valueOf((short) 4616);
		public static final Short OriginalDA = Short.valueOf((short) 4617);
		public static final Short Transcoding = Short.valueOf((short) 4618);
		public static final Short ErrCode = Short.valueOf((short) 5121);
		public static final Short ReturnMessage = Short.valueOf((short) 5377);
		public static final Short SeviceStatues = Short.valueOf((short) 5378);
		public static final Short UserStatus = Short.valueOf((short) 5379);
		public static final Short Redirect = Short.valueOf((short) 5380);
		public static final Short DestAddrList = Short.valueOf((short) 5382);
		public static final Short PushId = Short.valueOf((short) 5383);
		public static final Short Fee = Short.valueOf((short) 5384);
		public static final Short CPID = Short.valueOf((short) 5385);
		public static final Short ContentID = Short.valueOf((short) 5392);
		public static final Short OrderMethod = Short.valueOf((short) 5393);
		public static final Short SP_ProductID = Short.valueOf((short) 5394);
		public static final Short SPEC_ProductID = Short.valueOf((short) 5395);
		public static final Short ProductID = Short.valueOf((short) 5396);
		public static final Short AccessNo = Short.valueOf((short) 5397);
	}
}
