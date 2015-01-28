package com.baitaner.common.utils;

public class CacheKeyUtil {
	private static String USER_NAME = "user";
	private static String USER_LINES = "user_lines_only";

	private static String USER_UPDATE_EMAIL = "user_update_email";
	private static String USER_UPDATE_EMAIL_RCODE = "user_update_email_rcode";

	private static String USER_SESSION = "user_session";
	private static String USER_SESSION_LIST = "user_session_list";
	private static String USER_RESET_PASSWORD = "user_rp";  //重置密码功能


	private static String PANEL_NAME = "panel";
	private static String PANEL_STATUS = "panel_status";
	private static String PANEL_SESSION = "panel_session";
    private static String BIND_PANEL = "bc_panel";  //key: bindCode, value: panelId
	private static String PANEL_HEART_BEAT = "panel_hb_map";

    //app端和panel端消息传递的队列
	private static String QUEUE_PANEL_STATUS_KEY = "queue_panel_curr_status_key";
	private static String QUEUE_PANEL_SETTING_KEY = "queue_panel_setting_key";
	private static String QUEUE_USER_CTRL_KEY = "queue_user_ctrl_key";
	private static String QUEUE_MANAGER_MSG_KEY = "queue_manager_msg_key";


	public static String getUserUpdateEmail(long id){
		return createId(USER_UPDATE_EMAIL,id);
	}
	public static String getUserUpdateEmailRcode(String rcode){
		return  createId(USER_UPDATE_EMAIL_RCODE,rcode);
	}

	
	public static String getUserKey(long id){
		return createId(USER_NAME,id);
	}
	
	public static String getUserRPKey(String rcode){
		return createId(USER_RESET_PASSWORD,rcode);
	}

	public static String getUserSessionKey(String session){
		return createId(USER_SESSION,session);
	}

    public static String getUserSessionListKey(long id){
        return createId(USER_SESSION_LIST,id);
    }
    public static String getUserlinesKey(){
        return USER_LINES;
    }

	public static String getPanelKey(long id){
		return createId(PANEL_NAME,id);
	}
	public static String getPanelStatusKey(long id){
		return createId(PANEL_STATUS,id);
	}

    public static String getPanelSessionKey(long id){
		return createId(PANEL_SESSION,id);
    }

    public static String getBindPanelKey(String bindCode){
        return createId(BIND_PANEL,bindCode);
    }
    public static String getPanelHBKey(){
		return PANEL_HEART_BEAT;
	}
	private static String createId(String type,long id){
		if(type!=null && !"".equals(type.trim())){
			return type+":" + id;
		}
		return ""+id;
	}
	private static String createId(String type,String name){
		return type+":" + name;
	}

    public static String getQueueUserCtrlKey(long id){
        return createId(QUEUE_USER_CTRL_KEY,id);
    }
    public static String getQueuePanelStatus(long id,String session){
        return createId(QUEUE_PANEL_STATUS_KEY,id+"_"+session);
    }
    public static String getQueuePanelSetting(long id,String session){
        return createId(QUEUE_PANEL_SETTING_KEY,id+"_"+session);
    }

	public static String getManagerMSG(String sn){
		return createId(QUEUE_MANAGER_MSG_KEY,sn);
	}
}
