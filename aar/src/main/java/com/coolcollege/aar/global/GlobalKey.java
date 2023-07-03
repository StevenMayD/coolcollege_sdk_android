package com.coolcollege.aar.global;

/**
 * Created by Evan_for on 2020/6/11
 */
public interface GlobalKey {

    String DOMAIN = "1";
    String BUGLY_APP_ID = "a3d49b70c7";
    //达摩院
    String CORP_ID_DM = "ww63cccec6547a5684"; //t4

    //d1
    String CORP_ID_D1 = "ding2411a1dea026d64d35c2f4657eb6378f";
    //测试b
    String CORP_ID_T1 = "dingd421e1b6e491fa9c35c2f4657eb6378f";
    String CORP_ID_T3 = "dingc08f4d8f4d8647f135c2f4657eb6378f";
    //String CORP_ID_RELEASE = "oaf2d2e8c06343a69ac9aea2c3544699";
    String CORP_ID_RELEASE = "dingbb5719b96eee8cb324f2f5cc6abecb85";
    //灰度
    String CORP_ID_RELEASE2 = "dingef2502a50df74ccc35c2f4657eb6378f";
    String UM_KEY = "600e6ee06a2a470e8f898083";

    String DEBUG_ADDRESS = "debug_address";

    String LOGIN_TYPE = "own_app";
    String SYSTEM_NAME = "android";

    String USER_NAME_KEY = "username";
    String LOGIN_MOBILE_KEY = "login_mobile";
    String LONG_TOKEN_KEY = "long_token";
    String USER_AVATAR_KEY = "user_avatar";
    String USER_NAME2_KEY = "user_name";
    String ACCOUNT_KEY = "account";
    String PASSWORD_KEY = "password";
    String LOGIN_DATA_KEY = "login_data";
    String DOMAIN_KEY = "domain";
    String CORP_ID_KEY = "corp_id";
    String PAGE_NUM_KEY = "pageNumber";
    String PAGE_SIZE_KEY = "pageSize";
    String PAGE_NUM2_KEY = "page_number";
    String PAGE_SIZE2_KEY = "page_size";
    String ACTION_TYPE_KEY = "action_type";
    String ACTION_KEY = "action";
    String ACTION_TYPE_LOGIN = "login";
    String ACTION_TYPE_RESET = "reset_password";
    String ACTION_TYPE_REGISTER = "register";
    String ACTION_TYPE_JOIN_ENTER = "join_enterprise";


    String ACTION_AFTER_LOGIN = "after_login";
    String ACTION_CHANGE_ENTERPRISE = "change_enterprise";

    String USER_KEY = "users";
    String V2_KEY = "v2";
    String MICRO_COURSES_KEY = "micro_courses";

    String INDUSTRY_KEY = "industry";
    String POSITION_KEY = "position";
    String TEXT_KEY = "text";
    String ENTERPRISE_NAME_KEY = "enterprise_name";
    String ENTERPRISE_SCALE_KEY = "enterprise_scale";
    String FIRST_INDUSTRY_KEY = "first_industry";
    String PROVINCE_KEY = "province";
    String CITY_KEY = "city";
    String ENTERPRISE_LIST_KEY = "enterprise_list";
    String REGISTER_REQ_DATA_KEY = "register_request_data";

    String POSITION_LIST_KEY = "position_list";
    String INDUSTRY_LIST_KEY = "industry_list";

    String INVITATION_PARAMS_KEY = "invitation_params";
    String IS_MULTITERMINAL_KEY = "isMultiterminal";


