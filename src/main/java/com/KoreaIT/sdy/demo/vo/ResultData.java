package com.KoreaIT.sdy.demo.vo;

import lombok.Getter;

public class ResultData {
	@Getter
	private String resultCode;
	@Getter
	private String msg;
	@Getter
	private Object data1;
	
	public static ResultData from(String resultCode, String msg) {
		return from(resultCode, msg, null);
	}
	
	public static ResultData from(String resultCode, String msg, Object data1) {
		ResultData rd = new ResultData();
		rd.resultCode = resultCode;
		rd.msg = msg;
		rd.data1 = data1;
		
		return rd;
	}
	
	public boolean isSuccess() { 
		// private boolean success한 것과 같다.
		return resultCode.startsWith("S-");
		// 출력형태 -> "success": true || false 
	}
	
	public boolean isFail() { 
		// private boolean fail한 것과 같다.
		return isSuccess()==false;
		// 출력형태 -> "fail": true || false
	}

	public static ResultData newData(ResultData joinRd, Object newData) {
		return from(joinRd.getResultCode(), joinRd.getMsg(), newData);
	}

}
