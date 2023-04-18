package com.KoreaIT.sdy.demo.vo;

import lombok.Getter;

// 제네릭
public class ResultData<DT> {
	@Getter
	private String resultCode;
	@Getter
	private String msg;
	@Getter
	private DT data1;
	
	public static <DT> ResultData<DT> from(String resultCode, String msg) {
		return from(resultCode, msg, null);
	}
	
	public static <DT> ResultData<DT> from(String resultCode, String msg, DT data1) {
		ResultData<DT> rd = new ResultData<>();
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

	public static <DT>  ResultData<DT> newData(ResultData<?> Rd, DT newData) {
		return from(Rd.getResultCode(), Rd.getMsg(), newData);
	}

}
