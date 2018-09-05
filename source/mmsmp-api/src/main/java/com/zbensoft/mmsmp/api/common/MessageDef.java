package com.zbensoft.mmsmp.api.common;

public interface MessageDef {

	public interface MENU_TYPE {
		/** 1：菜单 **/
		public static int MENU = 1;
		/** 2：功能 **/
		public static int FUNCTION = 2;

	}

	public interface COUPON_STATUS {
		/** 0：未使用 **/
		public static int UNUSE = 0;
		/** 1：已使用 **/
		public static int USE = 1;
		/** 2：已过期 **/
		public static int EXPAIN = 2;
	}
	
	public interface COUPON_READ {
		public static int IS_NOT_READ = 0;
		public static int IS_READ = 1;
	}
	
	public interface IS_GAS_STATION {
		public static int NO = 0;
		public static int YES = 1;
	}


	public interface COUPON_TYPE {
		/** 1:购买券 **/
		public static int BUY = 1;
		/** 1:购买券Str **/
		public static String BUY_STRING = "1";
		/** 2:分发券 **/
		public static int GENERATE = 2;
		/** 2:分发券Str **/
		public static String GENERATE_STRING = "2";

	}

	public interface STATUS {
		/** 0 启用 **/
		public static String ENABLE_STRING = "0";
		/** 1 停用 **/
		public static String DISABLE_STRING = "1";

		/** 0 启用 **/
		public static int ENABLE_INT = 0;
		/** 1 停用 **/
		public static int DISABLE_INT = 1;
	}
	public interface CONSUMER_USER_CLAP_STATUS {

		/** 1：正常 **/
		public static int NORMAL = 1;
		/** 2：丢失 **/
		public static int LOST = 2;
		/** 3：锁定 **/
		public static int LOCKED = 3;
	}

	public interface PAY_APP_STATUS {
		/** 1 启用 **/
		public static String ENABLE_STRING = "1";
		/** 0 停用 **/
		public static String DISABLE_STRING = "0";

		/** 1 启用 **/
		public static int ENABLE_INT = 1;
		/** 0 停用 **/
		public static int DISABLE_INT = 0;
	}

	public interface PAY_GATEWAY_STATUS {
		/** 1 启用 **/
		public static String ENABLE_STRING = "1";
		/** 0 停用 **/
		public static String DISABLE_STRING = "0";

		/** 1 启用 **/
		public static int ENABLE_INT = 1;
		/** 0 停用 **/
		public static int DISABLE_INT = 0;
	}

	public interface PAY_CALC_PRICE_STATUS {
		/** 1启用 **/
		public static int ENABLE_INT = 1;
		/** 0 停用 **/
		public static int DISABLE_INT = 0;
	}

	public interface VIEW {
		/** 1：显示 **/
		public static int VIEW = 1;
		/** 0：不显示 **/
		public static int UNVIEW = 0;
	}

	public interface LOCKED {
		/** 未锁定 **/
		public static int UNLOCKED = 0;
		/** 锁定 **/
		public static int LOCKED = 1;
	}

	public interface FIRST_LOGIN {
		/** 1：是 **/
		public static int FIRST = 1;
		/** 2：否 **/
		public static int NOT_FIRST = 2;
	}

	public interface DEFAULT_LOGIN_PASSWORD {
		/** 1：是 **/
		public static int DEFUALT = 1;
		/** 2：否 **/
		public static int NOT_DEFUALT = 2;
	}

	public interface DEFAULT_PAY_PASSWORD {
		/** 1：是 **/
		public static int DEFUALT = 1;
		/** 2：否 **/
		public static int NOT_DEFUALT = 2;
	}

	public interface TRADE_TYPE {
		/** 1：充值 **/
		public static int RECHARGE = 1;
		/** 2：提现 **/
		public static int CHARGE = 2;
		/** 3：转账 **/
		public static int TRANSFER = 3;
		/** 4：消费（收款） **/
		public static int CONSUMPTION = 4;
		/** 5：转账到银行卡 **/
		public static int TRANSFER_BANK = 5;
		/** 11：银行充值 **/
		public static int BANK_RECHARGE = 11;
		/** 12：银行取消 **/
		public static int BANK_REVERSE = 12;
		/** 13：消费退款 **/
		public static int REFUND = 13;
		/** 14：石油交易 **/
		public static int GAS_CONSUPTION = 14;
	}

