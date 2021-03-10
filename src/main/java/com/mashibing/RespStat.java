package com.mashibing;

/**
 * JSON报文
 * @author humm
 */
public class RespStat {

	/**
	 * 状态码
	 * 用于前端判断   200  = 成功
	 * 400\500 出错
	 * 
	 * msg = 信息
	 */
	private int code;
	private String msg;
	private String data;
	
		
	public RespStat() {
		super();
	}
	
	public RespStat(int code, String msg, String data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

	public static RespStat build(int i) {
		return new RespStat(200, "ok", "haveNot");
	}

	public static RespStat build(int code, String msg) {
		return new RespStat(code, msg, "haveNot");
	}
	
	
	
	
}