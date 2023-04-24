package com.KoreaIT.sdy.demo.util;

import javax.servlet.http.HttpServletRequest;

public class Ut {

	public static boolean empty(Object obj) {
		if(obj==null) {
			return true;
		}
		if(obj instanceof String == false) { // object가 String이니?, 타입 검사
			String str = (String) obj;
			return str.trim().length()==0;
		}
		
		return false;
	}

	public static String f(String format, Object... args) { // 가변인자
		return String.format(format, args);
	}

	public static String jsHistroyBack(String ResultCode, String msg) {
		
		if(msg==null) {
			msg="";
		}
		
		// """ """사용하면 자바스크립트를 사용할 수 있다.
		return Ut.f("""
				<script>
					const msg = '%s'.trim();
					if ( msg.length > 0 ) {
						alert(msg);
					}
					history.back();
				</script>
				""", msg);
	}
	
	public static String jsReplace(String msg, String uri) {
		if (msg == null) {
			msg = "";
		}
		if (uri == null) {
			uri = "/";
		}

		return Ut.f("""
					<script>
					const msg = '%s'.trim();
					if ( msg.length > 0 ){
						alert(msg);
					}
					location.replace('%s');
				</script>
				""", msg, uri);

	}

	public static String jsHistroyBackOnView(HttpServletRequest req, String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);
		
		return "usr/common/js";
	}

}