	public interface TRADE_STATUS {
		/** 1：进行中 **/
		public static int PROCESSING = 1;
		/** 2：未付款 **/
		public static int UNPAID = 2;
		/** 3：退款中 **/
		public static int REFUNDING = 3;
		/** 4：退款成功 **/
		public static int REFUND_succ = 4;
		/** 5：退款成功 **/
		public static int REFUND_fail = 5;
		/** 6：成功 **/
		public static int SUCC = 6;
		/** 7：失败 **/
		public static int FAIL = 7;
	}

	public interface TRADE_HAVE_REFUND {
		/** 0：无 **/
		public static int NO = 0;
		/** 1：有 **/
		public static int YES = 1;
	}

	public interface TRADE_CLOSE {
		/** 未关闭 **/
		public static int UNCLOSE = 0;
		/** 已关闭 **/
		public static int CLOSE = 1;
	}

	public interface BORROW_LOAN {
		/** 借 :减少 **/
		public static int BORROW = 0;
		/** 贷 ：增加 **/
		public static int LOAN = 1;
	}

	public interface TRADE_PAY_GETWAY_TYPE {
		// /** 1：快捷支付 **/
		// public static int QUICK_PAYMENT = 1;
		// /** 2：网银 **/
		// public static int CYBER_BANK = 2;
		/** 3：账号余额 **/
		public static int ACCOUNT_NUMBER = 3;

		/** 11：银行充值 **/
		public static int BANK_RECHARGE = 11;
		/** 12：银行充值取消 **/
		public static int BANK_REVERSE = 12;

		/** 51：epay充值 **/
		public static int EPAY_RECHARGE = 51;
		/** 52：epay提现 **/
		public static int EPAY_CHARGE = 52;
	}

	public interface BANK_TYPE {
		/** 1：储蓄卡 **/
		public static int SAVINGS_CARD = 1;
		/** 2：信用卡 **/
		public static int CREDIT_CARD = 2;
	}

	public interface DELETE_FLAG {
		/** 0：未删除 **/
		public static int UNDELETE = 0;
		/** 1：已删除 **/
		public static int DELETED = 1;
		/** 0：未删除 **/
		public static String UNDELETE_STRING = "0";
		/** 1：已删除 **/
		public static String DELETED_STRING = "1";
	}

	public interface USER_TYPE {
		/** 10：消费用户 **/
		public static int CONSUMER = 10;
		/** 20：商户 **/
		public static int MERCHANT = 20;
		/** 80：政府用户 **/
		public static int GOV = 80;
		/** 90：管理用户 **/
		public static int SYS = 90;

		/** 10：消费用户 **/
		public static String CONSUMER_STRING = "10";
		/** 20：商户 provide **/
		public static String MERCHANT_STRING = "20";
		/** 30：员工  user**/
		public static String MERCHANT_EMPLOYEE_STRING = "30";
		/** 80：政府用户 **/
		public static String GOV_STRING = "80";
		/** 90：管理用户 **/
		public static String SYS_STRING = "90";

		/** 11：消费用户-app登录使用 **/
		public static String CONSUMER_STRING_APP_LOGIN = "11";
		/** 21：商户-app登录使用 **/
		public static String MERCHANT_STRING_APP_LOGIN = "21";
//		/** 登陆分隔符 **/
//		public static String DELIMITER = "#";
	}

	public interface LOGIN {
		public static String TOKEN_HEADER = "Authorization";
		// public static String SECRET = "mySecret";
		// public static Long EXPIRATION = 3600l;// 秒
		public static String TOKEN_HEAD = "zbtoken.";

	}

	public interface UPLOAD_PATH {
		public static String CONSUMER = "consumer";
		public static String MERCHANT = "merchant";
		public static String MERCHANT_EMPLOYEE = "merchantEmployee";
		public static String GOV = "gov";
		public static String SYS = "sys";

	}

