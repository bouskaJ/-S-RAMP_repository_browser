package cz.muni.fi.srampRepositoryBrowser.UI;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;



public class Table extends Composite {
	private org.eclipse.swt.widgets.Table table;

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
			TableColumn columnName = new TableColumn(table, SWT.NONE);
			columnName.setText(titles[i]);
			
		}
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
