package com.miguelandaluz.oneboxcart.constants;

public enum Texts {
	
	WELCOME("Bienvenid@", "Welcome"),
	MENU_QUESTION("¿Qué te gustaría hacer?", "What would you like to do?"),
	MENU_INSTRUCTION("Introduce el valor numérico indicado para las opciones:",
			"Insert the number value for the given options:"),
	MENU_1("Crear nueva cesta de compra: 1", "Create new cart: 1"),
	MENU_2("Continuar con cesta ya existente: 2", "Continue with a previus cart: 2"),
	MENU_3("Eliminar cesta: 3", "Delete cart: 3"),
	
	CART_CRT_WLC("Nueva cesta", "New cart"),
	CART_CRT_END("Cesta creada con id: ", "Cart created with id: "),
	CART_CRT_1("Añadir prodcutos: 1", "Add products: 1"),
	CART_CRT_2("Volver al menú principal: 2" , "Return to main menu: 2"),
	
	PRD_ADD_WLC("Selecciona el producto que desees","Choose the product you want"),
	PRD_ADD_SEL(" opción: " , " option: "),
	PRD_ADD_INS("Inserta  el número de opción deseada: " , "Insert the number of the desired option: "),
	PRD_ADD_AMO("Inserta la cantidad deseada: " , "Insert the desired amount: "),
	PRD_ADD_MOR("¿Añadir más productos?","Would you like to add more produts?"),
	PRD_ADD_CONT("Añadir: 1 Finalizar: 0" , "Add: 1 End: 0"),
	
	CART_SRC_BCK("No hay cestas activas que mostrar", "No active carts to show"),
	CART_SRC_WLC("Gestor de cestas", "Cart management"),
	CART_SRC_LST("Cestas activas: ", "Active carts: "),
	CART_SRC_DAT(" - fecha de modificación: ", " - last access date: "),
	CART_SRC_INS("Inserte el id de la cesta: ", "Insert the cart id: "),
	
	CART_DEL_WLC("Borrado de cestas", "Cart eraser"),
	CART_DEL_INS("Inserte el id de la cesta a borrar: ", "Insert the cart id to delete: "),
	CART_DEL_NEX("La cesta con el id elegido no existe", "There is no cart with te chosen id"),
	CART_DEL_CPR("Vas a eliminar la cesta con id: ", "You are going to delete the cart with id: "),
	CART_DEL_QTN("¿Estás seguro?", "Are you sure?"),
	CART_DEL_AN1("Elegir otra cesta: 1", "Chose another cart: 1"),
	CART_DEL_AN2("Borrar cesta elegida: 2", "Delete chosen cart: 2"),
	CART_DEL_AN3("Salir al menu: 3", "Go back to menu: 3"),
	CART_DEL_TED("Cesta borrada", "Cart deleted"),
	
	
	NOT_AN_OPTION("Opción no disponible", "Option unsuported"),
	CRIT_ERROR("Error crítico en: ", "Critical Error in: ")
	;

	String esp;
	String eng;
	
	Texts(String esp, String eng) {
		this.esp = esp;
		this.eng = eng;
	}

	public String getEsp() {
		return esp;
	}

	public String getEng() {
		return eng;
	}	
}
