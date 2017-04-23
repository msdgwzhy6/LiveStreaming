package com.dali.admin.livestreaming.http.response;


import com.dali.admin.livestreaming.http.IDontObfuscate;

public class Response<T>  extends IDontObfuscate {

	private int status;
	private String msg;
	private T data;
	@Override
	public String toString() {
		return "Response [code=" + status + ", msg=" + msg + ", data=" + data
				+ "]";
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}

