package cz.muni.fi.srampRepositoryBrowser.views;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.ui.handlers.HandlerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.muni.fi.srampRepositoryBrowser.UI.ConnectToServerDialog;

/**
 * 
 * @author honza
 * Default handler for ConnectToServerComand
 *
 */
public class ConnectToServerComand extends AbstractHandler{

	final static Logger log = LoggerFactory.getLogger(ConnectToServerComand.class);
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		if(HandlerUtil.getActivePart(event) instanceof RepositoryBrowser)
		{
			RepositoryBrowser b = (RepositoryBrowser) HandlerUtil.getActivePart(event);
			ConnectToServerDialog d =  new ConnectToServerDialog(HandlerUtil.getActiveShell(event),SWT.NONE,b);
			d.open();			
		}
		
		log.error("Connect to server comand is called wrong");
		
		return null;
	}

}
