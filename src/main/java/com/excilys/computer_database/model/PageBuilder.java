package com.excilys.computer_database.model;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.exception.ExceptionModel;

public class PageBuilder<T> {

	private static Logger log= LoggerFactory.getLogger(PageBuilder.class);
	
	private List<T> content;
	private Integer index;
	private Integer size;
	private String url;
	public Optional<Page<T>> build() throws ExceptionModel{
		if(content == null) {
			log.warn("Can't build a page without data, return empty");
			return Optional.empty();
		}
		if(index == null) {
			index = Integer.valueOf(0);
		}
		if(size == null) {
			size = Integer.valueOf(10);
		}
		if(url == null) {
			throw new ExceptionModel("An url is needed");
		}
		
		return Optional.of(new Page<T>(url,content,index,size));
	}
	
	public PageBuilder<T> setContent(List<T> list) {
		this.content = list;
		return this;
	}
	
	public PageBuilder<T> setIndex(Integer index) {
		if(this.content == null || index > this.content.size()) {
			log.warn("Can't initialize index without content");
		} else {
			this.index = index;
		}
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
}