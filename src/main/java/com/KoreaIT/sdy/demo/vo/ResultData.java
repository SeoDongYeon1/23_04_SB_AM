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
	
	public boolean isSuccess() { // 출력형태 -> "success": true || false
		return resultCode.startsWith("S-");
	}
	
	public boolean isFail() { // 출력형태 -> "fail": true || false
		return isSuccess()==false;
	}

}