    String ENTERPRISE_ID_KEY = "enterprise_id";
    String USER_ID_KEY = "user_id";
    String COURSE_ID_KEY = "course_id";
    String PARENT_ID_KEY = "parentId";
    String PARENT_ID2_KEY = "parent_id";
    String TIME_STAMP_KEY = "timestamp";
    String SYSTEM_NAME_KEY = "system_name";
    String VERSION_KEY = "version";
    String BRAND_KEY = "brand";
    String MODEL_NUMBER_KEY = "model_number";
    String LOGIN_TYPE_KEY = "login_type";
    String ENCRYPT_KEY = "encrypt";
    String FILTER_AUTH_KEY = "filter_auth";
    String QUERY_USER_COUNT_KEY = "query_user_count";
    String RECURSION_KEY = "recursion";
    String PUSH_MESSAGE_TYPE_KEY = "messageType";
    String PUSH_MESSAGE_URL_KEY = "message_url";
    String PUSH_MESSAGE_ID_KEY = "message_id";
    String PUSH_FLAG_KEY = "flag";
    String PUSH_REQUEST_CODE_KEY = "push_request_code";

    String COVER_KEY = "cover";
    String DURATION_KEY = "duration";
    String SIZE_KEY = "size";
    String PARAMETER_KEY = "parameter";
    String KEY_KEY = "key";
    String FILE_KEY = "file";
    String FILE_TYPE_IMG_KEY = "image";
    String FILE_TYPE_KEY = "fileType";
    String PRIVATE_POLICY_KEY = "private_policy";
    String AGREE_KEY = "agree";

    String POLICY_START = "policy_start";
    String POLICY_END = "policy_end";
    String PRIVATE_START = "private_start";
    String PRIVATE_END = "private_end";

    String ID_KEY = "id";
    String CREDIT_KEY = "credit";
    String TITLE_KEY = "title";
    String SUMMARY_KEY = "summary";
    String RESOURCES_KEY = "resources";
    String COURSE_CLASSIFY_KEY = "course_classify";
    String COURSE_CLASSIFY_NAME_KEY = "course_classify_name";
    String DEPARTMENT_IDS_KEY = "department_ids";
    String USER_IDS_KEY = "user_ids";
    String HAS_PRAISED_KEY = "has_praised";
    String LIKE_COUNT_KEY = "like_count";
    String LIKE_STATUS_KEY = "like";
    String UNLIKE_STATUS_KEY = "unlike";
    String COMMENT_COUNT_KEY = "comment_count";
    String VISIBLE_COUNT_KEY = "visible_count";
    String AVATAR_KEY = "avatar";
    String VIDEO_PATH_KEY = "video_path";
    String TYPE_KEY = "type";
    String NODE_TYPE_CREATION = "creation";
    String NODE_TYPE_PENDING = "pending";
    String LESSON_DETAIL_KEY = "lesson_detail";
    String LESSON_TITLE_KEY = "lesson_title";
    String OTHER_WEB_PATH_KEY = "web_path";
    String OTHER_WEB_HOST_KEY = "web_host";
    String OTHER_WEB_TITLE_KEY = "web_title";
    String RESOURCE_IDS_KEY = "resource_ids";

    String LESSON_INFO = "create_lesson_info";
    String UPGRADE_INFO_KEY = "upgrade_info";
    String UPGRADE_VERSION_KEY = "upgrade_version";

    String PROGRESS_KEY = "progress";
    String RECENT_START_KEY = "recent_start";
    String STATUS_KEY = "status";
    String STATUS_RELEASE_KEY = "release";
    String STATUS_DRAFT_KEY = "draft";
    String ISSUED_KEY = "ISSUED";
    String NOT_ISSUED_KEY = "NOT_ISSUED";
    String APPROVAL_KEY = "APPROVAL";

    String P_ENTERPRISE_ID = "{enterprise-id}";
    String P_USER_ID = "{user-id}";
    String P_COURSE_ID = "{course-id}";
    String P_RESOURCE_ID = "{resource-id}";
    String P_LOGIN_ACCOUNT_ID = "{login-account}";
    String P_LOGIN_TRANSITION_ID = "{login-transition}";
    String P_DEPARTMENT_ID = "{department-id}";
    String P_TEST_ENV = "{test-env}";
    String ENV_TYPE = "env_type";

