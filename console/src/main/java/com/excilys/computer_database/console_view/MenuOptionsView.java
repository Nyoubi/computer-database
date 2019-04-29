package com.excilys.computer_database.console_view;

public enum MenuOptionsView {
	ERROR(-1),
	DELETE_COMPANY(1),
	EXIT(2);
	
	private final Integer id;
	
	MenuOptionsView(Integer id) {
		this.id = id;
	}
	
	public static MenuOptionsView getById(Integer id) {
		MenuOptionsView[] options = MenuOptionsView.values();
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