	public interface PLATFORM_TYPE {
		/** 1：android **/
		public static int ANDROID = 1;
		/** 2：ios **/
		public static int IOS = 2;
	}

	public interface PUBLISH {
		/** 0：未发布 **/
		public static int UNPUBLISH = 0;
		/** 1：已发布 **/
		public static int PUBLISH = 1;
	}
	
	public interface BLACK_WHITE_LIST_TYPE {
		/** 1：黑名单 **/
		public static int BLACK = 1;
		/** 2：白名单**/
		public static int WHITE = 2;
	}

	public interface NOTICE_STATUE {
		/** 1：编辑中 **/
		public static int EDIT = 1;
		/** 2：已发布 **/
		public static int PUBLISH = 2;
		/** 3：已撤销 **/
		public static int CANCEL = 3;
	}

	public interface NOTICE_VIEW_TYPE {

		/** 10：消费用户 **/
		public static String CONSUMER_STRING = "10";
		/** 20：商户 **/
		public static String MERCHANT_STRING = "20";
		/** 80：政府用户 **/
		public static String GOV_STRING = "80";
		/** 90：管理用户 **/
		public static String SYS_STRING = "90";
		/** 99：app **/
		public static String APP_STRING = "99";
	}

	public interface GET_TYPE {

		/** 1：领用 **/
		public static int RECEIVE = 1;
		/** 2：发放 **/
		public static int GRANT = 2;
	}

	public interface GET_LIMIT {

		/** 0：个人 **/
		public static int CONSUMER = 0;
		/** 1：家庭 **/
		public static int FAMILY = 1;
		/** 3：石油 **/
		public static int GAS = 3;
		/** 1：家庭 **/
		public static int STORE_FAMILY = 2;
		/** 0：个人 **/
		public static String CONSUMER_STRING = "0";
		/** 1：家庭 **/
		public static String FAMILY_STRING = "1";
		/** 1：家庭 **/
		public static String STORE_FAMILY_STRING = "2";
	}

	public interface GRANT_TYPE {

		/** 1：已发放 **/
		public static int GRANT = 1;
		/** 0：未发放 **/
		public static int UNGRANT = 0;
	}



	public interface RECONCILIATION_TYPE {

		/** 1：银行 **/
		public static int BANK = 1;
		/** 2：商户 **/
		public static int MERCHANT = 2;
		/** 3：消费用户 **/
		public static int CONSUMER = 3;
	}

	// public interface GATEWAY_ID {
	// public static String ACCOUNT_NUMBER = "1";
	//
	// }

	public interface CONSUMER_IS_ACTIVE {

		/** 0：未激活 **/
		public static int UNACTIVATE = 0;
		/** 1：激活 **/
		public static int ACTIVATE = 1;
	}

	public interface ERROR_HANDLING_REASON {
		/** 1：银行无信息 **/
		public static int BANK_NO_INFO = 1;
		/** 2：Epay无信息 **/
		public static int EPAY_NO_INFO = 2;
		/** 3：金额不对 **/
		public static int AMOUNT_NOT_MATCH = 3;
		/** 4：时间窗口数据 **/
		public static int TIME_WINDOW = 4;
		/** 5：REVERSE **/
		public static int REVERSE = 5;
	}

	public interface ERROR_HANDLING_RESULT {
		/** 0：未处理 **/
		public static int UNHANDLING = 0;
		/** 1:扣款 **/
		public static int PAY_AMOUNT = 1;
		/** 2：补款 **/
		public static int RECV_AMOUNT = 2;
		/** 3：不处理 **/
		public static int NOT_NEED_HANDLING = 3;
		/** 4：手动处理 **/
		public static int MANUAL_HANDLING = 4;
	}

	public interface RECONCILIATION_BANK_STATUS {
		/** 0：未开始 **/
		public static int UN_START = 0;
		/** 1：已下载 **/
		public static int FTP_DOWNLOAD = 1;
		/** 2:银行已入库 **/
		public static int BANK_TO_DB = 2;
		/** 3:交易已入库 **/
		public static int EPAY_TO_DB = 3;
		/** 4:对账结束 **/
		public static int END = 4;
	}

	public interface TRADE_ERROR_CODE {
		/** 1:银行处理失败 **/
		public static int BANK_PROCESS_FAIL = 1;

