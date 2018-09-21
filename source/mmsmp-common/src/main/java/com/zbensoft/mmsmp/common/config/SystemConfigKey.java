package com.zbensoft.mmsmp.common.config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * 系统配置处理
 * common:共通
 * alarm：告警
 * log:日志
 * 
 * 
 * </pre>
 * 
 * @author xieqiang
 * 
 */
public enum SystemConfigKey {
	COMMON_SERVER_CODE("server_code", "01", "server code", "服务器代码，用于生成ID", ""), //
	COMMON_MAINTEN("common_mainten", "0", "mainten flag.0:NO 1:YES", "维护标识，0：没有维护，1：正在维护", ""), //

	// ALARM_TO_EMAIL("alarm_to_email", "list.cantv.epay.mainten@zbensoft.com", "send alarm to the emailAddress", "接收邮件的地址，逗号隔开", ""), //
	ALARM_MAIN("alarm_main", "1", "alarm main", "每个机器都能检测到的机器，由main=1的来告警", ""), //
	// ALARM_TO_EMAIL("alarm_to_email", "list.cantv.epay.mainten@zbensoft.com", "send alarm to the emailAddress", "接收邮件的地址，逗号隔开", ""), //
	// ALARM_SEND_NAME("alarm_send_name", "Alarm-cantv-epay", "send user Name", "发送邮件的名称，用于接收显示比较明确为哪里发的", ""), //
	ALARM_CPU("alarm_cpu", "90", "max cpu use x% are alarm", "cpu使用率>=x%进行告警", ""), //
	ALARM_DISK("alarm_disk", "90", "disk use x% are alarm", "硬盘使用率>=x%进行告警", ""), //
	ALARM_IO("alarm_io", "90", "io use x% are alarm", "硬盘IO使用率>=x%进行告警", ""), //
	ALARM_MEM("alarm_mem", "90", "memory use x% are alarm", "内存使用率>=x%进行告警", ""), //
	ALARM_NET("alarm_net", "90", "net use x% are alarm", "网络使用率>=x%进行告警", ""), //
	ALARM_NET_BAND_WIDTH("alarm_net_band_width", "1000", "net ubank width", "网络带宽 1000=千兆网卡", ""), //
	ALARM_THREAD_TIME_SEC("alarm_thread_time_sec", "60", "alarm thread how time process one time", "告警线程多久执行一次", ""), //
	ALARM_LICENSE_USER_QUANTITY("alarm_license_user_quantity", "5500000", "user quantiy for license,only for alarm", "用户到达多少进行告警", ""), //
	ALARM_REQUEST_COUNT("alarm_request_count", "10000", "alarm request count", "总共请求达到多少进行告警", ""), //
	ALARM_REQUEST_COUNT_SUCC("alarm_request_count_succ", "10000", "alarm request succ count", "总共请求成功达到多少进行告警", ""), // 12000

	JOB_RECONCLIATION_BATCH_TO_DB_COUNT("job_reconcliation_batch_to_db_count", "100", "reconciliation job:batch to db count", "对账任务：每批次入库数", ""), //
	JOB_RECONCLIATION_ALL_BANK_DATE("job_reconcliation_all_bank_date", "1900-01-01", "job_reconcliation_all_bank_date yyyy-MM-dd", "对账任务：日期 yyyy-MM-dd", ""), //
	JOB_RECONCLIATION_ALL_BANK_BANK_ID("job_reconcliation_all_bank_bank_id", "1", "job_reconcliation_all_bank_bank_id", "对账任务：银行ID", ""), //
	JOB_RECONCLIATION_ALL_BANK_FILE_PATH("job_reconcliation_all_bank_file_path", "/data/master/zben/file/reconciliation/", "job_reconcliation_all_bank_file_path", "对账任务：对账文件目录", ""), //
	JOB_RECONCLIATION_ALL_BANK_BANK_FILE_PATH("job_reconcliation_all_bank_bank_file_path", "/data/master/sftp/", "job_reconcliation_all_bank_bank_file_path", "对账任务：对账文件目录", ""), //
	
