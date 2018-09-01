package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Resource;

public class Sm7Constant {
    public static boolean debug;
    public static String LOGINING;
    public static String LOGIN_ERROR;
    public static String SEND_ERROR;
    public static String CONNECT_TIMEOUT;
    public static String STRUCTURE_ERROR;
    public static String NONLICETSP_ID;
    public static String SP_ERROR;
    public static String VERSION_ERROR;
    public static String OTHER_ERROR;
    public static String HEARTBEAT_ABNORMITY;
    public static String SUBMIT_INPUT_ERROR;
    public static String FORWARD_INPUT_ERROR;
    public static String MTROUTEUPDATE_INPUT_ERROR;
    public static String MOROUTEUPDATE_INPUT_ERROR;
    public static String CONNECT_INPUT_ERROR;
    public static String CANCEL_INPUT_ERROR;
    public static String QUERY_INPUT_ERROR;
    public static String DELIVER_REPINPUT_ERROR;
    public static String ACTIVE_REPINPUT_ERROR;
    public static String SMC_MESSAGE_ERROR;
    public static String INT_SCOPE_ERROR;
    public static String STRING_LENGTH_GREAT;
    public static String STRING_NULL;
    public static String VALUE_ERROR;
    public static String FEE_USERTYPE_ERROR;
    public static String REGISTERED_DELIVERY_ERROR;
    public static String PK_TOTAL_ERROR;
    public static String PK_NUMBER_ERROR;
    public static String NEEDREPORT_ERROR;
    public static String PRIORITY_ERROR;
    public static String DESTTERMID_ERROR;
    public static final int Login_Request_Id = 1;
    public static final int Login_Resp_Request_Id = -2147483647;
    public static final int Submit_Request_Id = 2;
    public static final int Submit_Resp_Request_Id = -2147483646;
    public static final int Deliver_Request_Id = 3;
    public static final int Deliver_Resp_Request_Id = -2147483645;
    public static final int Active_Test_Request_Id = 4;
    public static final int Active_Test_Resp_Request_Id = -2147483644;
    public static final int Forward_Request_Id = 5;
    public static final int Forward_Resp_Request_Id = -2147483643;
    public static final int Exit_Request_Id = 6;
    public static final int Exit_Resp_Request_Id = -2147483642;

    public Sm7Constant() {
    }

    public static void initConstant(Resource resource) {
        if (LOGINING == null) {
            LOGINING = resource.get("smproxy/logining");
            LOGIN_ERROR = resource.get("smproxy/login-error");
            SEND_ERROR = resource.get("smproxy/send-error");
            CONNECT_TIMEOUT = resource.get("smproxy/connect-timeout");
            STRUCTURE_ERROR = resource.get("smproxy/structure-error");
            NONLICETSP_ID = resource.get("smproxy/nonlicetsp-id");
            SP_ERROR = resource.get("smproxy/sp-error");
            VERSION_ERROR = resource.get("smproxy/version-error");
            OTHER_ERROR = resource.get("smproxy/other-error");
            HEARTBEAT_ABNORMITY = resource.get("smproxy/heartbeat-abnormity");
            SUBMIT_INPUT_ERROR = resource.get("smproxy/submit-input-error");
            CONNECT_INPUT_ERROR = resource.get("smproxy/connect-input-error");
            CANCEL_INPUT_ERROR = resource.get("smproxy/cancel-input-error");
            QUERY_INPUT_ERROR = resource.get("smproxy/query-input-error");
            DELIVER_REPINPUT_ERROR = resource.get("smproxy/deliver-repinput-error");
            ACTIVE_REPINPUT_ERROR = resource.get("smproxy/active-repinput-error");
            SMC_MESSAGE_ERROR = resource.get("smproxy/smc-message-error");
            INT_SCOPE_ERROR = resource.get("smproxy/int-scope-error");
            STRING_LENGTH_GREAT = resource.get("smproxy/string-length-great");
            STRING_NULL = resource.get("smproxy/string-null");
            VALUE_ERROR = resource.get("smproxy/value-error");
            FEE_USERTYPE_ERROR = resource.get("smproxy/fee-usertype-error");
            REGISTERED_DELIVERY_ERROR = resource.get("smproxy/registered-delivery-error");
            PK_TOTAL_ERROR = resource.get("smproxy/pk-total-error");
            PK_NUMBER_ERROR = resource.get("smproxy/pk-number-error");
            FORWARD_INPUT_ERROR = resource.get("smproxy/forward_input_error");
            MTROUTEUPDATE_INPUT_ERROR = resource.get("smproxy/mtrouteupdate_input_error");
            MOROUTEUPDATE_INPUT_ERROR = resource.get("smproxy/morouteupdate_input_error");
            NEEDREPORT_ERROR = resource.get("smproxy/needreport_error");
            PRIORITY_ERROR = resource.get("smproxy/priority_error");
            DESTTERMID_ERROR = resource.get("smproxy/desttermid_error");
        }

    }
}

