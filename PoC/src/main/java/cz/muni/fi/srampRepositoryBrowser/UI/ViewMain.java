package cz.muni.fi.srampRepositoryBrowser.UI;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Button;

public class ViewMain extends Composite {
	private Text text;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewMain(Composite parent, int style) {
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
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(this, SWT.BORDER |SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
	
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setMinHeight(100);
		scrolledComposite.setMinWidth(600);
		
		Table table = new Table(scrolledComposite,SWT.NONE);
		scrolledComposite.setContent(table);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