		/** 10:提现，银行对账记录不存在 **/
		public static int CHARGE_BANK_NO_RECORD = 10;
		/** 11:提现，epay对账记录不存在 **/
		public static int CHARGE_EPAY_NO_RECORD = 11;
		/** 12:提现，提现结果失败 **/
		public static int CHARGE_EPAY_RESULT_FAILED = 12;
	}

	public interface ALARM_STATUS {
		/** 1:未处理 **/
		public static int UN_HANDLING = 1;
		/** 2:已处理 **/
		public static int HANDLED = 1;
	}

	public interface BUYER_STATUS {
		/** 1:激活 **/
		public static String ACTIVE = "1";
		/** 2:销户 **/
		public static String INACTIVATED = "2";
	}

	public interface USER_SEX {
		/** 1:女性 Female **/
		public static int FEMALE = 1;
		/** 2:男性 Male **/
		public static int MALE = 2;
	}

	public interface CLAP_DISTRIBUTION {
		/** 1：新增 **/
		public static String ADD = "1";
		/** 2：更新 **/
		public static String UPDATE = "2";
		/** 3: 取消 **/
		public static String NULLED = "3";
	}
	
	public interface CLAP_SELLER_ACCOUNT {
		/** 1：新增 **/
		public static String ADD = "1";
		/** 2：更新 **/
		public static String UPDATE = "2";
		/** 3: 删除 **/
		public static String DELETE = "3";
	}

	public interface CLAP_AMOUNT {
		/** 1：长度 **/
		public static int LENGTH = 17;
		/** 2：小数位 **/
		public static int DECIMAL = 2;
	}

	public interface CHARGE_AMOUNT {
		/** 1：长度 **/
		public static int LENGTH = 18;
		/** 2：小数位 **/
		public static int DECIMAL = 2;
	}

	public interface BANK_ACCOUNT_TYPE {
		/** 00：常用账户 **/
		public static String CURRENT_ACCOUNT = "00";
		/** 01：储蓄账户 **/
		public static String SAVINGS_ACCOUNT = "01";
	}

	public interface BANK_AMOUNT {
		/** 00：长度 **/
		public static int LENGTH = 18;
		/** 01：小数位 **/
		public static int DECIMAL = 2;
	}

	public interface BANK_PAYMENT_TYPE {
		/** 00：其他银行 **/
		public static String OTHER_BANK = "00";
		/** 01：付款账户银行（委内瑞拉） **/
		public static String SAME_BANK = "10";
		/** 01：人工检查 **/
		public static String MANAGE_CHECK = "20";
	}

	public interface CHARGE_FIXED_VALUE {
		/** VEF：批次 currency **/
		public static String BATCH_CURRENCY = "VEF";
		/** 01：付款账户银行（委内瑞拉） **/
		public static String BATCH_TYPE_OF_PAYMENT = "40";
	}

	public interface CHARGE_ERROR_HANDLING_REASON {
		/** 1：银行无信息 **/
		public static int BANK_NO_INFO = 1;
		/** 2：Epay无信息 **/
		public static int EPAY_NO_INFO = 2;
		/** 3：银行结果不一致 **/
		public static int RESULT_NOT_MATCH = 3;

	}

	public interface CHARGE_ERROR_HANDLING_RESULT {
		/** 0：未处理 **/
		public static int UNHANDLING = 0;
		/** 1:扣款 **/
		public static int PAY_AMOUNT = 1;
		/** 2：补款 **/
		public static int RECV_AMOUNT = 2;
		/** 3：不处理 **/
		public static int NOT_NEED_HANDLING = 3;
		/** 4：手动处理 **/
		public static int MANUAL_HANDLING = 4;
	}

	public interface CHARGE_CREDITO_RESULT {