	JOB_BILL_DALIY_SELLER_GET_USER_COUNT("job_bill_daliy_seller_get_user_count", "100", "job_bill_daliy_seller_get_user_count", "卖家日账单任务：每次取多少商户", ""), //
	JOB_BILL_MONTHLY_BUYER_GET_USER_COUNT("job_bill_monthly_buyer_get_user_count", "100", "job_bill_monthly_buyer_get_user_count", "买家月账单任务：每次取多少买家", ""), //
	JOB_BILL_DALIY_EMPLOYEE_GET_USER_COUNT("job_bill_daliy_employee_get_user_count", "100", "job_bill_daliy_employee_get_user_count", "员工日账单任务：每次取多少员工", ""), //
	JOB_BILL_DAILY_SELLER_ERROR_DATE("job_bill_daily_seller_error_date", "1900-01-01", "JOB_BILL_DAILY_SELLER_ERROR_DATE yyyy-MM-dd", "日账单错误处理：日期 yyyy-MM-dd", ""), //
	JOB_BILL_DAILY_SELLER_ERROR_USER_LIST("job_bill_daily_seller_error_user_list", "000001,00002", "error user list split by ',' ", "错误用户列表 ',' ", ""), //
	JOB_BILL_DAILY_SELLER_STAT_EMAIL_SUBJECT("job_bill_daily_seller_stat_email_subject", "Billetera Movil - Estadistica de facturacion diaria", "subject of statistics email", "日账单统计邮件主题", ""), //
	JOB_BILL_DAILY_SELLER_STAT_EMAIL_TO("job_bill_daily_seller_stat_email_to", "yofren.hernandez@zte.com.cn,cesar.alvarel@zte.com.cn", "job_bill_daily_seller_stat_email_to", "日账单统计-发送邮件,收件人", ""), //
	JOB_BILL_DAILY_SELLER_STAT_EMAIL_BCC("job_bill_daily_seller_stat_email_bcc", "list.cantv.epay.mainten@zbensoft.com", "job_bill_daily_seller_stat_email_bcc", "日账单统计-发送邮件,暗抄送人", ""), //
	JOB_BILL_DAILY_SELLER_STAT_EMAIL_ENABLE("job_bill_daily_seller_stat_email_enable", "0", "job_bill_daily_seller_stat_email_enable send email flag.1:send", "日账单统计-是否发送邮件，1：发送", ""), //
	
	
	JOB_CLAP_DOWNLOAD_FILE_PATH("job_clap_download_file_path", "/data/master/zben/file/clap/download/", "the download file path of Clap SFTP", "Clap中心更新文件的目录", ""), //
	JOB_CLAP_BYER_FILE_PREFIX("job_clap_byer_file_prefix", "Comprador_", "the byer file name prefix", "Clap中心buyer更新文件的前缀", ""), //
	JOB_CLAP_BYER_FILE_DATEFORMATE("job_clap_byer_file_dateformate", "yyyyMMdd", "the byer file name appanding date formate", "Clap中心buyer更新文件日期后缀名称", ""), //
	JOB_CLAP_SELLER_FILE_PREFIX("job_clap_seller_file_prefix", "Vendedor_", "the seller file name prefix", "Clap中心seller更新文件的前缀", ""), //
	JOB_CLAP_SELLER_FILE_DATEFORMATE("job_clap_seller_file_dateformate", "yyyyMMdd", "the Seller file name appanding date formate", "Clap中心Seller更新文件日期后缀名称", ""), //
	JOB_CLAP_DISTRIBUTION_FILE_PREFIX("job_clap_distribution_file_prefix", "Distribucion_", "the distribution file name prefix", "Clap中心distribution更新文件的前缀", ""), //
	JOB_CLAP_DISTRIBUTION_FILE_DATEFORMATE("job_clap_distribution_file_dateformate", "yyyyMMdd", "the distribution file name appanding date formate", "Clap中心distribution更新文件日期后缀名称", ""), //
	JOB_CLAP_SELLERACCOUNT_FILE_PREFIX("job_clap_selleraccount_file_prefix", "CuentaPago_", "the selleraccount file name prefix", "Clap中心seller银行卡绑定更新文件的前缀", ""), //
	JOB_CLAP_SELLERACCOUNT_FILE_DATEFORMATE("job_clap_selleraccount_file_dateformate", "yyyyMMdd", "the selleraccount file name appanding date formate", "Clap中心seller银行卡绑定更新文件日期后缀格式", ""), //
	JOB_CLAP_DISTRIBUTION_SELECT_BUYER_ONETIME("job_clap_distribution_select_buyer_onetime", "500", "how many records select from db one time", "每次从数据库中取得多少数据", ""), //
	JOB_CLAP_TRADE_SELECT_BY_CLAPSTORENO_ONETIME("job_clap_trade_select_by_clapstoreno_onetime", "500", "how many records select from db one time", "每次从数据库中取得多少数据", ""), //
	JOB_CLAP_TRADE_SELECT_AVAILABLE_COUPON_ONETIME("job_clap_trade_select_available_coupon_onetime", "500", "how many records select from db one time", "每次从数据库中取得多少数据", ""), //
	JOB_RECONCILIATIONBANK_ONETIME("job_reconciliationbank_onetime", "100", "how many records select from db one time", "每次从数据库中取得多少数据", ""), //
	
	JOB_CLAP_UPNLOAD_FILE_PATH("job_clap_upnload_file_path", "/data/master/zben/file/clap/upload/", "the upload file path of Clap SFTP", "Clap中心更新文件的目录", ""), //
	JOB_CLAP_TRADE_FILE_PREFIX("job_clap_trade_file_prefix", "conciliation_", "the TradeInfo file name prefix", "Clap中心Trade前缀", ""), //
	JOB_CLAP_CHARGE_TRADE_FILE_PREFIX("job_clap_charge_trade_file_prefix", "Reporte_de_descarga_", "the TradeInfo file name prefix", "Clap中心Trade前缀", ""), //
	JOB_CLAP_TRADE_FILE_DATEFORMATE("job_clap_trade_file_dateformate", "yyyyMMdd", "the TradeInfo file name appanding date formate", "Clap中心TradeInfo文件日期后缀名称", ""), //
	JOB_CLAP_TRADE_FILE_WRITE_ONETIME("job_clap_trade_file_write_onetime", "500", "how many records write to file one time", "单次写入文件的交易信息条数", ""), //
	JOB_CLAP_TRADE_FILE_SFTP_PATH("job_clap_trade_file_sftp_path", "/Conciliation/", "Clap Center SFTP Trade file path", "Clap中心 SFTP Trade 文件路径", ""), //
	JOB_CLAP_UPDATE_FILE_SFTP_PATH("job_clap_update_file_sftp_path", "/Update/", "Clap Center SFTP Update file path", "Clap中心 SFTP 更新文件路径", ""), //
	JOB_CLAP_COMPRADOR_FILE_SFTP_PATH("job_clap_comprador_file_sftp_path", "/billetera/", "Clap Center SFTP Update file path", "Clap中心 SFTP 更新文件路径", ""), //
	JOB_CLAP_SFTP_FILE_DAY_BEFORE("job_clap_sftp_file_day_before", "0", "get X day before file of Clap center", "获取Clap中心几天前的数据", ""), //
	JOB_CLAP_DIF_FILE_PATH("job_clap_dif_file_path", "/data/master/zben/file/clap/dif/", "job clap different file path", "clap buyer 差异数据文件目录", ""), //

