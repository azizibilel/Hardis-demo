package fr.hardis.baz.demo.enums;

public enum FormatEnum {
	
	XML("xml"),JSON("json");
	
	private String code;
	
	FormatEnum(String code) {
		this.code =  code ;
	}

	public String getCode() {
		return code;
	}

	static public FormatEnum getByCode(String code) {
		for(FormatEnum e : values()) {
			if(e.getCode().equalsIgnoreCase(code)) {
				return e;
			}
		}
		return null;
	}
	
	
	

}
