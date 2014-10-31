package cz.muni.fi.srampRepositoryBrowser.UI;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

public class ConnectToServerDialog extends Dialog {

	protected Object result;
	protected Shell shlConnectToServer;
	private Text serverT;
	private Text usernameT;
	private Label passwordLabel;
	private Text passwordT;
	private Button cancelButton;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ConnectToServerDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlConnectToServer.open();
		shlConnectToServer.layout();
		Display display = getParent().getDisplay();
		while (!shlConnectToServer.isDisposed()) {
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
		shlConnectToServer = new Shell(getParent(), getStyle());
		shlConnectToServer.setText("Connect to server");
		shlConnectToServer.setSize(450, 176);
		shlConnectToServer.setLayout(new GridLayout(2, false));
		
		Label usernameLabel = new Label(shlConnectToServer, SWT.NONE);
		usernameLabel.setText("Username:");
		
		passwordLabel = new Label(shlConnectToServer, SWT.NONE);
		passwordLabel.setText("Password:");
		
		usernameT = new Text(shlConnectToServer, SWT.BORDER);
		usernameT.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		passwordT = new Text(shlConnectToServer, SWT.BORDER);
		passwordT.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label serverLabel = new Label(shlConnectToServer, SWT.NONE);
		serverLabel.setText("Server:");
		new Label(shlConnectToServer, SWT.NONE);
		
		serverT = new Text(shlConnectToServer, SWT.BORDER);
		serverT.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Button OKButton = new Button(shlConnectToServer, SWT.NONE);
		GridData gd_OKButton = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_OKButton.widthHint = 60;
		OKButton.setLayoutData(gd_OKButton);
		OKButton.setText("OK");
		
		cancelButton = new Button(shlConnectToServer, SWT.NONE);
		GridData gd_cancelButton = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_cancelButton.widthHint = 60;
		cancelButton.setLayoutData(gd_cancelButton);
		cancelButton.setText("Cancel");

	}
}
