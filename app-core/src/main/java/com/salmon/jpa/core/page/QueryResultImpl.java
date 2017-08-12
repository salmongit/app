package com.salmon.jpa.core.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.salmon.jpa.core.utils.JacksonUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class QueryResultImpl<T> implements QueryResult<T> {

	private static final long serialVersionUID = -2994480749867253509L;

	@JsonProperty("data")
	private List<T> content;
	private Long totalRow = 0L;
	/**
	 * 每页显示的总记录数
	 */
	private Integer pageRow = 25;
	/**
	 * 从1开始的第几页
	 */
	private Integer curPage;
	private Integer totalPage;
	/**
	 * 从0开始的开始行数
	 */
	private Long beginRow;
	/**
	 * 从0开始的结束行数
	 */
	private Long endRow;

	/**
	 *
	 * @param totalRow 总共多少行
	 * @param start 开始行号(从0开始)
	 * @param pageRow 每页显示多少行记录
	 */
	public QueryResultImpl(Long totalRow, Long start, Integer pageRow){
		if(totalRow != null && totalRow > -1 && pageRow != null && pageRow > 0 && start != null && start > -1){
			Long curPage = start / pageRow + 1;
			if (curPage <= 0 && totalRow > 0) {
				curPage = 1L;
			}
			//还剩余的行数
			Long resetRow = totalRow - pageRow * (curPage-1);
			Long beginRow = (curPage - 1) * pageRow;
			Long endRow = beginRow + pageRow - 1;
			if(resetRow < pageRow){
				endRow = beginRow + resetRow - 1;
			}
			Long totalPage = 0L;
			if (totalRow % pageRow == 0L) {
				totalPage = totalRow / pageRow;
			} else {
				totalPage = totalRow / pageRow + 1;
			}
			this.beginRow = beginRow;
			this.curPage = curPage.intValue();
			this.endRow = endRow;
			this.pageRow = pageRow;
			this.totalPage = totalPage.intValue();
			this.totalRow = totalRow;
		}
		else{
			this.beginRow = null;
			this.curPage = null;
			this.endRow = null;
			this.pageRow = null;
			this.totalPage = null;
			this.totalRow = null;
		}
	}

	public boolean pageable(){
		return totalRow != null;
	}

	@Override
	public Pageable getPageable(Sort sort) {
		return new PageRequest(curPage, pageRow, sort);
	}

	@Override
	public String toJsonString() {
		return JacksonUtils.obj2json(this);
	}

	@Override
	public void setContent(List<T> content) {
		this.content = content;
	}
	/**
	 * 满足条件的所有记录的总行数
	 * @return
	 */
	public Long getTotalRow() {
		return totalRow;
	}
	/**
	 * 每页行数，不论本业实际条数为多少，返回分页的每页记录数
	 * @return
	 */
	public Integer getPageRow() {
		return pageRow;
	}
	/**
	 * 从1开始的第几页
	 */
	public Integer getCurPage() {
		return curPage;
	}
	/**
	 * 总页数
	 * @return 返回总页数
	 */
	public Integer getTotalPage() {
		return totalPage;
	}
	/**
	 * 基于0为第一行的本页实际的第一行在所有记录中的行号
	 * @return
	 */
	public Long getBeginRow() {
		return beginRow;
	}
	/**
	 * 基于0为第一行的本页实际的最后行在所有记录中的行号
	 * @return
	 */
	public Long getEndRow() {
		return endRow;
	}

	public List<T> getContent(){
		return content;
	}

}
