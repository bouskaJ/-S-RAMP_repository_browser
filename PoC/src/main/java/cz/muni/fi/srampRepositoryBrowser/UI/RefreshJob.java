package cz.muni.fi.srampRepositoryBrowser.UI;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import cz.muni.fi.srampRepositoryBrowser.background.ServiceFailureException;


/**
 * loading data from repository and refreshing table
 * 
 * @author Jan Bouska
 *
 */
public class RefreshJob extends Job{

	private ViewMain ui;
	
	/**
	 * constructor
	 * 
	 * @param s
	 * @param ui
	 */
	public RefreshJob(String s,ViewMain ui)
	{
		super(s);
		this.ui = ui;
	}
	

	/**
	 * job should run when manager is connected
	 */
		public boolean shouldRun() {
			return super.shouldSchedule()
					&& ui.getManager().isConnected();
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {

			try {
				//loading data 
				ui.loadContent(); 
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						//updating table
						ui.updateTable();
					}
				});

			} catch (ServiceFailureException e) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						MessageDialog
								.openError(ui.getShell(),
										"Loading failed!",
										"Error while retrieving data from the repository.");

					}
				});
			}

			return Status.OK_STATUS;
		}

}
