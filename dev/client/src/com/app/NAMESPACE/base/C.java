package com.app.NAMESPACE.base;

public final class C {
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// server configs (editable)
	
	public static final class api {
		public static final String base				= "http://192.168.41.37:8001";
		public static final String index			= "/index/index";
		public static final String login			= "/index/login";
		public static final String logout			= "/index/logout";
		public static final String blogList			= "/blog/blogList";
		public static final String blogView			= "/blog/blogView";
		public static final String blogCreate		= "/blog/blogCreate";
	}
	
	public static final class web {
		public static final String base				= "http://192.168.41.37:8002";
		public static final String index			= base + "/index.php";
		public static final String gomap			= base + "/gomap.php";
	}
	
	public static final class err {
		public static final String network			= "网络错误";
		public static final String message			= "消息错误";
		public static final String jsonFormat		= "消息格式错误";
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// client configs (protected)
	
	public static final class task {
		public static final int login				= 1001;
		public static final int logout				= 1002;
		public static final int welcome				= 1003;
		public static final int blogList			= 1004;
		public static final int blogView			= 1005;
		public static final int blogCreate			= 1006;
	}
	
	public static final class intent {
		public static final class action {
			public static final String EDIT			= "com.app.NAMESPACE.EDIT";
		}
	}
	
	public static final class action {
		public static final class edit {
			public static final int CONF			= 2001;
		}
	}
}