		/** VE3：资金不足 **/
		public static String LACK_OF_FUNDS = "VE3";
		/** VE6：成功 **/
		public static String SUCCESS = "VE6";
		/** VE8：被SIP或主机拒绝 **/
		public static String REJECTED_BY_SIP = "VE8";
		/** VV3：拒绝 **/
		public static String CREDIT_REJECTED = "VV3";
		/** VV8：被SIP或主机拒绝 **/
		public static String REJECTED_BY_HOST = "VV8";
		/** VE91：检查处理 **/
		public static String SUCCESS_BUT_PROCESS = "VE91";
		
		
		/** VE3Int：资金不足 **/
		public static int LACK_OF_FUNDS_INT =  703;
		/** VE6Int：成功 **/
		public static int SUCCESS_INT = 706;
		/** VE8Int：被SIP或主机拒绝 **/
		public static int REJECTED_BY_SIP_INT = 708;
		/** VV3Int：拒绝 **/
		public static int CREDIT_REJECTED_INT = 723;
		/** VV8Int：被SIP或主机拒绝 **/
		public static int REJECTED_BY_HOST_INT = 728;
		/** VE91Int：检查处理 **/
		public static int SUCCESS_BUT_PROCESS_INT = 7091;
	}

	public interface CHARGE_HEADER_RESULT {
		/** VL2: 成功 **/
		public static String SUCCESS = "VL2";
		/** VL3: 拒绝 **/
		public static String ORDER_REJECTED = "VL3";
		/** VL32: 客户端撤销 **/
		public static String CUSTOMER_SUSPENDED = "VL32";
		
		/** VL2Int: 成功 **/
		public static int SUCCESS_INT = 712;
		/** VL3Int: 拒绝 **/
		public static int ORDER_REJECTED_INT = 713;
		/** VL32Int: 客户端撤销 **/
		public static int CUSTOMER_SUSPENDED_INT = 7132;
	}

	public interface CHARGE_DEBITO_RESULT {
		/** VE3：资金不足 **/
		public static String LACK_OF_FUNDS = "VE3";
		/** VE3Int：资金不足 **/
		public static int LACK_OF_FUNDS_INT = 703;
		/** VE6：成功 **/
		public static String SUCCESS = "VE6";
		/** VE6Int：成功 **/
		public static int SUCCESS_INT = 706;
		/** VE8：被SIP或主机拒绝 **/
		public static String REJECTED_BY_SIP = "VE8";
		/** VE8Int：被SIP或主机拒绝 **/
		public static int REJECTED_BY_SIP_INT = 708;
		/** VE91：检查处理 **/
		public static String SUCCESS_BUT_PROCESS = "VE91";
		/** VE91Int：检查处理 **/
		public static int SUCCESS_BUT_PROCESS_INT = 7091;
		/** VL3: 拒绝 **/
		public static String ORDER_REJECTED = "VL3";
		/** VL3Int: 拒绝 **/
		public static int ORDER_REJECTED_INT = 713;
		/** VV8：被SIP或主机拒绝 **/
		public static String REJECTED_BY_HOST = "VV8";
		/** VV8Int：被SIP或主机拒绝 **/
		public static int REJECTED_BY_HOST_INT = 728;
		/** VV8：被SIP或主机拒绝 **/
		public static String UNKNOW = "VE99";
		/** VV8Int：被SIP或主机拒绝 **/
		public static int UNKNOW_INT = 7099;
		
	}

	public interface BANK_CARD_BIND_STATUS {
		/** 0：未绑定 **/
		public static int UN_BIND = 0;
		/** 1：已绑定 **/
		public static int BIND = 1;
		/** 2：不提醒 **/
		public static int NOT_REMIND = 2;

	}
	public interface CLAP_BIND_STATUS {
		/** 0：未绑定 **/
		public static int UN_BIND = 0;
		/** 1：已绑定 **/
		public static int BIND = 1;
		/** 2：不提醒 **/
		public static int NOT_REMIND = 2;

	}
	public interface EMAIL_BIND_STATUS {
		/** 0：未绑定 **/
		public static int UN_BIND = 0;
		/** 1：已绑定 **/
		public static int BIND = 1;
		/** 2：不提醒 **/
		public static int NOT_REMIND = 2;

	}

	public interface PASSWORD_TYPE {
		/** 1：登录密码 **/
		public static String LOGIN_PASSORD = "1";
		/** 2：支付密码 **/
		public static String PAY_PASSWORD = "2";

	}