	JOB_CLAP_SFTP_USER_NAME("job_clap_sftp_user_name", "zte", "Clap Center SFTP user name", "Clap中心 SFTP用户名", ""), //
	JOB_CLAP_SFTP_IP_ADDRESS("job_clap_sftp_ip_address", "192.168.0.136", "Clap Center SFTP Ip address via VPN", "Clap中心 SFTP地址，VPN", ""), //
	JOB_CLAP_SFTP_PORT("job_clap_sftp_port", "22", "Clap Center SFTP port via VPN", "Clap中心 SFTP端口，VPN", ""), //
	JOB_CLAP_SFTP_PASSWORD("job_clap_sftp_password", "B0l1v4r", "Clap Center SFTP password", "Clap中心 SFTP密码", ""), //
	JOB_CLAP_SFTP_TRADE_DAY_BEFORE("job_clap_sftp_trade_day_before", "-1", "get X day before TradeInfo of Epay", "获取Epay数据库中几天前的数据", ""), //

	JOB_CHARGE_HEADER_IDENTIFIER_REGISTRY("job_charge_header_identifier_registry", "HEADER", "The fixed value of Charge file Header identifier registry", "提现文件头的固定字符 HEADER  ", ""), //
	JOB_CHARGE_RECORD_IDENTIFIER_REGISTRY("job_charge_record_identifier_registry", "CREDITO ", "The fixed value of Charge file Record identifier registry", "提现文总计的固定字符 CREDITO   ", ""), //
	JOB_CHARGE_BATH_IDENTIFIER_REGISTRY("job_charge_bath_identifier_registry", "DEBITO", "The fixed value of Charge file Bath identifier registry", "提现文总计的固定字符 CREDITO  ", ""), //
	JOB_CHARGE_TOTAL_IDENTIFIER_REGISTRY("job_charge_total_identifier_registry", "TOTAL   ", "The fixed value of Charge file Total identifier registry", "提现文总计的固定字符 TOTAL  ", ""), //
	JOB_CHARGE_HEADER_NUMBER_NEGOTIATION("job_charge_header_number_negotiation", "00000008", "Value assigned by the Bank", "银行分配给Cantv的8位判别码", ""), //
	JOB_CHARGE_RIF_OF_CANTV("job_charge_rif_of_cantv", "J001241345", "Cantv RIF to be paying the account holder", "Cantv的注册代码RIF", ""), //
	JOB_CHARGE_FILE_PREFIX("job_charge_file_prefix", "PROV_", "the charge file name prefix", "Bank提现文件的前缀", ""), //
	JOB_CHARGE_FILE_DATEFORMATE("job_charge_file_dateformate", "yyyyMMdd", "the charge file name appanding date formate", "Bank提现文件日期后缀名称", ""), //
	JOB_CHARGE_FILE_PATH("job_charge_file_path", "/data/master/sftp/venezuela/Charge/", "the Charge file path of Bank", "Bank提现文件路径", ""), //
	JOB_CHARGE_FILE_TRADE_DAY_BEFORE("job_charge_file_trade_day_before", "0", "get X day before TradeInfo of Epay", "获取Epay数据库中几天前的数据", ""), //
	JOB_CHARGE_FILE_WRITE_ONETIME("job_charge_file_write_onetime", "500", "how many records write to file one time", "单次写入文件的提现信息条数", ""), //
	JOB_CHARGE_PAYER_NAME_OF_CANTV("job_charge_payer_name_of_cantv", "CANTV C.A", "payer Name of cantv", "Cantv的支付名称", ""), //
	JOB_CHARGE_BANK_0102_ACCOUNT_OF_CANTV("job_charge_bank_0102_account_of_cantv", "01020762220000036184", "Cantv's bank account in Bank of Venezuela", "Cantv在委内瑞拉银行注册的账号", ""), //

