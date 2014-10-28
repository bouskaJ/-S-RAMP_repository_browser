package background;

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;
import org.overlord.sramp.client.query.ArtifactSummary;
import org.overlord.sramp.client.query.QueryResultSet;


public class m {
	
	private static Table table;
	private static BrowserManager manager;
	
	public static void main(String args [])
	{
		Display myDisplay = new Display();
	      Shell myShell = new Shell(myDisplay);
	      myShell.setText("Sample");
	      myShell.setBounds(120, 120, 220, 120);
	      myShell.setLayout(new FillLayout());
	      
		table = new Table(myShell, SWT.MULTI | SWT.BORDER
		| SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		String[] titles = { "ID", "BRAND", "TYPE"};
		
		for (int i = 0; i < titles.length; i++) {
		TableColumn column = new TableColumn(table, SWT.NONE);
		column.setText(titles[i]);
		}
		
		 manager = new BrowserManagerImpl();
			
	        	manager.setConnection("http://localhost:8080/s-ramp-server","admin","jejdri2nat");
	        	
	        	

	      			
	      	        	QueryResultSet content = manager.listAllArtifacts();
	      	        	
	    	        	for( ArtifactSummary as :content)
	    	        	{
	    	        		TableItem item = new TableItem(table, SWT.NONE);
	    	        		item.setText(0,as.getName());
	    	        		item.setText(1, as.getType().toString());
	    	        		item.setText(2, as.getCreatedBy());
	    	        	}
	    	        	
	    	        	for (int i = 0; i < 3; i++) {
	    	        	table.getColumn(i).pack();
	    	}
	    	        	
	    	        	
	      	            
	      	          
	      	       
	      	
	      	     
	      	     
	        	
	        	
	        	myShell.open ();
	    		while (!myShell.isDisposed()) {
	    			if (!myDisplay.readAndDispatch())
	    				myDisplay.sleep ();
	    		}
	}
	
			
}


