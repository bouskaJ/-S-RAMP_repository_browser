package cz.muni.fi.srampRepositoryBrowser.views;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;

import org.eclipse.ui.handlers.HandlerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author Jan Bouska
 * Default handler for ImportArtifactComand
 *
 */
public class ImportArtifactComand extends AbstractHandler{

	final static Logger log = LoggerFactory.getLogger(ConnectToServerComand.class);
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		MessageDialog.openInformation(HandlerUtil.getActiveShell(event), "info", "You want to import artifact.");	 		   		    	  
		
		return null;
	}

}
