package carviewer.views;

public class Car {
	
	private String id;
	private String brand;
	private String type;
	
	Car(String id, String brand, String type)
	{
		this.id = id;
		this.brand =brand;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public String getBrand() {
		return brand;
	}

	public String getType() {
		return type;
	}
	
	
}
