package com.excilys.computer_database.view;

public enum MenuViewOptions {
	ERROR(-1),
	DELETE_COMPANY(1),
	EXIT(2);
	
	private final Integer id;
	
	MenuViewOptions(Integer id) {
		this.id = id;
	}
	
	public static MenuViewOptions getById(Integer id) {
		MenuViewOptions[] options = MenuViewOptions.values();
		Integer i = 0;
		
		while(i < options.length) {
			if(options[i].getId() == id) {
				return options[i];
			}else {
				i ++;
			}	
		}
		return ERROR;		
	}
	
	public Integer getId() {
		return this.id;
	}
}