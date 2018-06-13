package hotel.ejb.services;

public enum SessionObject {
	ROOM_ID("roomId", Long.class);
	
	private String name;
	private Class<?> objectClass;
	
	private SessionObject(String name, Class<?> objectClass) {
		this.name = name;
		this.objectClass = objectClass;
	}
	
	public String getName() {
		return name; 
	}
	
	public Class<?> getObjectClass(){
		return objectClass;
	}
}
