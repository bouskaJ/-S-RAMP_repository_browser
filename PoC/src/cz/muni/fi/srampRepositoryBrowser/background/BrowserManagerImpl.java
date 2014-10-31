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

public class BrowserManagerImpl implements BrowserManager{

	private SrampAtomApiClient client;
	
	
	
	public void setConnection(final String endpoint, final String username, final String password) throws ServiceFailureException
	{
		try {
			client = new SrampAtomApiClient(endpoint,username,password,true);
		}catch (SrampClientException | SrampAtomException e) {
			throw new ServiceFailureException("Problem with connecting",e);
		}
	}
	
	
	@Override
	public QueryResultSet listAllArtifacts() throws ServiceFailureException {
		return ExecuteQuery("/s-ramp");
	}

	@Override
	public void uploadArtifact(File file, String name, String type) throws ServiceFailureException {
		if(file == null)
		{
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
		} catch (FileNotFoundException | SrampClientException | SrampAtomException e) {
			throw new ServiceFailureException("Problem with uploading artifact (file = " + file + ").",e);
		}	
		
		
		
	}

	@Override
	public void deleteArtifact(String uuid, ArtifactType type) throws ServiceFailureException {
				
		try {
			client.deleteArtifact(uuid, type);
		} catch (SrampClientException | SrampAtomException e) {
			throw new ServiceFailureException("Problem with deleting artifact (uuid = " + uuid + ").",e);
			
		}
		
		
		
		
	}

	@Override
	public QueryResultSet ExecuteQuery(String query) throws ServiceFailureException {
		try {
			return client.query(query);
		} catch (SrampClientException | SrampAtomException e) {
			throw new ServiceFailureException("Problem with execute query (" + query + ").",e);
		}
		
		
		
	}



	@Override
	public void importToWorkspace(String uuid) {
		// TODO Auto-generated method stub
		
	}


}
