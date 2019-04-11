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
        NAME_ASC("nameAsc","ORDER BY c.name"), 
        NAME_DESC("nameDesc","ORDER BY c.name DESC"), 
        
        INTRO_ASC("introAsc","ORDER BY c.introduced IS NULL, c.introduced"), 
        INTRO_DESC("introDesc","ORDER BY c.introduced DESC"), 
        
        DISCON_ASC("disconAsc","ORDER BY c.discontinued IS NULL, c.discontinued"), 
        DISCON_DESC("disconDesc","ORDER BY c.discontinued  DESC"), 
        
        COMPANY_ASC("companyAsc","ORDER BY cName IS NULL, cName"), 
        COMPANY_DESC("companyDesc","ORDER BY cName DESC");
		
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
		if (index <= 3) {
			return 1;
		} else if (index >= Math.round(content.size() / size)) {
			return  (int) (Math.round(content.size() / size) - 3);
		}
		return index - 2;
	}
	
	public Integer getEnd () {
		return Math.min(Math.round(content.size() / size),4);
	}
	
	
}