	JOB_CHARGE_RECONCILIATION_FILE_PREFIX("job_charge_reconciliation_file_prefix", "PROV_", "the charge file name prefix", "Bank提现对账文件的前缀", ""), //
	JOB_CHARGE_RECONCILIATION_FILE_DATEFORMATE("job_charge_reconciliation_file_dateformate", "yyyyMMdd", "the charge file name appanding date formate", "Bank提现对账文件日期后缀名称", ""), //
	JOB_CHARGE_RECONCILIATION_FILE_TRADE_DAY_BEFORE("job_charge_reconciliation_file_trade_day_before", "0", "get X day before reconciliation file of Bank", "获取bank几天前的对账文件", ""), //
	JOB_CHARGE_RECONCILIATION_COPY_FILE_PATH("job_charge_reconciliation_copy_file_path", "/data/master/zben/file/reconciliation_charge/venezuela/", "the Charge Reconciliation file copy path of Bank", "Bank提现对账文件拷贝路径",
			""), //
	JOB_CHARGE_DOCUMENT_IDENTIFIER_REGISTRY("job_charge_document_identifier_registry", "DOCUMENT", "The fixed value of Charge file Header identifier registry", "提现文件头的固定字符 HEADER  ", ""), //
	JOB_CHARGE_AJUSTE_IDENTIFIER_REGISTRY("job_charge_ajuste_identifier_registry", "AJUSTE ", "The fixed value of Charge file Record identifier registry", "提现文总计的固定字符 CREDITO   ", ""), //
	JOB_CHARGE_RECONCLIATION_BATCH_TO_DB_COUNT("job_charge_reconcliation_batch_to_db_count", "100", "charge reconciliation job:batch to db count", "提现对账任务：每批次入库数", ""), //
	JOB_CHARGE_RECONCILIATION_BANK_FILE_PATH("job_charge_reconciliation_bank_file_path", "/data/master/sftp/venezuela/Conciliation_charge/", "the Charge Reconciliation bank file path ", "Bank提现对账文件路径", ""), //
	JOB_CHARGE_TRADE_INFO_HOUR_OF_DAY("job_charge_trade_info_hour_of_day", "20", "every day which hour create the charge file", "每天几点生成提现文件", ""), //
	JOB_CHARGE_RECONCILIATION_TRADE_INFO_HOUR_OF_DAY("job_charge_reconciliation_trade_info_hour_of_day", "20", "every day which hour create the charge file", "每天几点生成提现文件", ""),//
	
	JOB_ONLINE_USER_COUNT_EMAIL_SUBJECT("job_online_user_count_email_subject", "Usuarios en linea", "JOB_ONLINE_USER_COUNT_EMAIL_SUBJECT", "在线数统计-发送邮件标题", ""), //
	JOB_ONLINE_USER_COUNT_EMAIL_TO("job_online_user_count_email_to", "yofren.hernandez@zte.com.cn,cesar.alvarel@zte.com.cn", "JOB_ONLINE_USER_COUNT_EMAIL_TO", "在线数统计-发送邮件,收件人", ""), //
	JOB_ONLINE_USER_COUNT_EMAIL_BCC("job_online_user_count_email_bcc", "list.cantv.epay.mainten@zbensoft.com", "JOB_ONLINE_USER_COUNT_EMAIL_BCC", "在线数统计-发送邮件,暗抄送人", ""), //
	JOB_ONLINE_USER_COUNT_API("job_online_user_count_api", "http://#ip#/api/statistics/getOnlineUserCount", "job_online_user_count_api", "在线数统计-统计所有业务机的api", ""), //
	JOB_ONLINE_USER_COUNT_SERVICE_IP("job_online_user_count_service_ip", "10.11.2.1,10.11.2.2,10.11.2.3", "JOB_ONLINE_USER_COUNT_SERVICE_IP", "在线数统计-统计所有业务机的ip", ""), //
	
	
	JOB_REPORT_BUYER_ACTIVE_STAT_FILE_PATH("job_report_buyer_active_stat_file_path",  "/data/master/zben/file/statitic/buyer/", "report statistics buyer active file_path", "Buyer 激活用户报表",""), //
	JOB_REPORT_BUYER_ACTIVE_STAT_FILE_WRITE_ONETIME("job_report_buyer_active_stat_file_write_onetime", "50000", "report buyer active stat file write onetime", "每次读取数据库激活用户的条数", ""), //
	JOB_REPORT_BUYER_ACTIVE_STAT_FILE_PREFIX("job_report_buyer_active_stat_file_prefix", "Report_Comprador_Active_", "the byer report file name prefix", "buyer激活统计文件的前缀", ""), //
	JOB_REPORT_BUYER_ACTIVE_STAT_FILE_DATEFORMATE("job_report_buyer_active_stat_file_dateformate", "yyyyMMddHHmmss", "the buyer report file name appanding date formate", "buyer激活统计文件日期后缀名称", ""), //
	JOB_REPORT_BUYER_ACTIVE_STAT_FILE_DAY_OF_MONTH("job_report_buyer_active_stat_file_day_of_month", "1", "which day of month write the report", "每月几号出报表", ""), //
	JOB_REPORT_BUYER_ACTIVE_STAT_EMAIL_SUBJECT("job_report_buyer_active_stat_email_subject", "Billetera Movil - Estadistica del comprador", "subject of statistics email", "用户统计邮件主题", ""), //
	JOB_REPORT_BUYER_ACTIVE_STAT_EMAIL_TO("job_report_buyer_active_stat_email_to", "yofren.hernandez@zte.com.cn,cesar.alvarel@zte.com.cn", "JOB_ONLINE_USER_COUNT_EMAIL_TO", "用户统计-发送邮件,收件人", ""), //
	JOB_REPORT_BUYER_ACTIVE_STAT_EMAIL_BCC("job_report_buyer_active_stat_email_bcc", "list.cantv.epay.mainten@zbensoft.com", "JOB_ONLINE_USER_COUNT_EMAIL_BCC", "用户统计-发送邮件,暗抄送人", ""), //
	JOB_REPORT_BUYER_ACTIVE_STAT_EMAIL_ENABLE("job_report_buyer_active_stat_email_enable", "0", "job_report_buyer_active_stat send email flag.1:send", "用户统计-是否发送邮件，1：发送", ""), //
	
	
	JOB_REPORT_SELLER_ACTIVE_STAT_FILE_PATH("job_report_seller_active_stat_file_path",  "/data/master/zben/file/statitic/seller/", "report statistics seller active file_path", "Buyer 激活用户报表",""), //
	JOB_REPORT_SELLER_ACTIVE_STAT_FILE_WRITE_ONETIME("job_report_seller_active_stat_file_write_onetime", "1000", "report seller active stat file write onetime", "每次读取数据库激活用户的条数", ""), //
	JOB_REPORT_SELLER_ACTIVE_STAT_FILE_PREFIX("job_report_seller_active_stat_file_prefix", "Report_Vendedor_Active_", "the seller report file name prefix", "seller激活统计文件的前缀", ""), //
	JOB_REPORT_SELLER_ACTIVE_STAT_FILE_DATEFORMATE("job_report_seller_active_stat_file_dateformate", "yyyyMMddHHmmss", "the seller report file name appanding date formate", "seller激活统计文件日期后缀名称", ""), //
	JOB_REPORT_SELLER_ACTIVE_STAT_FILE_DAY_OF_MONTH("job_report_seller_active_stat_file_day_of_month", "1", "which day of month write the report", "每月几号出报表", ""), //
	JOB_REPORT_SELLER_ACTIVE_STAT_EMAIL_SUBJECT("job_report_seller_active_stat_email_subject", "Billetera Movil - Estadistica del vendedor", "subject of statistics email", "商户统计邮件主题", ""), //
	JOB_REPORT_SELLER_ACTIVE_STAT_EMAIL_TO("job_report_seller_active_stat_email_to", "yofren.hernandez@zte.com.cn,cesar.alvarel@zte.com.cn", "JOB_ONLINE_USER_COUNT_EMAIL_TO", "商户统计-发送邮件,收件人", ""), //
	JOB_REPORT_SELLER_ACTIVE_STAT_EMAIL_BCC("job_report_seller_active_stat_email_bcc", "list.cantv.epay.mainten@zbensoft.com", "JOB_ONLINE_USER_COUNT_EMAIL_BCC", "商户统计-发送邮件,暗抄送人", ""), //
	JOB_REPORT_SELLER_ACTIVE_STAT_EMAIL_ENABLE("job_report_seller_active_stat_email_enable", "0", "job_report_seller_active_stat_email send email flag.1:send", "商户统计-是否发送邮件，1：发送", ""), //
	JOB_REPORT_EMPLOYEE_ACTIVE_STAT_FILE_PREFIX("job_report_employee_active_stat_file_prefix", "Report_Employee_Active_", "the employee report file name prefix", "employee激活统计文件的前缀", ""), //
	JOB_CLAP_VENDEDOR_FILE_SFTP_PATH("job_clap_vendedor_file_sftp_path",  "/billetera/", "report statistics seller active file_path", "Seller 激活用户报表",""), //
	
	
	

