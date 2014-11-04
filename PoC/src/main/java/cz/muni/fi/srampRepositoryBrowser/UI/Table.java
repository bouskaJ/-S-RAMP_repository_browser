package cz.muni.fi.srampRepositoryBrowser.UI;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.overlord.sramp.client.query.ArtifactSummary;
import org.overlord.sramp.client.query.QueryResultSet;

import cz.muni.fi.srampRepositoryBrowser.background.BrowserManager;
import cz.muni.fi.srampRepositoryBrowser.background.BrowserManagerImpl;



public class Table extends Composite {
	private org.eclipse.swt.widgets.Table table;
	private QueryResultSet content;
	
	
	
	
	private void updateTable()
	{
		Display.getDefault().asyncExec(new Runnable() {
		      public void run() {
		    	 
		    	  for (ArtifactSummary as : content) {
		  			TableItem item = new TableItem(table, SWT.NULL);
		              item.setText(new String [] {as.getName(), as.getType().getLabel(),as.getLastModifiedTimestamp().toString()
		            		  ,as.getCreatedTimestamp().toString()});
		              
		              
		  		}
		  			
		  			
		      }
		    });
	}
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public Table(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		table = new org.eclipse.swt.widgets.Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		String[] titles = { "NAME", "TYPE", "LAST MODIFIED","DATE CREATED"  };
		
		for (int i =0; i<titles.length;i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(titles[i]);
			
			
		}
		
		
		
		
		    Job job = new Job("fill table") {
		    	
		      @Override
		      protected IStatus run(IProgressMonitor monitor) {
		  		BrowserManager bm = new BrowserManagerImpl();
				bm.setConnection("http://localhost:8080/s-ramp-server", "admin", "jejdri2nat");
				content  = bm.listAllArtifacts();
				
				  updateTable();
		    	  
		    	  
		        return Status.OK_STATUS;
		      }

			

		    };
		   
		    job.setUser(true);
		    job.schedule();
		  

		
		
/*
		BrowserManager bm = new BrowserManagerImpl();
		bm.setConnection("http://localhost:8080/s-ramp-server", "admin", "jejdri2nat");
		
		
		
		QueryResultSet qr  = bm.listAllArtifacts();
		for (ArtifactSummary as : qr) {
			TableItem itemN = new TableItem(table, SWT.NONE);
            itemN.setText(as.getName());
            TableItem itemT = new TableItem(table, SWT.NONE);
            itemT.setText(as.getType().getLabel());
            TableItem itemLM = new TableItem(table, SWT.NONE);
            itemLM.setText(as.getLastModifiedTimestamp().toString());
            TableItem itemDC = new TableItem(table, SWT.NONE);
            itemDC.setText(as.getCreatedTimestamp().toString());
		}
			*/
			
		    
		    for (int i = 0; i < titles.length; i++) {
				table.getColumn(i).pack();
			}

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
