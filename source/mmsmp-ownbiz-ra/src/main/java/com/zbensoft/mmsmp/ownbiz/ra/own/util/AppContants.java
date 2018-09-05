

package com.zbensoft.mmsmp.ownbiz.ra.own.util;

public class AppContants {
    public static final int QUEUE_NUM = PropertiesUtil.getInt("common", "queue_number");
    public static final String CLIENT_IP = PropertiesUtil.get("common", "client_ip");
    public static final int CLIENT_PORT = PropertiesUtil.getInt("common", "client_port");
    public static final int SERVER_PORT = PropertiesUtil.getInt("common", "server_port");
    public static final int SEND_REQUEST_THREAD_NUM = PropertiesUtil.getInt("common", "send_request_thread_num");
    public static final int RECEIVE_RESULT_THREAD_NUM = PropertiesUtil.getInt("common", "receive_result_thread_num");
    public static final String MOBILE_URL = PropertiesUtil.get("common", "mobileUrl");
    public static final String CONFIRM_URL = PropertiesUtil.get("common", "confirmUrl");
    public static final long APP_REPEAT_REQUEST_INTERVAL = (long)(PropertiesUtil.getInt("common", "app_repeat_request_interval") * 60 * 1000);
    public static final long WEB_REPEAT_REQUEST_INTERVAL = (long)(PropertiesUtil.getInt("common", "web_repeat_request_interval") * 60 * 1000);
    public static final long PAY_REQUEST_MAX_KEEP_TIME = (long)(PropertiesUtil.getInt("common", "pay_request_max_keep_time") * 60 * 1000);
    public static final String DES_ENCRYPTION_KEY = PropertiesUtil.get("common", "des_encryption_key");
    public static final String TEST_URl = PropertiesUtil.get("common", "test_url");
    public static String OWN_SENDADDRESS = PropertiesUtil.get("common", "own_sendAddress");
    public static final int MMS_REPEAT_SEND_COUNT = PropertiesUtil.getInt("common", "mms_repeat_send_count");
    public static String APP_PAY_TIPS = PropertiesUtil.get("common", "app_pay_tips");
    public static String GET_MOBILE_VASID = PropertiesUtil.get("common", "get_mobile_vasid");
    public static String GET_MOBILE_SMS_BEGIN = PropertiesUtil.get("common", "get_mobile_sms_begin");
    public static long PHONE_NUMBER_KEY_DAY = (long)(PropertiesUtil.getInt("common", "phone_number_key_day") * 24 * 60 * 60 * 1000);
    public static final String SYSTEM_PARA_APP_PAY_TIPS_KEY = "APP_PAY_TIPS";
    public static final String SYSTEM_PARA_MOBILE_KEEP_TIME_KEY = "MOBILE_KEEP_TIME";
    public static final String SYSTEM_PARA_UNIONVASPID_KEY = "UNIONVASPID";
    public static final String SYSTEM_PARA_GET_MOBILE_VASID_KEY = "TAKE_ACC_NUMBER";
    public static final String SYSTEM_PARA_GET_MOBILE_SMS_BEGIN_KEY = "TAKE_SMS_TEXT";
    public static final String SYSTEM_PARA_OWN_VASID_KEY = "OWN_VASID";
    public static final String SYSTEM_PARA_OWN_SENDADDRESS_KEY = "OWN_SENDADDRESS";
    public static final String COOPER_KEY = "cooper_key";
    public static final String COOPER_KEY_MOBILE = "cooper_key_mobile";
    public static final String PRODUCT_SERVICE_KEY = "product_service_key";
    public static final String PRODUCT_DISTRIBUTE_KEY = "product_distribute_key";
    public static final String VASP_KEY = "vasp_key";
    public static final int NOTIFY_SP_TYPE = 3;
    public static final int RETURN_CODE_SUCCESS = Integer.parseInt(PropertiesUtil.get("returnCode", "request_receive_success").split(",")[0]);
    public static final String RETURN_CODE_SUCCESS_DESC = PropertiesUtil.get("returnCode", "request_receive_success").split(",")[1];
    public static final int RETURN_CODE_DATA_NULL = Integer.parseInt(PropertiesUtil.get("returnCode", "data_null").split(",")[0]);
    public static final String RETURN_CODE_DATA_NULL_DESC = PropertiesUtil.get("returnCode", "data_null").split(",")[1];
    public static final int RETURN_CODE_ACCOUNTID_OR_COOPER_KEY_NULL = Integer.parseInt(PropertiesUtil.get("returnCode", "accountid_or_cooper_key_null").split(",")[0]);
    public static final String RETURN_CODE_ACCOUNTID_OR_COOPER_KEY_NULL_DESC = PropertiesUtil.get("returnCode", "accountid_or_cooper_key_null").split(",")[1];
    public static final int RETURN_CODE_KEY_VALIDATE_ERROR = Integer.parseInt(PropertiesUtil.get("returnCode", "key_validate_error").split(",")[0]);
    public static final String RETURN_CODE_KEY_VALIDATE_ERROR_DESC = PropertiesUtil.get("returnCode", "key_validate_error").split(",")[1];
    public static final int RETURN_CODE_VALIDATE_REQUEST_NULL_OR_OUT = Integer.parseInt(PropertiesUtil.get("returnCode", "validate_request_null_or_out").split(",")[0]);
    public static final String RETURN_CODE_VALIDATE_REQUEST_NULL_OR_OUT_DESC = PropertiesUtil.get("returnCode", "validate_request_null_or_out").split(",")[1];
    public static final int RETURN_CODE_VALIDATE_CODE_ERROR = Integer.parseInt(PropertiesUtil.get("returnCode", "validate_code_error").split(",")[0]);
    public static final String RETURN_CODE_VALIDATE_CODE_ERROR_DESC = PropertiesUtil.get("returnCode", "validate_code_error").split(",")[1];
    public static final int RETURN_CODE_REPEAT_REQUEST = Integer.parseInt(PropertiesUtil.get("returnCode", "repeat_request").split(",")[0]);
    public static final String RETURN_CODE_REPEAT_REQUEST_DESC = PropertiesUtil.get("returnCode", "repeat_request").split(",")[1];
    public static final int RETURN_CODE_APP_PAY_REQUESt_NULL_OR_OUT = Integer.parseInt(PropertiesUtil.get("returnCode", "app_pay_request_null_or_out").split(",")[0]);
    public static final String RETURN_CODE_APP_PAY_REQUESt_NULL_OR_OUT_DESC = PropertiesUtil.get("returnCode", "app_pay_request_null_or_out").split(",")[1];
    public static final int RETURN_CODE_VAS_SERVICE_NULL = Integer.parseInt(PropertiesUtil.get("returnCode", "vas_service_null").split(",")[0]);
    public static final String RETURN_CODE_VAS_SERVICE_NULL_DESC = PropertiesUtil.get("returnCode", "vas_service_null").split(",")[1];
    public static final int RETURN_CODE_MOBILE_ERROR = Integer.parseInt(PropertiesUtil.get("returnCode", "mobile_error").split(",")[0]);
    public static final String RETURN_CODE_MOBILE_ERROR_DESC = PropertiesUtil.get("returnCode", "mobile_error").split(",")[1];
    public static final int RETURN_CODE_MOBILE_OUT = Integer.parseInt(PropertiesUtil.get("returnCode", "mobile_out").split(",")[0]);
    public static final String RETURN_CODE_MOBILE_OUT_DESC = PropertiesUtil.get("returnCode", "mobile_out").split(",")[1];
    public static final int RETURN_CODE_UNEXPECT_ERROR = Integer.parseInt(PropertiesUtil.get("returnCode", "unexpect_error").split(",")[0]);
    public static final String RETURN_CODE_UNEXPECT_ERROR_DESC = PropertiesUtil.get("returnCode", "unexpect_error").split(",")[1];
    public static final int RETURN_CODE_INSTRUCTION_ERROR = Integer.parseInt(PropertiesUtil.get("returnCode", "instruction_error").split(",")[0]);
    public static final String RETURN_CODE_INSTRUCTION_ERROR_DESC = PropertiesUtil.get("returnCode", "instruction_error").split(",")[1];
    public static final int RETURN_CODE_PREDETERMINED_TIME = Integer.parseInt(PropertiesUtil.get("returnCode", "repeat_request_in_predetermined_time").split(",")[0]);
    public static final String RETURN_CODE_PREDETERMINED_TIME_DESC = PropertiesUtil.get("returnCode", "repeat_request_in_predetermined_time").split(",")[1];
    public static final int RETURN_CODE_MOBILE_UNEQUAL = Integer.parseInt(PropertiesUtil.get("returnCode", "mobile_unequal").split(",")[0]);
    public static final String RETURN_CODE_MOBILE_UNEQUAL_DESC = PropertiesUtil.get("returnCode", "mobile_unequal").split(",")[1];
    public static final int RETRUE_MOBILE_PARAM_CODE = Integer.parseInt(PropertiesUtil.get("returnCode", "mobile_param_error").split(",")[0]);
    public static final String RETRUE_MOBLIE_PARAM_DESC = PropertiesUtil.get("returnCode", "mobile_param_error").split(",")[1];
    public static final int RETRUE_MOBILE_GET_CODE = Integer.parseInt(PropertiesUtil.get("returnCode", "mobile_get_error").split(",")[0]);
    public static final String RETRUE_MOBILE_GET_DESC = PropertiesUtil.get("returnCode", "mobile_get_error").split(",")[1];
    public static final int RETRUE_MOBILE_GET_SUCCESS_CODE = Integer.parseInt(PropertiesUtil.get("returnCode", "mobile_get_success").split(",")[0]);
    public static final String RETRUE_MOBILE_GET_SUCCESS_DESC = PropertiesUtil.get("returnCode", "mobile_get_success").split(",")[1];
    public static final int RETRUE_ORDERING_RELATIONS_ISEXISTS_CODE = Integer.parseInt(PropertiesUtil.get("returnCode", "ordering_relations_isExists").split(",")[0]);
    public static final String RETRUE_ORDERING_RELATIONS_ISEXISTS_DESC = PropertiesUtil.get("returnCode", "ordering_relations_isExists").split(",")[1];
    public static final String SEPARATOR = ",";
    public static final String FEE_TYPE_ORDER = "1";
    public static final String FEE_TYPE_CANCEL = "2";
    public static final String FEE_TYPE_DEMAND = "4";
    public static final String FEE_TYPE_SEND_VALIDATE_CODE = "9";
    public static final String STATUS_TO_VAC = "400";
    public static final String STATUS_PAY_REQUEST_SUCCESS_RECEIVE = "401";

    public AppContants() {
    }

    public static void main(String[] args) {
        System.out.println(RETRUE_MOBILE_GET_CODE);
    }
}
