package com.excilys.computer_database.console_view;

public enum MenuOptionsView {
	ERROR(-1),
	LIST_COMPUTER(1),
	SHOW_COMPUTER(2),
	CREATE_COMPUTER(3),
	UPDATE_COMPUTER(4),
	DELETE_COMPANY(5),
	EXIT(4);
	
	private final Integer id;
	
	MenuOptionsView(Integer id) {
		this.id = id;
	}
	
	public static MenuOptionsView getById(Integer id) {
		MenuOptionsView[] options = MenuOptionsView.values();
		Integer i = 0;
		
		while(i < options.length) {
			if(options[i].getId().equals(id)) {
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