	public interface APP_USER_TYPE {
		/** 1：买家app **/
		public static int BUYER_APP = 1;
		/** 2：卖家app **/
		public static int SELLER_APP = 2;

	}
	public interface APP_PLATFORM_TYPE {
		/** 1：Android **/
		public static int ANDROID = 1;
		/** 2：苹果 **/
		public static int IOS = 2;

	}
	
	public interface IMAGETYPE {
		/** JPG：jpg **/
		public static String JPG = "jpg";
		/** JPEG2:jpeg **/
		public static String JPEG ="jpeg";
		/** PNG：png **/
		public static String PNG = "png";
		/** GIF：gif **/
		public static String GIF = "gif";
		/** BMP：bmp **/
		public static String BMP = "bmp";
		/** UNKNOW：unknow **/
		public static String UNKNOW = "unknow";

	}
	
	public interface CLAP_WEBSERVICE {
		public static String NORMAL =  "0";
		public static String IS_DOWN =  "1";
	}
	
	public interface SELLER_LOGIN_TYPE {
		/** 1：provide **/
		public static String PROVIDE = "1";
		/** 2：office **/
		public static String OFFICE = "2";
		/** 3：user **/
		public static String USER = "3";
	}
	
	public interface EMPLOYEE_STORE_TYPE {
		public static int CLAP = 1;
		public static int CHAIN_STORES = 2;
		public static int HEAD_COMPANY = 3;
		public static int OFFICE = 4;
		public static int STORE = 5;
		public static int PERSON = 6;
		
	}
	
	public interface EMPLOYEE_TYPE {
		public static int STORE = 1;
		public static int SUPERE_EMPLOYEE = 2;
		public static int EMPLOYEE = 3;

	}
	
	public interface PROVIDE_TYPE {
		/** 1：public govment**/
		public static int PUBLIC_GOVMENT = 1;
		/** 2：private company **/
		public static int PRIVATE_COMPANY = 2;
		/** 3：Natural person **/
		public static int NATURAL_PERSON = 3;
	}
	
	public interface STORE_TYPE{
		/**1：clap(删除) **/
		public static int CLAP = 1;
		/**2：连锁店**/
		public static int FRANCHISE = 2;
		/**3：总公司**/
		public static int MAIN_OFFICE = 3;
		/**4：办公室**/
		public static int SUB_OFFICE = 4;
		/**5：店**/
		public static int STORE = 5;
		/**6：私人店（注册选择的私人店）**/
		public static int PERSONAL_PROVIDER = 6;
	}
	
	
	
	public interface REGISTER_TYPE {
		/** 1:provide **/
		public static String PROVIDE = "1";
		/** 2:office **/
		public static String OFFICE = "2";
		/** 3:employee **/
		public static String EMPLOYEE = "3";
		/** 4: clap**/
		public static String CLAP = "4";
		/** 5: person**/
		public static String PERSON = "5";
	}
	public interface CLAP {
		/** 0:不是clap 默认值 **/
		public static int NO_CLAP = 0;
		/** 1:是clap **/
		public static int IS_CLAP = 1;
	}
	public interface GasStation {
		/** 0:不是加油站 默认值 **/
		public static int NO_GasStation = 0;
		/** 1:是加油站 **/
		public static int IS_GasStation = 1;
	}
	
	public interface MERCHANT_EMPLOYEE_ISACTIVE {
		/** 0:未激活 **/
		public static int INACTIVATED = 0;
		/** 1:激活 **/
		public static int ACTIVE = 1;
	}
	
	public interface MERCHANT_EMPLOYEE_ROLE {
		/** 1:CONSUMPTION **/
		public static String EMPLOYEE_CONSUMPTION = "1";
		/** 2:EMPLOYEE **/
		public static String EMPLOYEE_EMPLOYEE = "2";
		/** 3:EMPLOYEE **/
		public static String EMPLOYEE_CONSUMER_ORDER="3";
		/** 4:EMPLOYEE **/
		public static String EMPLOYEE_CONSUMER_RECHARGE="4";
		/** 5:EMPLOYEE **/
		public static String EMPLOYEE_CONSUMER_PASSWORD="5";
	}
	public interface MERCHANT_EMPLOYEE_TYPE{
		/** 1:Clap Store **/
		public static int STORE = 1;
		/** 2:clap超级员工 **/
		public static int SUPER_EMPLOYEE = 2;
		/** 3:员工 **/
		public static int EMPLOYEE = 3;
		
	}
	
