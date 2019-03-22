package com.excilys.computer_database.model;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageBuilder<T> {

	private static Logger log= LoggerFactory.getLogger(PageBuilder.class);
	
	private List<T> content;
	private Integer index;
	private Integer size;
	
	public Optional<Page<T>> build(){
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
		return Optional.of(new Page<T>(content,index,size));
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
}