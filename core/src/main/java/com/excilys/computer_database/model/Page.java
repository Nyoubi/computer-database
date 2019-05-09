package com.excilys.computer_database.model;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Sort;


public class Page<T> {
	private List<T> content;
	private Integer index;
	private Integer size;
	private Integer totalSize;
	private String url;
	private String search;
	private String order;
	
	private static List<Integer> sizeList = Arrays.asList(10,20,50,100);
		
	public enum orderEnum {
		
		DEFAULT("",Sort.by(Sort.Direction.ASC,"id")),
		
        NAME_ASC("nameAsc",Sort.by(Sort.Direction.ASC, "name")), 
        NAME_DESC("nameDesc",Sort.by(Sort.Direction.DESC, "name")), 
        
        INTRO_ASC("introAsc",Sort.by(Sort.Direction.ASC, "introduced")), 
        INTRO_DESC("introDesc",Sort.by(Sort.Direction.DESC, "introduced")), 
        
        DISCON_ASC("disconAsc",Sort.by(Sort.Direction.ASC, "discontinued")), 
        DISCON_DESC("disconDesc",Sort.by(Sort.Direction.DESC, "discontinued")), 
        
        COMPANY_ASC("companyAsc",Sort.by(Sort.Direction.ASC, "company.name")),
        COMPANY_DESC("companyDesc",Sort.by(Sort.Direction.DESC, "company.name"));
		
		private String tag;
		private Sort value;

		orderEnum (String tag, Sort value) {
			this.tag = tag;
			this.value=value;
	}
		
		public String getTag() {
			return this.tag;
		}
		
		public Sort getValue() {
			return this.value;
		}
	}

	public Page(String url, List<T> content,Integer totalSize, Integer index,Integer size, String search, String order) {
		
		this.url = url;
		this.content = content;
		this.totalSize = totalSize;
		this.index = Math.max(index,1);
		this.size = Math.max(size,1);
		this.search = search;
		this.order = order;

	}

	private String formatUrl(int index, int size, String search, String order) {
		return String.format("%s?%s=%d&%s=%d&%s=%s&%s=%s", url, "index", index, "size", size, "search", search, "order", order);
	}
	
	public String nextPage(){
		if(index*size < totalSize) {
			return formatUrl(index+1,size,search,order);
		}
		return formatUrl(index,size,search,order);
	}
	
	public Integer nextIndex() {
		if(index*size < totalSize) {
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
	
	public Integer getTotalSize() {
		return this.totalSize;
	}
	
	public Integer getStart () {
		int value = (int) Math.ceil((double)totalSize / size);
		if (index <= 3) {
			return 1;
		} else if (index + 2 <= value) {
			return index - 2;
		} else {
			return Math.max(value - 4,1) ;
		}
	}
	
	public Integer getEnd () {
		int value = (int) Math.ceil((double)totalSize / size);
		if (value <= 4) {
			return --value;
		} else {
			return Math.min(value,4);
		}
	}
	
	
}