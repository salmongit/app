package com.salmon.jpa.core.page;

import java.io.Serializable;
import java.util.List;

public class PageView<T> implements Serializable{

	private static final long serialVersionUID = -2848617662263333556L;
	/** 分页数据 **/
	private List<T> records;
	/** 页码开始索引和结束索引 **/
	private PageIndex pageIndex;
	/** 总页数 **/
	private long totalPage = 1;
	/** 每页显示记录数 **/
	private int maxResult = 25;
	/** 当前页 **/
	private int currentPage = 1;
	/** 总记录数 **/
	private long totalRecord;
	/** 页码数量 **/
	private int pageCode = 10;

	/** 要获取记录的开始索引 **/
	public int getFirstIndex() {
		return (this.currentPage - 1) * this.maxResult;
	}

	public int getPageCode() {
		return pageCode;
	}

	public void setPageCode(int pageCode) {
		this.pageCode = pageCode;
	}

	public PageView(int maxResult, int currentPage) {
		this.maxResult = maxResult;
		this.currentPage = currentPage;
	}

	public void setQueryResult(QueryResult<T> qr) {
		setTotalRecord(qr.getTotalRow());
		setRecords(qr.getContent());
	}

	public long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
		//也可以用注释的内容(下面这行)计算页数
		//this.totalRecord % this.maxResult == 0 ? this.totalRecord / this.maxResult : this.totalRecord / this.maxResult + 1
		setTotalPage((this.totalRecord + this.maxResult - 1) / this.maxResult);
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public PageIndex getPageIndex() {
		return pageIndex;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
		this.pageIndex = PageIndex.getPageIndex(pageCode, currentPage,totalPage);
	}

	public int getMaxResult() {
		return maxResult;
	}

	public int getCurrentPage() {
		return currentPage;
	}
}

