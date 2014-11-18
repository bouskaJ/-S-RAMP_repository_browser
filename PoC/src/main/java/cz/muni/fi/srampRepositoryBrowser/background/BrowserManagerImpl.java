package cz.muni.fi.srampRepositoryBrowser.background;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.ResourcesPlugin;
import org.overlord.sramp.atom.err.SrampAtomException;
import org.overlord.sramp.client.SrampAtomApiClient;
import org.overlord.sramp.client.SrampClientException;
import org.overlord.sramp.client.SrampClientQuery;
import org.overlord.sramp.client.query.ArtifactSummary;
import org.overlord.sramp.client.query.QueryResultSet;
import org.overlord.sramp.common.ArtifactType;

/**
 * Browser manager interface impl.
 * @author Jan Bouska
 *
 */

public class BrowserManagerImpl implements BrowserManager{

	private SrampAtomApiClient client;
	private boolean isConected = false;
	
	public static final Logger log = Logger.getLogger(BrowserManagerImpl.class.getName());
	
	
	
	public void setConnection(final String endpoint, final String username, final String password) throws ServiceFailureException
	{
		try {
			client = new SrampAtomApiClient(endpoint,username,password,true);
			isConected = true;
		}catch (SrampClientException  e) {
			log.log(Level.WARNING,"Problem with connecting",e);
			throw new ServiceFailureException("Problem with connecting",e);
		}
		catch ( SrampAtomException e) {
			log.log(Level.WARNING,"Problem with connecting",e);
			throw new ServiceFailureException("Problem with connecting",e);
		}
	}
	
	public boolean isConnected()
	{
		return isConected;
	}

	
	public SrampClientQuery buildQuery(String query)
	{
		return client.buildQuery(query);
	}
	
	public SrampClientQuery listAllArtifacts() 
	{
		
		return	buildQuery("/s-ramp");
		
	}


	public void uploadArtifact(File file, String name, String type) throws ServiceFailureException {
		if(file == null)
		{	log.log(Level.WARNING,"Uploading Artifact without content.");
			throw new ServiceFailureException("Uploading Artifact without content.");
		}
		
		if(name == null)
		{
			name = file.getName();
		}
		
		if(type == null)
		{
			ArtifactTypeGuessingService gs = new ArtifactTypeGuessingService();
			type = gs.guess(file.getName());
		}
		
		try { 
			InputStream is = new FileInputStream(file);
			
			client.uploadArtifact(ArtifactType.valueOf(type),is,name);
		} catch (FileNotFoundException e) {
			log.log(Level.WARNING,"Problem with uploading artifact (file = " + file + ").",e);
			throw new ServiceFailureException("Problem with uploading artifact (file = " + file + ").",e);
		}	
		catch (SrampClientException  e) {
			log.log(Level.WARNING,"Problem with uploading artifact (file = " + file + ").",e);
			throw new ServiceFailureException("Problem with uploading artifact (file = " + file + ").",e);
		}	
		catch (SrampAtomException e) {
			log.log(Level.WARNING,"Problem with uploading artifact (file = " + file + ").",e);
			throw new ServiceFailureException("Problem with uploading artifact (file = " + file + ").",e);
		}	
		
		
		
	}

	public void deleteArtifact(String uuid, ArtifactType type) throws ServiceFailureException {
				
		try {
			client.deleteArtifact(uuid, type);
		} catch (SrampAtomException e) {
			log.log(Level.WARNING,"Problem with deleting artifact (uuid = " + uuid + ").",e);
			throw new ServiceFailureException("Problem with deleting artifact (uuid = " + uuid + ").",e);
			
		}
		catch (SrampClientException e) {
			log.log(Level.WARNING,"Problem with deleting artifact (uuid = " + uuid + ").",e);
			throw new ServiceFailureException("Problem with deleting artifact (uuid = " + uuid + ").",e);
			
		}
		
		
		
		
	}

	public QueryResultSet ExecuteQuery(SrampClientQuery query)
			throws ServiceFailureException {
		try {
			return query.query();
		} catch ( SrampAtomException e) {
			log.log(Level.WARNING,"Problem with execute query (" + query + ").",e);
			throw new ServiceFailureException("Problem with execute query (" + query + ").",e);
		}
		catch (SrampClientException  e) {
			log.log(Level.WARNING,"Problem with execute query (" + query + ").",e);
			throw new ServiceFailureException("Problem with execute query (" + query + ").",e);
		}
		
		
		
	}



	public void importToWorkspace(ArtifactSummary as) throws ServiceFailureException {

		InputStream str = null;
		OutputStream os = null;
		
		try {
			str = client.getArtifactContent(as);
			File root = ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile();
			File file = new File(root,as.getName());
			os = new FileOutputStream(file);
			IOUtils.copy(str,os);
			
		} catch (SrampClientException | SrampAtomException | IOException e) {
			log.log(Level.WARNING,"Problem in import to workspace (file name: " + as.getName() + ").",e);
			throw new ServiceFailureException("Problem in import to workspace (file name: " + as.getName() + ").",e);
		}
		finally{
			
			
				try {
					if(str!=null) str.close();
					if(os != null) os.close();
				} catch (IOException e) {
					log.log(Level.WARNING,"Problem with closing streams.",e);
				}
			
		}
		
		
		
		
		
		
		
	}


}