	REDIS_ACCOUNT_AMOUNT_EXPIRE_TIME_SEC("redis_account_amount_expire_time_sec", "864000", "account amount exipre time(sec)", "redis存放的用户余额过期时间秒 864000=10天", ""), //
	REDIS_STATISTICS_MONITOR_EXPIRE_TIME_SEC("redis_statistics_monitor_expire_time_sec", "10", "statistics monitor exipre time(sec)", "redis存放的接口统计过期时间秒 10=10s,最终会X2", ""), //
	REDIS_STATISTICS_MONITOR_TO_DB_EXPIRE_TIME_SEC("redis_statistics_monitor_to_db_expire_time_sec", "7200", "statistics monitor to db exipre time(sec)", "redis存放:入库的接口统计过期时间秒 7200=2小时,最终会X2", ""), //
	// REDIS_PAY_ORDER_NO_EXPIRE_TIME_SEC("redis_pay_order_no_expire_time_sec", "7200", "pay order no exipre time(sec)", "redis存放:订单号多久过期，防止重复提交相同的orderNo 7200=2小时", ""), //
	//REDIS_PAY_ORDER_NO_EXPIRE_TIME_SEC("redis_pay_order_no_expire_time_sec", "86400", "pay order no exipre time(sec)", "redis存放:订单号多久过期，防止重复提交相同的orderNo 86400=24小时", ""), //
	REDIS_PAY_ORDER_NO_EXPIRE_TIME_SEC("redis_pay_order_no_expire_time_sec", "7200", "pay order no exipre time(sec)", "redis存放:订单号多久过期，防止重复提交相同的orderNo 7200=2小时", ""), //
	REDIS_TOKEN_EXPIRE_CREATE_TIME_SEC("redis_token_expire_create_time_sec", "36000", "token exipre create time(sec)", "redis存放:token多久过期，用户登录多久后创建token，token中的过期时间 36000=10小时", ""), //
	REDIS_TOKEN_EXPIRE_TIME_SEC("redis_token_expire_time_sec", "600", "token exipre time(sec)", "redis存放:token多久不用过期，用户登录多久后不操作会过期。时间： 360=6分钟", ""), //
	REDIS_TOKEN_EXPIRE_UPDATE_TIME_MS("redis_token_expire_update_time_ms", "5000", "update token exipre time", "redis存放:更新token多久过期，防止频繁更新，设置的时间内只更新一次，5000=5秒", ""), //
	REDIS_IMG_VALIDATE_CODE_EXPIRE_TIME_SEC("redis_img_validate_code_expire_time_sec", "900", "img validate code exipre time(sec)", "redis存放:图片验证码多久过期，900=15分钟", ""), //
	