	public interface MERCHANT_USER_STORE_TYPE_STATUS{
		/** 1:授权(boss/gov) **/
		public static int GRANT = 1;
		/** 2:启用(merchant)**/
		public static int ENABLE = 2;
		/** 3:停用 **/
		public static int DISABLE = 3;
		
	}
	
	public interface FIRST_SYN{
		/** 0:no_first_syn**/
		public static int NO_FIRST_SYN = 0;
		/** 1:IS_FIRST_SYN **/
		public static int IS_FIRST_SYN = 1;
		
	}
	
	//登录类型
	public interface MERCHANT_LOGIN_TYPE {
		/** 1:provide **/
		public static int PROVIDE = 1;
		/** 2:office **/
		public static int OFFICE = 2;
		/** 3:employee **/
		public static int EMPLOYEE = 3;
		/** 4: clap**/
		public static int CLAP = 4;
		/** 5: person**/
		public static int PERSON = 5;
		
		/** 1:provide **/
		public static String PROVIDE_STR = "1";
		/** 2:office **/
		public static String OFFICE_STR = "2";
		/** 3:employee **/
		public static String EMPLOYEE_STR = "3";
		/** 4: clap**/
		public static String CLAP_STR = "4";
		/** 5: person**/
		public static String PERSON_STR = "5";
	}
	
	public interface PROVIDER_HAS_MAINOFFICE{
		/** 1:yes **/
		public static int YES = 1;
		/** 0:no **/
		public static int NO = 0;
	}
	public interface JWTUSER_TYPE_NO{
		/** 0:no **/
		public static int PROVIDERT_TYPE_NO = 0;
		/** 0:no **/
		public static int STORE_TYPE_NO = 0;
		/** 0:no **/
		public static int EMPLOYEE_TYPE_NO = 0;
	}
	
		
	public interface SELLER_CDLP_WS_RESPONSE_CODE{
		/** 3000:更新成功 **/
		public static String UPDATE_SUCCESS = "3000";
		/** 1000:查询成功 **/
		public static String QUERY_SUCCESS = "1000";
		/** 5000:查询成功 **/
		public static String INSERT_SUCCESS = "5000";
	}
	public interface MERCHANT_BANK_CARD_NUMBER{
		/** provide**/
		public static int PROVIDE_NUMBER = 100;
		/** personal **/
		public static int PERSONAL_NUMBER = 3;
		
	}
	
	public interface DAILYBILL_STATUS{
		/** 0:未完成**/
		public static int NOT_FINISH = 0;
		/** 1:已完成 **/
		public static int FINISH = 1;

	}
	
	public interface DAILYBILL_TASK_STATUS{
		/** 1:未开始**/
		public static int NOT_START = 1;
		/** 2:处理中 **/
		public static int PROCESSING = 2;
		/** 3:部分出错 **/
		public static int SOME_ERROR = 3;
		/** 4:全部完成**/
		public static int FINISH = 4;

	}

	public interface COUPON_TYPE_PREFIX{
		public static String CONSUMER_COUPON = "70_";
		public static String FAMILY_COUPON = "71_";
	}
	public interface COUPON_COUNT{
		/** 1:默认值**/
		public static int DEFAULT = 1;
		
	}
	public interface IS_READ{
		/** 0:未读**/
		public static int NO_READ = 0;
		/** 1:已读**/
		public static int READ = 1;
		
	}
	public interface AMOUNT_STATUS {

		/** 1：使用**/
		public static int AMOUNT_STATUS_YES = 1;
		/** 0：未使用 **/
		public static int AMOUNT_STATUS_NO = 0;
	}
	public interface GAS_STATUS {
		
		/** 0：未使用 **/
		public static int GAS_UNUSED = 0;
		/** 1：已使用**/
		public static int GAS_USED = 1;
		/** 2：已过期**/
		public static int GAS_EXPIRED = 2;
	}

}
