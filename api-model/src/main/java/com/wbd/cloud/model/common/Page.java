package com.wbd.cloud.model.common;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 * @author jwh
 *
 */
public class Page<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//总共记录数
	private int total;
	//每页记录列表
	private List<T> data;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public Page(int total, List<T> data) {
		super();
		this.total = total;
		this.data = data;
	}
	public Page() {
		super();
	}
	
}