	REDIS_QRCODE_EXPIRE_TIME_SEC("redis_qrcode_expire_time_sec", "360", "qrcode exipre time(sec)", "redis存放:qrcode多久过期，用户登录多久后token过期 60=1分钟", ""), //

	REQUEST_COUNT_REST_TIME_MS("request_count_rest_time_ms", "10000", " request count reset time(ms)", "总共的请求统计多久清空，用于告警,用于界面统计显示", ""), //

	REDIS_TOP_KPI_EXPIRE_TIME_DAY("redis_top_kpi_expire_time_day", "2", "top kpi exipre time(day)", "redis存放:top kpi多久过期，365天", ""), //
	
	REDIS_ClAP_IS_DOWN_TIME_DAY("redis_clap_is_down_time_day", "2", "clap down time day", "", ""), //

	
	PAY_LIMIT_DAY_AMOUNT_BANK_RECHARGE("pay_limit_day_amount_bank_recharge", "-1", "pay_limit_day_amount_bank_recharge", "每个用户每天限制充值的金额，-1不限制", ""), //
	PAY_LIMIT_MONTH_AMOUNT_BANK_RECHARGE("pay_limit_month_amount_bank_recharge", "10000000", "pay_limit_month_amount_bank_recharge", "每个用户每月限制充值的金额，-1不限制", ""), //

	QRCODE_WHILE_COUNT_BREAK("QRCODE_WHILE_COUNT_BREAK", "100", "qrcode while count break", "二维码如果生成重复，尝试多少次", ""), //

	LOGIN_LIMIT_USER_PASSWORD_ERROR_COUNT("login_limit_user_password_error_count", "5", "login_limit_user_password_error_count", "每个用户每天限制输入错误密码的次数", ""), //
	LOGIN_LIMIT_USER_PAY_PASSWORD_ERROR_COUNT("login_limit_user_pay_password_error_count", "5", "login_limit_user_pay_password_error_count", "每个用户每天限制输入错误支付密码的次数", ""), //
	LOGIN_LIMIT_CONSUMER_APP_VERSION("login_limit_consumer_app_version", "0", "login_limit_consumer_app_version", "登录限制买家app的版本号,<=0都不能登录", ""), //
	LOGIN_LIMIT_MERCHANT_APP_VERSION("login_limit_merchant_app_version", "0", "login_limit_merchant_app_version", "登录限制卖家app的版本号,<=0都不能登录", ""), //

	JOB_DELETE_TRADE_INFO_DAY_BEFORE("job_delete_trade_info_day_before", "-180", "delete X day before TradeInfo of Epay", "删除Epay数据库中几天前的数据", ""), //
	JOB_DELETE_TRADE_INFO_ONE_TIME_NUM("job_delete_trade_info_one_time_num", "10000", "delete TradeInfo number one time", "一次删除多少TradeInfo数据", ""), //
	JOB_DELETE_TABLES("job_delete_tables", "1", "delete which tables 1:tardeInfo 2:tradeInfo buyerTradeInfo 3:tradeInfo buyerTradeInfo sellerTradeInfo", "删除哪些表", ""), //

	VID_PRE("vid_pre", "V,E,G,J", "vid_pre", "vid 的前缀", ""), //

	EMAIL_BIND_SUBJECT("email_bind_subject", "Billetera Movil - Codigo de verificacion", "email_bind_subject", "绑定邮箱，发送邮件的标题:使用验证码激活", ""), //
	EMAIL_RETRIEVE_PASSWORD_SUBJECT("email_retrieve_password_subject", "Billetera Movil - Recuperacion de contrasena", "email_retrieve_password_subject", "找回密码，发送邮件的标题：找回密码", ""), //
	PROVIDE_STORETYPE_EMAIL("provide_storetype_email", "Billetera Movil - Registro", "email_bind_subject", "当为专营店时候需要发邮寄", ""), //
	
	ERROR_HANDLING_BANK_RECHARGE_AUTO("error_handling_bank_recharge_auto", "0", "error_handling_bank_recharge_auto,1:AUTO", "银行对账，差错是否自动处理 1：是", ""), //
	ERROR_HANDLING_CHARGE_AUTO("error_handling_charge_auto", "0", "error_handling_charge_auto,1:AUTO", "提现对账，差错是否自动处理 1：是", ""), //
	
	
	IMAGE_UPLOAD_MIN_WITH("image_upload_min_with", "10", "image upload min with", "图片上传最小宽度", ""), //
	
	IMAGE_UPLOAD_MIN_HEIGHT("image_upload_min_height", "10", "image upload min height", "图片上传最小高度", ""), //
	
	
	REGISTER_IMG_VALIDATE_CODE_LEN("register_img_validate_code_len", "5", "register img validate code len", "图片验证码位数", ""), //
	
	CLAP_SYN_LIMIT_DAY("clap_syn_limit_day", "1", "clap syn：you can syn once in this limit day", "clap同步：限制天内只能同步一次", ""),//
	
