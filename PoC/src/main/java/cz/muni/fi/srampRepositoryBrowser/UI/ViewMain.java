package cz.muni.fi.srampRepositoryBrowser.UI;



import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.overlord.sramp.client.query.ArtifactSummary;
import org.overlord.sramp.client.query.QueryResultSet;

import cz.muni.fi.srampRepositoryBrowser.background.BrowserManager;

/**
 * class representing main UI
 * @author honza
 *
 */
public class ViewMain extends Composite {
	private Text text;
	private org.eclipse.swt.widgets.Table table;
	private QueryResultSet content;
	
	
	/**
	 * update table with data from content attribute
	 */
	public void updateTable()
	{
		for(ArtifactSummary as : content)
		{
			TableItem item = new TableItem(table,SWT.NONE);
			item.setText(new String [] {as.getName(),as.getType().getLabel(),as.getLastModifiedTimestamp().toString(),as.getCreatedTimestamp().toString()});
			
		}
	}
	
	
	private void createColumns()
	{
		String[] titles = { "NAME", "TYPE", "LAST MODIFIED","DATE CREATED"  };
		
		for (int i =0; i<titles.length;i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(titles[i]);
			
			
		}
		
		table.getColumn(0).setWidth(70);
		table.getColumn(1).setWidth(70);
		table.getColumn(2).setWidth(150);
		table.getColumn(3).setWidth(150);
		
		
	}
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @param manager
	 */
	public ViewMain(Composite parent, int style,BrowserManager manager) {
		super(parent, style);
		
		setLayout(new GridLayout(5, false));
		
		Label lblQuery = new Label(this, SWT.NONE);
		lblQuery.setText("Query");
		
		text = new Text(this, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Button btnRefresh = new Button(this, SWT.NONE);
		btnRefresh.setText("Refresh");
		
		new Label(this, SWT.NONE);
		
		Filter filter = new Filter(this, SWT.NONE);
		filter.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		filter.setSize(320,280);
		
		
		table = new org.eclipse.swt.widgets.Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		createColumns();
		
		
		

	}
	
	public void setContent(QueryResultSet content)
	{
		this.content = content;
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
