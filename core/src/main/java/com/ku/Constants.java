package com.ku;

/**
 * Constant values used throughout the application.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public final class Constants {

	private Constants() {
		// hide me
	}

	// ~ Static fields/initializers
	// =============================================

	public static final String SUCCESS = "success";

	public static final String ERROR = "error";

	public static final String FAIL = "fail";

	public static final String FAILED = "failed";

	/**
	 * Assets Version constant
	 */
	public static final String ASSETS_VERSION = "assetsVersion";
	/**
	 * The name of the ResourceBundle used in this application
	 */
	public static final String BUNDLE_KEY = "ApplicationResources";

	/**
	 * File separator from System properties
	 */
	public static final String FILE_SEP = System.getProperty("file.separator");

	/**
	 * User home from System properties
	 */
	public static final String USER_HOME = System.getProperty("user.home")
			+ FILE_SEP;

	/**
	 * The name of the configuration hashmap stored in application scope.
	 */
	public static final String CONFIG = "appConfig";

	/**
	 * The name of the configuration hashmap stored in application scope.
	 */
	public static final String KU_CONFIG = "kuConfig";
	
	/**
	 * Session scope attribute that holds the locale set by the user. By setting
	 * this key to the same one that Struts uses, we get synchronization in
	 * Struts w/o having to do extra work or have two session-level variables.
	 */
	public static final String PREFERRED_LOCALE_KEY = "org.apache.struts2.action.LOCALE";

	/**
	 * The request scope attribute under which an editable user form is stored
	 */
	public static final String USER_KEY = "userForm";

	/**
	 * The request scope attribute that holds the user list
	 */
	public static final String USER_LIST = "userList";

	/**
	 * The request scope attribute that holds the merchant list
	 */
	public static final String MERCHANT = "merchant";

	/**
	 * The request scope attribute that holds the merchant list
	 */
	public static final String MERCHANT_LIST = "merchantList";

	/**
	 * The request scope attribute that holds the merchant list
	 */
	public static final String MERCHANT_TYPE_LIST = "merchantTypeList";

	/**
	 * The request scope attribute that holds the support request list
	 */
	public static final String SUPPORT_REQUEST_LIST = "supportRequestList";

	/**
	 * The request scope attribute for indicating a newly-registered user
	 */
	public static final String REGISTERED = "registered";

	/**
	 * The name of the Administrator role, as specified in web.xml
	 */
	public static final String ADMIN_ROLE = "ROLE_ADMIN";

	/**
	 * The name of the User role, as specified in web.xml
	 */
	public static final String USER_ROLE = "ROLE_USER";

	/**
	 * The name of the user's role list, a request-scoped attribute when
	 * adding/editing a user.
	 */
	public static final String USER_ROLES = "userRoles";

	/**
	 * The name of the available roles list, a request-scoped attribute when
	 * adding/editing a user.
	 */
	public static final String AVAILABLE_ROLES = "availableRoles";

	public static final String MERCHANT_TYPES = "merchantTypes";

	public static final String SR_OPEN = "open";

	public static final String SR_CLOSE = "close";

	/**
	 * The name of the CSS Theme setting.
	 * 
	 * @deprecated No longer used to set themes.
	 */
	public static final String CSS_THEME = "csstheme";

	public static final String GET = "GET";

	public static final String POST = "POST";

	public static final String STATUS_SUCCESS = "SUCCESS";

	public static final String STATUS_FAILED = "FAILED";

	public static final String APP_MODE = "app.mode";
	
	public static final String DATA_TYPE_STRING = "String";
	
	public static final String MERCHANT_TYPE_RECHARGE = "Recharge";
	
	public static final String MERCHANT_TYPE_BOOKING = "Booking";
	
	public static final String APP_URL_CONFIG_KEY = "app.url";
	
	public static final String APP_URL = "appUrl";
	
	public static final String APP_NAME_CONFIG_KEY = "app.name";
	
	public static final String APP_NAME = "appName";
	
	public static final String APP_TITLE_CONFIG_KEY = "app.title";
	
	public static final String APP_TITLE = "appTitle";
	
	public static final String APP_META_KEYWORD_CONFIG_KEY = "app.meta.keyword";
	
	public static final String APP_META_KEYWORD = "appMetaKeyword";
	
	public static final String APP_META_DESCRIPTION_CONFIG_KEY = "app.meta.description";
	
	public static final String APP_META_DESCRIPTION = "appMetaDescription";
	
	public static final String DEFAULT_SCHEDULER_GROUP = "ku";
	
	public static final int OFFER_TO_LOAD = 20;
}
