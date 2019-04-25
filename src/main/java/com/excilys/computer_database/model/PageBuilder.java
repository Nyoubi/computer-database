package com.excilys.computer_database.model;

import java.util.List;

public class PageBuilder<T> {
	
	private List<T> content;
	private Integer index;
	private Integer size;
	private Integer totalSize;
	private String url;
	private String search;
	private String order;
	
	public Page<T> build(){
		return new Page<T>(url,content,totalSize,index,size,search,order);
	}
	
	public PageBuilder<T> setContent(List<T> list) {
		this.content = list;
		return this;
	}
	
	public PageBuilder<T> setIndex(Integer index) {
		this.index = index;
		return this;
	}
	
	public PageBuilder<T> setSize(Integer size) {
		this.size = size;
		return this;
	}
	
	public PageBuilder<T> setUrl(String url) {
		this.url = url;
		return this;
	}
	
	public PageBuilder<T> setSearch(String search) {
		this.search = search;
		return this;
	}
	public PageBuilder<T> setOrder(String order) {
		this.order = order;
		return this;
	}
	
	public PageBuilder<T> setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
		return this;
	}
	
	public Integer getIndex() {
		return this.index;
	}
}