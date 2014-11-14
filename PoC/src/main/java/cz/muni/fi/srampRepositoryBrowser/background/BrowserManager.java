package cz.muni.fi.srampRepositoryBrowser.background;

import java.io.File;

import org.overlord.sramp.client.SrampClientQuery;
import org.overlord.sramp.client.query.QueryResultSet;
import org.overlord.sramp.common.ArtifactType;

/**
 * Browser manager interface.
 * @author Jan Bouska
 *
 */
public interface BrowserManager {
	
	/**
	 * Set connection to endpoint.
	 * 
	 * @param endpoint
	 * @param username
	 * @param password
	 * @throws ServiceFailureException
	 */
	void setConnection(final String endpoint, final String username, final String password) throws ServiceFailureException ;
	
	/**
	 * Build query.
	 * @param query
	 * @return SrampClientQuery
	 */
	SrampClientQuery buildQuery(String query);
	
	/**
	 * Build query for all artifacts
	 * 
	 * @return SrampClientQuery for all artifacts
	 */
	SrampClientQuery listAllArtifacts();
	
	/**
	 * Create new Artifact in s-ramp repository.
	 * 
	 * @param file content
	 * @param type type of artifact if type is null the type will  guess
	 */
	void uploadArtifact(File file, String name, String type) throws ServiceFailureException ;
	
	
	/**
	 * Remove Artifact from s-ramp repository
	 * 
	 * @param uuid artifact uuid
	 * @param type
	 */
	void deleteArtifact(String uuid, ArtifactType type)throws ServiceFailureException ;
	
	/**
	 * Execute query
	 * 
	 * @param query 
	 * @return QueryResultSet result of query
	 */
	QueryResultSet ExecuteQuery(SrampClientQuery query) throws ServiceFailureException;
	
	/**
	 * import artifact to workspace
	 */
	void importToWorkspace(String uuid);

	/**
	 * determines whether the manager is connected
	 * 
	 * @return if manager is connected return true
	 */
	public boolean isConnected();

}