    String MEDIA_TYPE_MP4 = "mp4";
    String MEDIA_TYPE_MP3 = "mp3";
    String MEDIA_TYPE_PPT = "ppt";
    String MEDIA_TYPE_PPTX = "pptx";
    String MEDIA_TYPE_DOC = "doc";
    String MEDIA_TYPE_DOCX = "docx";
    String MEDIA_TYPE_PDF = "pdf";
    String MEDIA_TYPE_XLS = "xls";
    String MEDIA_TYPE_XLSX = "xlsx";
    String MEDIA_TYPE_TXT = "txt";
    String MEDIA_TYPE_ARTICLE = "article";
    String MEDIA_TYPE_LINK = "link";
    String MEDIA_PARAMS = "media_params";

    String ACTION_LESSON_DETAIL = "lesson_detail";
    String ACTION_DISCOVER = "discover";
    String ACTION_CLOSE_MEDIA_PLAY = "close_media_play_big";
    String ACTION_NOTIFICATION_CLICK_TEXT = "com.coolcollege.notification.action.NOTIFICATION_CLICK_TEXT";
    String ACTION_NOTIFICATION_CLICK_OA = "com.coolcollege.notification.action.NOTIFICATION_CLICK_OA";
    String ACTION_PUSH_MESSAGE_CLICK = "com.coolcollege.notification.action.PUSH_MESSAGE_CLICK";
    String ACTION_WEB_RESET_PASSWORD = "com.coolcollege.notification.action.WEB_RESET_PASSWORD";
    String ACTION_APP_UPGRADE = "com.coolcollege.notification.action.APP_UPGRADE";
    String ACTION_FIRST_CONFIG = "com.coolcollege.notification.action.FIRST_CONFIG";
    String ACTION_SERVER_CONFIG = "com.coolcollege.notification.action.SERVER_CONFIG";

    String ACTION_PICK_IMG = "com.coolcollege.notification.action.PICK_IMG";
    String ACTION_PICK_VIDEO = "com.coolcollege.notification.action.PICK_VIDEO";

    String ACTION_PICK_IMG_RESULT = "com.coolcollege.notification.action.PICK_IMG_RESULT";
    String ACTION_PICK_VIDEO_RESULT = "com.coolcollege.notification.action.PICK_VIDEO_RESULT";

    String ACTION_VIDEO_RECORD = "com.coolcollege.notification.action.VIDEO_RECORD";
    String ACTION_AUDIO_RECORD = "com.coolcollege.notification.action.AUDIO_RECORD";
    String ACTION_VIDEO_RECORD_RESULT = "com.coolcollege.notification.action.VIDEO_RECORD_RESULT";
    String ACTION_AUDIO_RECORD_RESULT = "com.coolcollege.notification.action.AUDIO_RECORD_RESULT";

    String ACTION_CHECK_APP_UPDATE = "com.coolcollege.notification.action.CHECK_APP_UPDATE";

    String ACTION_REFRESH_LESSON_LIST = "refresh_lesson_list";
    String ACTION_PREVIEW_VIDEO = "preview_video";
    String TYPE_SELECT_AUDIO = "type_audio";
    String TYPE_SELECT_VIDEO = "type_video";
    String TYPE_VIDEO = "video";
    String TYPE_AUDIO = "audio";
    String ACTION_SELECT_TYPE = "select_type";
    String ACTION_CREATE_TYPE = "create_action";
    String ACTION_EDIT_TYPE = "edit";
    String ACTION_NEW_TYPE = "new";

    String ACTION_REGISTER = "register";
    String ACTION_RESET = "reset";
    String ACTION_WEB_RESET = "web_reset";

    String ACTION_UPDATE_LIST_FROM_EDITOR = "update_list_from_editor";
    String ACTION_UPDATE_LIST_WHEN_EDITOR_CLOSE = "update_list_when_editor_close";
    String ACTION_LOGIN_SUCCESS = "login_success";
    String ACTION_LOGIN_OUT = "login_out";

