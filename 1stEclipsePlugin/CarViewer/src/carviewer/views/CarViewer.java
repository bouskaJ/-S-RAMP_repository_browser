package carviewer.views;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.*;



/**
 * Sample view
 * @author honza
 *
 */
public class CarViewer extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "carviewer.views.CarViewer";
	private Table table;
	private List<Car> list;

	
	/**
	 * The constructor.
	 */
	public CarViewer() {
		
		list = new ArrayList<>();
		list.add(new Car("UOJ-994","OPEL","ASTRA"));
		list.add(new Car("BRA-994","RENAULT","CLIO"));
		list.add(new Car("E25-994","OPEL","ASTRA"));
	
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {

		table = new Table(parent, SWT.MULTI | SWT.BORDER
		        | SWT.FULL_SELECTION);
		    table.setLinesVisible(true);
		    table.setHeaderVisible(true);
		
		String[] titles = { "ID",  "BRAND", "TYPE"};
		
		    for (int i = 0; i < titles.length; i++) {
		      TableColumn column = new TableColumn(table, SWT.NONE);
		      column.setText(titles[i]);
		    }
		    
		   
		    for (int i = 0; i < list.size(); i++) {
		      TableItem item = new TableItem(table, SWT.NONE);
		      item.setText(0,list.get(i).getId());
		      item.setText(1, list.get(i).getBrand());
		      item.setText(2, list.get(i).getType());
		      
		    }
		    for (int i = 0; i < titles.length; i++) {
		      table.getColumn(i).pack();
		    }
		    
		
	}
	
	public void setFocus() {
		table.setFocus();
	}
}

	