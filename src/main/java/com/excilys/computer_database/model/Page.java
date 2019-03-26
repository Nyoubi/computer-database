package com.excilys.computer_database.model;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {
	private List<T> content;
	private Integer index;
	private Integer size;
	private List<Integer> sizeList =new ArrayList<Integer>();
	public Page(List<T> content,Integer index,Integer size) {
		this.content = content;
		this.index = Math.max(index,1);
		this.size = Math.max(size,1);
		
		sizeList.add(10);
		sizeList.add(20);
		sizeList.add(50);
		sizeList.add(100);
	}
	
	public Integer nextPage(){
		if(index*size < content.size()) {
			return index + 1;
		}else{
			return index;
		}
	}
	public Integer previousPage(){
		if(index * size > size) {
			return index - 1;
		}
		else {
			return 1;
		}
	}
	
	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index =  Math.max(index,1);
	}

	public Integer getSize() {
		return size;
	}

	public Integer setSize(Integer size) {
		if (sizeList.indexOf(size) != -1) {
			this.size = Math.max(size,10);	
		} else {
			this.size = sizeList.get(0);
		}
		return this.size;
	}
	
	public List<Integer> getSizeList () {
		return this.sizeList;
	}
	
	public Integer getContentSize() {
		return content.size();
	}
	
	public List<T> getContent(){
		return content;
	}
	
	public List<T> getPageContent(){
		Integer from = Math.min(content.size()/size*size,(index-1)*size);
		Integer to = Math.min(((index-1)*size)+size,content.size());
		return content.subList(from,to);
	}
	
	public Integer getStart () {
		if (index < 3) {
			return 1;
		} else if (index >= Math.round(content.size() / size)) {
			return  (int) (Math.round(content.size() / size) - 3);
		}
		return index - 2;
	}
}