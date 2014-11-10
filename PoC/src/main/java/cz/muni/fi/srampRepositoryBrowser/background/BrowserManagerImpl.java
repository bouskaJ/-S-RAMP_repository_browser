package cz.muni.fi.srampRepositoryBrowser.background;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;




import org.overlord.sramp.atom.err.SrampAtomException;
import org.overlord.sramp.client.SrampAtomApiClient;
import org.overlord.sramp.client.SrampClientException;
import org.overlord.sramp.client.query.QueryResultSet;
import org.overlord.sramp.common.ArtifactType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.muni.fi.srampRepositoryBrowser.views.ConnectToServerComand;

public class BrowserManagerImpl implements BrowserManager{

	private SrampAtomApiClient client;
	private boolean isConected = false;
	
	final static Logger log = LoggerFactory.getLogger(ConnectToServerComand.class);
	
	
	
	public void setConnection(final String endpoint, final String username, final String password) throws ServiceFailureException
	{
		try {
			client = new SrampAtomApiClient(endpoint,username,password,true);
			isConected = true;
		}catch (SrampClientException  e) {
			log.warn("Problem with connecting",e);
			throw new ServiceFailureException("Problem with connecting",e);
		}
		catch ( SrampAtomException e) {
			log.warn("Problem with connecting",e);
			throw new ServiceFailureException("Problem with connecting",e);
		}
	}
	
	public boolean isConnected()
	{
		return isConected;
	}

	public QueryResultSet listAllArtifacts() throws ServiceFailureException {
		return ExecuteQuery("/s-ramp");
	}


	public void uploadArtifact(File file, String name, String type) throws ServiceFailureException {
		if(file == null)
		{	log.warn("Uploading Artifact without content.");
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
			log.warn("Problem with uploading artifact (file = " + file + ").",e);
			throw new ServiceFailureException("Problem with uploading artifact (file = " + file + ").",e);
		}	
		catch (SrampClientException  e) {
			log.warn("Problem with uploading artifact (file = " + file + ").",e);
			throw new ServiceFailureException("Problem with uploading artifact (file = " + file + ").",e);
		}	
		catch (SrampAtomException e) {
			log.warn("Problem with uploading artifact (file = " + file + ").",e);
			throw new ServiceFailureException("Problem with uploading artifact (file = " + file + ").",e);
		}	
		
		
		
	}

	public void deleteArtifact(String uuid, ArtifactType type) throws ServiceFailureException {
				
		try {
			client.deleteArtifact(uuid, type);
		} catch (SrampAtomException e) {
			log.warn("Problem with deleting artifact (uuid = " + uuid + ").",e);
			throw new ServiceFailureException("Problem with deleting artifact (uuid = " + uuid + ").",e);
			
		}
		catch (SrampClientException e) {
			log.warn("Problem with deleting artifact (uuid = " + uuid + ").",e);
			throw new ServiceFailureException("Problem with deleting artifact (uuid = " + uuid + ").",e);
			
		}
		
		
		
		
	}

	public QueryResultSet ExecuteQuery(String query) throws ServiceFailureException {
		try {
			return client.query(query);
		} catch ( SrampAtomException e) {
			log.warn("Problem with execute query (" + query + ").",e);
			throw new ServiceFailureException("Problem with execute query (" + query + ").",e);
		}
		catch (SrampClientException  e) {
			log.warn("Problem with execute query (" + query + ").",e);
			throw new ServiceFailureException("Problem with execute query (" + query + ").",e);
		}
		
		
		
	}




	public void importToWorkspace(String uuid) {
		// TODO Auto-generated method stub
		
	}


}
