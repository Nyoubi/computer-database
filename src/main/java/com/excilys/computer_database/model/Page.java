package com.excilys.computer_database.model;

import java.util.Arrays;
import java.util.List;

public class Page<T> {
	private List<T> content;
	private Integer index;
	private Integer size;
	private String url;
	private String search;
	private String order;
	
	private static List<Integer> sizeList = Arrays.asList(new Integer[]{10,20,50,100});
	
	public enum orderEnum {
		
		DEFAULT("","c.id ASC"),
		
        NAME_ASC("nameAsc","c.name ASC"), 
        NAME_DESC("nameDesc","c.name DESC"), 
        
        INTRO_ASC("introAsc","c.introduced IS NULL, c.introduced ASC"), 
        INTRO_DESC("introDesc","c.introduced DESC"), 
        
        DISCON_ASC("disconAsc","c.discontinued IS NULL, c.discontinued ASC"), 
        DISCON_DESC("disconDesc","c.discontinued  DESC"), 
        
        COMPANY_ASC("companyAsc","cName IS NULL, cName ASC"), 
        COMPANY_DESC("companyDesc","cName DESC");
		
		private String tag;
		private String value;

		orderEnum (String tag, String value) {
			this.tag = tag;
			this.value=value;
	}
		
		public String getTag() {
			return this.tag;
		}
		
		public String getValue() {
			return this.value;
		}
	}

	public Page(String url, List<T> content,Integer index,Integer size, String search, String order) {
		
		this.url = url;
		this.content = content;
		this.index = Math.max(index,1);
		this.size = Math.max(size,1);
		this.search = search;
		this.order = order;

	}

	private String formatUrl(int index, int size, String search, String order) {
		return String.format("%s?%s=%d&%s=%d&%s=%s&%s=%s", url, "index", index, "size", size, "search", search, "order", order);
	}
	
	public String nextPage(){
		if(index*size < content.size()) {
			return formatUrl(index+1,size,search,order);
		}
		return formatUrl(index,size,search,order);
	}
	
	public Integer nextIndex() {
		if(index*size < content.size()) {
			return index+1;
		}
		return index;
	}
	
	public String previousPage(){
		if(index * size > size) {
			return formatUrl(index-1,size,search,order);
		}
		else {
			return formatUrl(1,size,search,order);
		}
	}
	
	public Integer previousIndex() {
		if(index*size > size) {
			return index-1;
		}
		return 1;
	}
	
	public String indexAt(Integer index) {
		return formatUrl(index,size,search,order);
	}

	public void setIndex(Integer index) {
		this.index =  Math.max(index,1);
	}

	public Integer getSize() {
		return size;
	}

	public String setSize(Integer size) {
		if (sizeList.indexOf(size) != -1) {
			return formatUrl(1,size,search,order);
		} else {
			return formatUrl(1,sizeList.get(0),search,order);
		}
	}
	
	public void setSearch(String search) {
		if (search != null && !search.equals("")) {
			this.search = search;
		} else {
			this.search = "";
		}
	}
	
	public String getOrder(String order) {
		return formatUrl(index,size,search,order);
	}
	
	public void setOrder(String order) {
		this.order = order;
	}
	
	public static List<Integer> getSizeList () {
		return sizeList;
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
		int value = (int) Math.ceil((double)content.size() / size);
		if (index <= 3) {
			return 1;
		} else if (index + 2 <= value) {
			return index - 2;
		} else {
			return Math.max(value - 4,1) ;
		}
	}
	
	public Integer getEnd () {
		int value = (int) Math.ceil((double)content.size() / size);
		if (value <= 4) {
			return --value;
		} else {
			return Math.min(value,4);
		}
	}
	
	
}