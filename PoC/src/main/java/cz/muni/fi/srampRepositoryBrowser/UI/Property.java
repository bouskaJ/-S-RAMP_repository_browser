package cz.muni.fi.srampRepositoryBrowser.UI;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;


/**
 * property class 
 * @author Jan Bouska
 *
 */
class Property extends Composite {
	private Text nameT;
	private Text valueT;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public Property(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Label nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText("Name:");
		
		Label valueLabel = new Label(this, SWT.NONE);
		valueLabel.setText("Value:");
		
		nameT = new Text(this, SWT.BORDER);
		nameT.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		valueT = new Text(this, SWT.BORDER);
		valueT.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}

