package com.lzc.serizable;

import java.io.Serializable;

import org.msgpack.annotation.Message;

@Message
public class User implements Serializable 
{

	private static final long serialVersionUID = 1L;

	private String name;

	private String pwd;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
