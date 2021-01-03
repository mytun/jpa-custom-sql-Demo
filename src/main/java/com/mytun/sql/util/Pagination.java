package com.mytun.sql.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Pagination<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 总笔数
	 */
	@JsonProperty("count")
	private long total;//总笔数
	@JsonProperty("data")
	private List<T> rows;
	String msg="";
	int code = 0;
	/**
	 *当前页
	 */
	private long page;//当前页
	/**
	 * 总页数
	 */
	private long tp;//总页数
	/**
	 * 每页笔数
	 */
	private long pagesize;//每页笔数
	
	private long start;
	
	
	
	public Pagination(int page, int limit){
		this.page = page;
		this.pagesize = limit;
		this.start = this.page*limit;
	}
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public long getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}

	public long getTp() {
		if(this.pagesize<=0) return 0;
		this.tp = this.total/this.pagesize;
		if(this.total % this.pagesize>0) this.tp++;
		return this.tp;
	}
	public void setTp(int tp) {
		this.tp = tp;
	}
	public long getPagesize() {
		return pagesize;
	}
	public void setPagesize(long limit) {
		this.pagesize = limit;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setTp(long tp) {
		this.tp = tp;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
