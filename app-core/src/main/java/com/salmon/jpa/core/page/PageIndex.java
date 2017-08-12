package com.salmon.jpa.core.page;

import java.io.Serializable;

public class PageIndex implements Serializable {

	private static final long serialVersionUID = -6706826676365231599L;
	private long startPage;
	private long endPage;

    public PageIndex(long startPage, long endPage) {
        this.startPage = startPage;
        this.endPage = endPage;
    }

    public long getStartPage() {
        return startPage;
    }

    public void setStartPage(long startPage) {
        this.startPage = startPage;
    }

    public long getEndPage() {
        return endPage;
    }

    public void setEndPage(long endPage) {
        this.endPage = endPage;
    }

    /**
	 * 返回PageIndex
	 * @param viewPageCount 页码数量
	 * @param currentPage 当前页
	 * @param totalPage 总页数
	 * @return
	 */
	public static PageIndex getPageIndex(long viewPageCount, int currentPage, long totalPage) {
		long startPage = currentPage - (viewPageCount % 2 == 0 ? viewPageCount / 2 - 1 : viewPageCount / 2);
		long endPage = currentPage + viewPageCount / 2;
		if (startPage < 1) {
			startPage = 1;
			if (totalPage >= viewPageCount)
				endPage = viewPageCount;
			else
				endPage = totalPage;
		}
		if (endPage > totalPage) {
			endPage = totalPage;
			if ((endPage - viewPageCount) > 0)
				startPage = endPage - viewPageCount + 1;
			else
				startPage = 1;
		}
		return new PageIndex(startPage, endPage);
    }

}

