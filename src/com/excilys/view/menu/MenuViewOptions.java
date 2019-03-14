package com.excilys.view.menu;

public enum MenuViewOptions {
	ERROR(-1),
	LIST_COMPUTERS(1),
	LIST_COMPANIES(2),
	COMPUTER_DETAILS(3),
	CREATE_COMPUTER(4),
	UPDATE_COMPUTER(5),
	DELETE_COMPUTER(6),
	EXIT(7);
	
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