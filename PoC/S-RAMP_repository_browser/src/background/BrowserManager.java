package background;

import java.io.File;
import org.overlord.sramp.client.query.QueryResultSet;
import org.overlord.sramp.common.ArtifactType;

public interface BrowserManager {
	
	
	void setConnection(final String endpoint, final String username, final String password) throws ServiceFailureException ;
	
	/**
	 * List Artifact sumary for all artifact from repository
	 * 
	 * @return ArtifactSummary list
	 */
	QueryResultSet listAllArtifacts() throws ServiceFailureException ;
	
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
	 * @return ArtifactSummary list
	 */
	QueryResultSet ExecuteQuery(String query) throws ServiceFailureException ;
	
	void importToWorkspace() throws ServiceFailureException ;

	

}