	TRADE_INFO_EXPORT_PATH("trade_info_export_path", "/data/master/zben/file/trade", "", "TradeInfo export path", "TradeInfo 全量导出路径"), //
	
    JOB_SERVICE_EXCEL_EXPORT_PATH("job_service_excel_export_path", "/data/master/zben/file/statitic/service/", "", "job_service_excel_export_path", "报表导出路径"), //
    JOB_SERVICE_EXPORT_DAYS("job_service_export_time", "30", "", "JOB_SERVICE_EXPORT_TIME", "报表导出路径"), //
    JOB_SERVICE_EXCEL_SEND_TO("job_service_excel_send_to", "wang.chenyang@ztesoft.com,hong.wang@ztesoft.com", "", "job_service_excel_send_to", "报表 邮件to列表"), //
    JOB_SERVICE_EXCEL_SEND_CC("job_service_excel_send_cc", "", "", "job_service_excel_send_cc", "报表 邮件cc列表"), //
    JOB_SERVICE_EXCEL_SEND_BCC("job_service_excel_send_bcc", "", "", "job_service_excel_send_bcc", "报表 邮件bcc列表"), //
    
    REQUEST_KPI_COUNT_TIME("request_kpi_count_time", "10", "1", "request_kpi_count_time", "request kpi 数据以多长时间统计"), //
    TEST_SELLER_REGISTER_EMAIL("test_seller_register_email", "t23456789@qq.com", "", "test_seller_register_email", "Seller测试用email"),
    CONTROLLER_SELET_CALP_BY_SELECTIVE_ONETIME("controller_selet_calp_by_selective_onetime", "1000", "", "controller_selet_calp_by_selective_onetime", "查询员工表中clap一次执行个数"),
    COUPON_CONSUMER_GROUP_SELECT_ONETIMES("coupon_consumer_group_select_onetimes", "5000", "", "coupon_consumer_group_select_onetimes", "查询consumer_group表一次的个数"),
    COUPON_CONSUMER_FAMILY_GROUP_SELECT_ONETIMES("coupon_consumer_family_group_select_onetimes", "5000", "", "coupon_consumer_family_group_select_onetimes", "查询consumer_Family_group表一次的个数"),
    COUPON_CLAP_STORE_SELECT_ONETIMES("coupon_clap_store_select_onetimes", "5000", "", "coupon_clap_store_select_onetimes", "查询coupon_clap_store表一次的个数"),
    COUPON_CONSUMER_FAMILY_SELECT_ONETIMES("coupon_consumer_family_select_onetimes", "5000", "", "coupon_consumer_family_select_onetimes", "查询consumer_user_clap表一次的个数"),
    REGISTER_PROVIDER_EMPLOYEE_LIMIT_DEFULT("register_provider_employee_limit_defult", "1", "", "register_provider_employee_limit_defult", "provider注册默认员工限制"),
    REGISTER_PROVIDER_OFFICE_EMPLOYEE_LIMIT_DEFULT("register_provider_office_employee_limit_defult", "1", "", "register_provider_office_employee_limit_defult", "创建office默认员工限制"),
    REGISTER_EMPLOYEE_LIMIT_DEFULT("register_employee_limit_defult", "1", "", "register_provider_office_employee_limit_defult", "创建员工的个数"),
    REGISTER_PERSON_EMPLOYEE_LIMIT_DEFULT("register_person_employee_limit_defult", "1", "", "register_person_employee_limit_defult", "创建个人员工的个数"),
    REGISTER_OFFICE_CLAP_EMPLOYEE_LIMIT_DEFULT("register_office_clap_employee_limit_defult", "1", "", "register_office_clap_employee_limit_defult", "office创建clap员工的个数"),
    REGISTER_PROVIDER_OFFICE_LIMIT_DEFULT("register_provider_office_limit_defult", "-1", "", "register_provider_office_limit_defult -1：不限", "office默认个数限制"),
    REGISTER_PROVIDER_CLAP_STORE_LIMIT_DEFULT("register_provider_clap_store_limit_defult", "-1", "", "register_provider_clap_store_limit_defult -1：不限", "cla默认个数限制"),
    
    PROVIDER_STORE_TYPE_J("provider_store_type_j", "001,002", "", "provider_store_type_j ", "可以创建店的类型"),
    PROVIDER_STORE_TYPE_G("provider_store_type_g", "001", "", "provider_store_type_j ", "可以创建店的类型"),
    PROVIDER_STORE_TYPE_E("provider_store_type_e", "003", "", "provider_store_type_j ", "可以创建店的类型"),
    PROVIDER_STORE_TYPE_V("provider_store_type_v", "003", "", "provider_store_type_j ", "可以创建店的类型"),
    
    
    MERCHANT_BANK_CARD_PERSONAL_LIMIT("merchant_bank_card_personal_limit", "3", "", "merchant_bank_card_personal_limit", "个人银行卡限制"),
    MERCHANT_BANK_CARD_PROVIDE_LIMIT("merchant_bank_card_provide_limit", "100", "", "merchant_bank_card_provide_limit", "provide银行卡限制"),
    
