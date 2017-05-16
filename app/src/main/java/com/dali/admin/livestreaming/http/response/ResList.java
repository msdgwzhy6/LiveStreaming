package com.dali.admin.livestreaming.http.response;


import com.dali.admin.livestreaming.model.LiveInfo;

import java.io.Serializable;
import java.util.List;


public class ResList  implements Serializable {

	private int pageIndex;
	private int pageSize;

	private List<LiveInfo> items;

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<LiveInfo> getItems() {
		return items;
	}

	public void setItems(List<LiveInfo> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "ResList{" +
				"currentPage=" + pageIndex +
				", totalRow=" + pageSize +
				", totalPage="  +
				", items=" + items +
				'}';
	}
}