    String NOTIFICATION_TYPE_TEXT = "1";
    String NOTIFICATION_TYPE_URL = "2";


    String COMPANY_KEY = "company";
    String COMPANY_NAME_KEY = "company_name";
    String COMPANY_PRIMARY_COLOR_KEY = "company_primary_color";
    String COMPANY_BACKGROUND_COLOR_KEY = "company_background_color";
    String USER_BEAN_KEY = "user";
    String MOBILE_KEY = "mobile";
    String NEW_PASSWORD_KEY = "new_password";
    String VERIFICATION_CODE_KEY = "verification_code";

    String LEVEL_TYPE_GROUP = "group_level";
    String LEVEL_TYPE_MEMBER = "member_level";

    String DATA_VISIBLE_RANGE_LIST = "visible_range_list";
    String SELECT_IMG_PARAMS = "params";
    String SOURCE_TYPE = "img";
    String SIZE_TYPE = "compressed";
    String CHOOSE_TYPE_CAMERA = "camera";
    String IMAGE_LIST_KEY = "img_list";
    String AUDIO_MATERIAL_KEY = "audio_material";
    String PAGER_POSITION_KEY = "pager_position";

    int WE_CHAT = 1;
    int DING_DING = 2;
    int WE_CHAT_MOMENT = 3;

    String STATE_RUNNING = "Running";
    String STATE_FAILED = "Failed";
    String STATE_FINISHED = "Finished";

    String LESSON_STATUS_NORMAL = "normal";
    String LESSON_STATUS_REJECT = "reject";

    String VISIBLE_RANGE_SELECTED_KEY = "visible_range_selected_list";

    String SHARE_WE_CHAT_PARAMS = "&isGroup=WE_CHAT";
    String SHARE_MOMENT_PARAMS = "&isGroup=MOMENT";

    String EN_PARAMS = "?lang=en";
    String ZH_PARAMS = "?lang=zh";
    String ZH_HK_PARAMS = "?lang=zh_hk";

    String LANG_EN = "en_us";
    String LANG_ZH = "zh_cn";
    String LANG_ZH_HK = "zh_hk";


    String REQUEST_CANCELED = "Canceled";

    String ALL_PIC = "all_pic";
    String ALL_VIDEO = "all_video";

    String INPUT_IMG_TYPE = "image/*";
    String INPUT_VIDEO_TYPE = "video/*";
    String INPUT_AUDIO_TYPE = "audio/*";
    String CLEAN_ENV = "clean_env";

    String REFERER_KEY = "Referer";

    String MAX_COUNT_KEY = "max_count";
    String COMPRESSED_KEY = "compressed";
    String PERCENT_KEY = "percent";
    String SOURCE_TYPE_KEY = "source_type";

    String IMG_PATH_LIST_KEY = "img_path_list";
    String VIDEO_PATH_LIST_KEY = "video_path_list";
    String FILE_PATH_KEY = "file_path";

    String VERSION_NAME_KEY = "versionName";
    String VERSION_CODE_KEY = "versionCode";
    String APP_ID_KEY = "appId";
    String APP_NAME_KEY = "appName";
    String APK_PATH_KEY = "apk_path";

    String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwp+dZYUGEJ8ixBevMmANELzri9jUet/m5AR8VkrktzuZZ0B0X+F+DYiYWmSn0/2MSWRxtiVU7d2ZzPYodBelubnH3YgxcOKJ8ILaLlCkWZd30L68MAO3JG65NWIgCDq9d0BFiOY6XzRFav2E5+u33hVk1jWspTlgL9ylaEB5ynQIDAQAB";

    // 新登陆接口
    // 密码
    String ACCOUNT_PASSWORD_LOGIN = "account_password_login";
    // 验证码
    String ACCOUNT_SMSCODE_LOGIN = "account_smscode_login";
    // 是否加密 1：加密
    String PASSWORD_ENCRYPTED = "password_encrypted";
    // 验证码
    String SMS_CODE = "sms_code";
}