    EPAYMENT_BUYER_ACTIVE_F_COUNT_API_KEY("epayment_buyer_active_f_count_api_key", "83omd630oijeG8r484923222cantv", "", "epayment_buyer_active_f_count_api_key", "kantv入口api_key"),
    EPAYMENT_COUNT_TOTAL_BUYER_KEY("epayment_count_total_buyer_key", "COUNT_TOTAL_BUYER", "", "epayment_count_total_buyer_key", "cantv 查询用户数量缓存key"),
    EPAYMENT_COUNT_ACTIVE_BUYER_KEY("epayment_count_active_buyer_key", "OUNT_ACTIVE_BUYER", "", "epayment_count_active_buyer_key", "cantv 查询激活用户数量缓存key"),
    
    SEQ_GET_COUNT_LIMIT("seq_get_count_limit", "10", "", "seq_get_count_limit", "获取seq，最多尝试次数"),
    JOB_SERVICE_EXCEL_SEND_TO_TEST("job_service_excel_send_to_test", "494057239@qq.com", "", "job_service_excel_send_to", "报表 邮件to列表"), //
    JOB_SERVICE_EXCEL_EXPORT_PATH_TEST("job_service_excel_export_path_TEST", "/data/master/zben/file/statitic/service/", "", "job_service_excel_export_path", "报表导出路径"), //  C://Users//49405//Desktop//    
    //JOB_SERVICE_EXCEL_EXPORT_PATH_TEST("job_service_excel_export_path_TEST", "C://Users//49405//Desktop//", "", "job_service_excel_export_path", "报表导出路径"),
    
    
    GAS_COUPON_TO_DB_THREAD_GET_ONCE("gas_coupon_to_db_thread_get_once", "500", "gas coupon to db thread get once", "一次取出的数据条数"),
    ALARM_MO_REQUEST_MAX("alarm_mo_request_max", "50", "MO reuqest caps max", "MO 请求caps阀值"),
    
    
	TEST("test", "test", "test", "test")     ;//

	private String key;
	private String defaultValue;
	private String[] name;

	private SystemConfigKey(String key, String defalut, String... name) {
		this.key = key;
		this.defaultValue = defalut;
		this.name = name;
	}

	public String getDefault() {
		return defaultValue;
	}

	public String getDefaultStr() {

		return defaultValue == null ? "" : defaultValue;
	}

	public int getDefaultInt() {
		return defaultValue == null ? 0 : Integer.valueOf(defaultValue);
	}

	public String getNameCN() {
		if (name.length > 1 && name[1] != null) {
			return name[1];
		}
		return "";
	}

	public String getNameEN() {
		if (name.length > 0 && name[0] != null) {
			return name[0];
		}
		return "";
	}

	public String getNameES() {
		if (name.length > 2 && name[2] != null) {
			return name[2];
		}
		return "";
	}

	/**
	 * 返回key
	 * 
	 * @return
	 */
	public String getKey() {
		return this.key;
	}

	public static final SystemConfigKey valueOfKey(String key) throws IllegalArgumentException {
		for (SystemConfigKey systemConfigKey : values()) {
			if (systemConfigKey.key == key) {
				return systemConfigKey;
			}
		}
		throw new IllegalArgumentException("No enum const BindType with command id " + key);
	}

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		List<SystemConfigCodeHelp> list = getList(SystemConfigKey.class, null);
		for (SystemConfigCodeHelp codeHelp : list) {
			if (!codeHelp.getCode().equals(SystemConfigKey.valueOfKey(codeHelp.getCode()).toString().toLowerCase())) {
				System.out.println("[WARN][NAME   NOT SAME]" + codeHelp.getCode());
			}
			if (codeHelp.getDefalut() == null || "".equals(codeHelp.getDefalut())) {
				System.out.println("[WARN][DAFALUT IS NULL]" + codeHelp.getCode());
			}

			if (codeHelp.getNameOne() == null || "".equals(codeHelp.getNameOne())) {
				System.out.println("[WARN][NameOne IS NULL]" + codeHelp.getCode());
			}
			if (codeHelp.getNameTwo() == null || "".equals(codeHelp.getNameTwo())) {
				System.out.println("[WARN][NameTwo   IS NULL]" + codeHelp.getCode());
			}
			if (codeHelp.getNameThree() == null || "".equals(codeHelp.getNameThree())) {
				System.out.println("[WARN][NameThree   IS NULL]" + codeHelp.getCode());
			}
		}
		System.out.println("===End==");
	}

	public static List<SystemConfigCodeHelp> getList(Class clazz, String code) {
		List<SystemConfigCodeHelp> list = new ArrayList<SystemConfigCodeHelp>();
		try {

			if (clazz.isEnum()) {
				Object[] obs = clazz.getEnumConstants();
				for (Object ob : obs) {
					Method m = clazz.getMethod("getKey");
					Method m2 = clazz.getMethod("getNameEN");
					Method m3 = clazz.getMethod("getNameCN");
					Method m4 = clazz.getMethod("getNameES");
					Method m5 = clazz.getMethod("getDefault");
					Object codeOb = m.invoke(ob);
					if (code == null || "".equals(code) || code.equals(String.valueOf(codeOb))) {
						SystemConfigCodeHelp codeHelp = new SystemConfigCodeHelp();
						codeHelp.setCode(String.valueOf(m.invoke(ob)));
						codeHelp.setNameOne(String.valueOf(m2.invoke(ob)));
						codeHelp.setNameTwo(String.valueOf(m3.invoke(ob)));
						codeHelp.setNameThree(String.valueOf(m4.invoke(ob)));
						codeHelp.setDefalut(String.valueOf(m5.invoke(ob)));
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
