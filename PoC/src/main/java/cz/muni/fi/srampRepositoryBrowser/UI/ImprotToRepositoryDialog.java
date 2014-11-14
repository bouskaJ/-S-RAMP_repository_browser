package cz.muni.fi.srampRepositoryBrowser.UI;


import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
/**
 * import to repository dialog
 * @author Jan Bouska
 *
 */
public class ImprotToRepositoryDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;
	private Text text_1;


	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ImprotToRepositoryDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
		
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(450, 300);
		shell.setText(getText());
		shell.setLayout(new GridLayout(2, false));
		
		Label nameLabel = new Label(shell, SWT.NONE);
		nameLabel.setText("Name:");
		new Label(shell, SWT.NONE);
		
		text = new Text(shell, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label artifactTypeLabel = new Label(shell, SWT.NONE);
		artifactTypeLabel.setText("Artifact Type:");
		new Label(shell, SWT.NONE);
		
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		new Label(shell, SWT.NONE);
		
		Label propertiesLabel = new Label(shell, SWT.NONE);
		propertiesLabel.setText("Properties");
		
		new Property(shell,SWT.NONE);
		
		
		

	}

}
