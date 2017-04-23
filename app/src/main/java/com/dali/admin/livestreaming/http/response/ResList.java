package com.dali.admin.livestreaming.http.response;


import com.dali.admin.livestreaming.http.IDontObfuscate;

import java.util.List;


public class ResList<T>  extends IDontObfuscate {

	public int pageIndex;
	public int pageSize;

	public List<T> items;

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
