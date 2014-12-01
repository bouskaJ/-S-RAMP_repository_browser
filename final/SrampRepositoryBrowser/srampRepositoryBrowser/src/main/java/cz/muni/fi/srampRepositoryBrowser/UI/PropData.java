package cz.muni.fi.srampRepositoryBrowser.UI;

/**
 * Class represents the property data (name and value).
 * @author Jan Bouska
 *
 */
public class PropData{
	private String name;
	private String value;
	
	/**
	 * 
	 * @return return property name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return return property value
	 */
	public String getValue() {
		return value;
	}

	
	/**
	 * Constructor.
	 * @param name property name
	 * @param value property value
	 */
	public PropData(String name,String value)
	{
		this.name = name;
		this.value = value;
	}
}


