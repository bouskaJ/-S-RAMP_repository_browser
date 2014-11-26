package cz.muni.fi.srampRepositoryBrowser.UI;


public class PropData{
	private String name;
	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	private String value;
	
	public PropData(String name,String value)
	{
		this.name = name;
		this.value = value;
	}
}


