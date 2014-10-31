package cz.muni.fi.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.overlord.sramp.client.query.ArtifactSummary;
import org.overlord.sramp.client.query.QueryResultSet;
import org.overlord.sramp.common.ArtifactType;

import cz.muni.fi.srampRepositoryBrowser.background.BrowserManagerImpl;
import cz.muni.fi.srampRepositoryBrowser.background.ServiceFailureException;


public class BrowserManagerImplTest {

	private BrowserManagerImpl manager;
	
	@Rule
    public TemporaryFolder folder= new TemporaryFolder();
	
	
	
	@Before
	public void setUp() throws Exception {
		
		manager = new BrowserManagerImpl();
		manager.setConnection("http://localhost:8080/s-ramp-server", "admin",
				"jejdri2nat");
		
		
		
	}
	
	@After
    public void tearDown() {
QueryResultSet qs = manager.listAllArtifacts();
		
		
		for(ArtifactSummary as : qs)
		{
			manager.deleteArtifact(as.getUuid(),as.getType());
		}
    }

	@Test
	public void uploadArtifactBasicTest() throws IOException {
		
		File f = createFile();
			
		
		manager.uploadArtifact(f,null,null);
		String query = "/s-ramp[@name ='"+f.getName()+"']";
		QueryResultSet qs = manager.ExecuteQuery(query);
		assertEquals(1,qs.getTotalResults());
		
		ArtifactSummary as = qs.get(0);
		assertcheckArtifact(f.getName(),ArtifactType.valueOf("Document"), as);
	}
	
	@Test
	public void uploadArtifactUserTypeNameTest() throws IOException {
		
		File f = createFile();
		String type = "File";
		ArtifactType ba = ArtifactType.valueOf(type);
		
		manager.uploadArtifact(f,"name",type);
		
		QueryResultSet qs = manager.ExecuteQuery("/s-ramp[@name ='name']");
		assertEquals(1,qs.getTotalResults());
		
		ArtifactSummary as = qs.get(0);
		
		assertcheckArtifact("name", ba, as);
		
	}
	
	
	@Test (expected = ServiceFailureException.class)
	public void uploadArtifactwithoutContent() {
		
		manager.uploadArtifact(null,null,null);
		
	}
	
	@Test 
	public void listAllArtifactBasicTest() throws IOException {
		
		QueryResultSet qs = manager.listAllArtifacts();
		
		assertEquals(0,qs.getTotalResults());
		
		File f = createFile();
		
		manager.uploadArtifact(f,null,null);
		manager.uploadArtifact(f,"file",null);
		manager.uploadArtifact(f,"file","File");
		manager.uploadArtifact(f,null,"File");
		
		qs = manager.listAllArtifacts();
		
		assertEquals(4,qs.getTotalResults());
		
		
		
		
	}
	
	@Test 
	public void deleteArtifactBasicTest() throws IOException {
		
		
		File f = createFile();
		manager.uploadArtifact(f,null,null);
		
		
		QueryResultSet qs = manager.listAllArtifacts();
		
		ArtifactSummary as = qs.get(0);
		
		manager.deleteArtifact(as.getUuid(), as.getType());
		qs = manager.listAllArtifacts();
		
		assertEquals(0,qs.getTotalResults());
		
		manager.uploadArtifact(f,"file",null);
		manager.uploadArtifact(f,"file","File");
		
		String query = "/s-ramp/wsdl/File[@name = 'file']";
		qs = manager.ExecuteQuery(query);
		as = qs.get(0);
		manager.deleteArtifact(as.getUuid(), as.getType());
		
		qs = manager.listAllArtifacts();
		as = qs.get(0);
		assertcheckArtifact("file",ArtifactType.valueOf("Document"), as);
		
	
	}
	
	@Test (expected = ServiceFailureException.class)
	public void deleteNoArtifactTest() throws IOException {
		
		manager.deleteArtifact("xx",ArtifactType.valueOf("Document"));
	}
	
	
	private void assertcheckArtifact(String name, ArtifactType ba, ArtifactSummary as)
	{
		assertEquals(name,as.getName());
		assertEquals(ba.getType(),as.getType().getType()) ;
		
	}
	
	private File createFile() throws IOException
	{
		File f = folder.newFile("x");
		FileOutputStream s = new FileOutputStream(f);
		s.write('a');
		s.close();
		return f;
	}
	
	
}
