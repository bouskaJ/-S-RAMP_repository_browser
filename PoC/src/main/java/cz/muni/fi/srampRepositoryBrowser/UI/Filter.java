package cz.muni.fi.srampRepositoryBrowser.UI;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

import org.eclipse.swt.widgets.Button;

import org.eclipse.swt.widgets.DateTime;


/**
 * 
 * @author honza
 * Class representing browser filter
 *
 */
public class Filter extends Composite {
	private Text typeT;
	private Text createdByT;
	private Text lastModifiedByT;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public Filter(Composite parent, int style) {
		super(parent, SWT.NONE);
		setLayout(new GridLayout(3, false));
		
		Label filter = new Label(this, SWT.NONE);
	
		filter.setEnabled(true);
		filter.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		
		filter.setText("filter");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		
	
		Label type = new Label(this, SWT.NONE);
		type.setEnabled(true);
		type.setText("Type");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		
		typeT = new Text(this, SWT.BORDER);
		typeT.setEnabled(true);
		GridData gd_typeT = new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1);
		gd_typeT.widthHint = 230;
		typeT.setLayoutData(gd_typeT);
		
		Label dateCreated = new Label(this, SWT.NONE);
		dateCreated.setEnabled(true);
		dateCreated.setText("Date Created");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		DateTime dateTime_1 = new DateTime(this, SWT.BORDER);
		GridData gd_dateTime_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_dateTime_1.widthHint = 107;
		dateTime_1.setLayoutData(gd_dateTime_1);
		
		Label dCto = new Label(this, SWT.NONE);
		dCto.setEnabled(true);
		GridData gd_dCto = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_dCto.widthHint = 16;
		dCto.setLayoutData(gd_dCto);
		dCto.setText("to");
		
		DateTime dateTime = new DateTime(this, SWT.BORDER);
		GridData gd_dateTime = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_dateTime.widthHint = 107;
		dateTime.setLayoutData(gd_dateTime);
		
		Label dateLastModified = new Label(this, SWT.NONE);
		dateLastModified.setEnabled(true);
		dateLastModified.setText("Date Last Modified");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		DateTime dateTime_3 = new DateTime(this, SWT.BORDER);
		GridData gd_dateTime_3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_dateTime_3.widthHint = 107;
		dateTime_3.setLayoutData(gd_dateTime_3);
		
		Label dMto = new Label(this, SWT.NONE);
		dMto.setEnabled(true);
		GridData gd_dMto = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_dMto.widthHint = 16;
		dMto.setLayoutData(gd_dMto);
		dMto.setText("to");
		
		DateTime dateTime_2 = new DateTime(this, SWT.BORDER);
		GridData gd_dateTime_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_dateTime_2.widthHint = 107;
		dateTime_2.setLayoutData(gd_dateTime_2);
		
		Label createdBy = new Label(this, SWT.NONE);
		createdBy.setEnabled(true);
		createdBy.setText("Created By");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		createdByT = new Text(this, SWT.BORDER);
		createdByT.setEnabled(true);
		GridData gd_createdByT = new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1);
		gd_createdByT.widthHint = 230;
		createdByT.setLayoutData(gd_createdByT);
		
		Label lastModifiedBy = new Label(this, SWT.NONE);
		lastModifiedBy.setEnabled(true);
		lastModifiedBy.setText("Last Modified By");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		lastModifiedByT = new Text(this, SWT.BORDER);
		lastModifiedByT.setEnabled(true);
		GridData gd_lastModifiedByT = new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1);
		gd_lastModifiedByT.widthHint = 230;
		lastModifiedByT.setLayoutData(gd_lastModifiedByT);
		
		Button btnFilter = new Button(this, SWT.NONE);
		btnFilter.setEnabled(true);
		btnFilter.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnFilter.setText("Filter");
		new Label(this, SWT.NONE);
		
		Button btnClearAllFilters = new Button(this, SWT.NONE);
		btnClearAllFilters.setEnabled(true);
		btnClearAllFilters.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnClearAllFilters.setText("Clear all filters");
		

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
