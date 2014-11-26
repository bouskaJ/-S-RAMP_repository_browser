package cz.muni.fi.srampRepositoryBrowser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.FileInfoMatcherDescription;
import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IPathVariableManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceFilterDescription;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IPluginDescriptor;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.IContentTypeMatcher;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.PropertyData;
import org.overlord.sramp.client.SrampClientQuery;
import org.overlord.sramp.client.query.ArtifactSummary;
import org.overlord.sramp.client.query.QueryResultSet;
import org.overlord.sramp.common.ArtifactType;

import cz.muni.fi.srampRepositoryBrowser.UI.PropData;
import cz.muni.fi.srampRepositoryBrowser.background.BrowserManagerImpl;
import cz.muni.fi.srampRepositoryBrowser.background.ServiceFailureException;


public class BrowserManagerImplTest {
	

	private BrowserManagerImpl manager;
	
	
	
	@Before
	public void setUp() throws Exception {
		
		manager = new BrowserManagerImpl();
		manager.setConnection("http://localhost:8080/s-ramp-server", "admin",
				"a");
		
		
		
			
		
		
	}
	
	@After
    public void tearDown() throws CoreException {
QueryResultSet qs = manager.ExecuteQuery(manager.listAllArtifacts());
		
		
		for(ArtifactSummary as : qs)
		{
			manager.deleteArtifact(as.getUuid(),as.getType());
		}
		
	
    }

	
	@Test 
	public void listAllArtifactBasicTest() throws IOException, CoreException {
		
		QueryResultSet qs = manager.ExecuteQuery(manager.listAllArtifacts());
		
		assertEquals(0,qs.getTotalResults());
		
		
		 IFile f =  new MockIFile("testFile");
		
		manager.uploadArtifact(f,"","",new ArrayList<PropData>());
		manager.uploadArtifact(f,"file","",new ArrayList<PropData>());
		manager.uploadArtifact(f,"file","File",new ArrayList<PropData>());
		manager.uploadArtifact(f,"","File",new ArrayList<PropData>());
		
		qs = manager.ExecuteQuery(manager.listAllArtifacts());
		
		assertEquals(4,qs.getTotalResults());
		
		
		
		
	}
	
	
	
	@Test
	public void uploadArtifactBasicTest() throws IOException, CoreException {
		
		
		 IFile f =new MockIFile("testFile");
		manager.uploadArtifact(f,"","",new ArrayList<PropData>());
		
		SrampClientQuery qr = manager.buildQuery("/s-ramp[@name =?]");
		qr.parameter(f.getName());
		QueryResultSet qs = manager.ExecuteQuery(qr);
		
		assertEquals(1,qs.getTotalResults());
		
		ArtifactSummary as = qs.get(0);
		assertcheckArtifact(f.getName(),ArtifactType.valueOf("Document"), as);
	}
	
	@Test
	public void uploadArtifactUserTypeNameTest() throws IOException, CoreException {
		
		String name = "name";
		String type = "File";
		ArtifactType ba = ArtifactType.valueOf(type);
		
		
		 IFile f = new MockIFile("testFile");
		
		
		manager.uploadArtifact(f,name,type,new ArrayList<PropData>());
				
		SrampClientQuery qr = manager.buildQuery("/s-ramp[@name =?]");
		qr.parameter(name);
		QueryResultSet qs = manager.ExecuteQuery(qr);
		assertEquals(1,qs.getTotalResults());
		
		ArtifactSummary as = qs.get(0);
		
		assertcheckArtifact(name, ba, as);
		
	}
	
	
	
	
	@Test (expected = ServiceFailureException.class)
	public void uploadArtifactFileDontExists() {
		
		IFile f = new MockIFile("test"){
			public boolean exists()
			{
				return false;
			}
		};
		
		manager.uploadArtifact(f,"","",new ArrayList<PropData>());
		
	}
	
	
	
	@Test 
	public void deleteArtifactBasicTest() throws IOException, CoreException {
		
		 IFile f =  new MockIFile("testFile");
		manager.uploadArtifact(f,"","",new ArrayList<PropData>());
		
		
		QueryResultSet qs = manager.ExecuteQuery(manager.listAllArtifacts());
		
		ArtifactSummary as = qs.get(0);
		
		manager.deleteArtifact(as.getUuid(), as.getType());
		qs = manager.ExecuteQuery(manager.listAllArtifacts());
		
		assertEquals(0,qs.getTotalResults());
		
		manager.uploadArtifact(f,"file","",new ArrayList<PropData>());
		manager.uploadArtifact(f,"file","File",new ArrayList<PropData>());
		
		String query = "/s-ramp/wsdl/File[@name = 'file']";
		qs = manager.ExecuteQuery(manager.buildQuery(query));
		as = qs.get(0);
		manager.deleteArtifact(as.getUuid(), as.getType());
		
		qs = manager.ExecuteQuery(manager.listAllArtifacts());
		as = qs.get(0);
		assertcheckArtifact("file",ArtifactType.valueOf("Document"), as);
		
	
	}
	
	@Test (expected = ServiceFailureException.class)
	public void deleteNoArtifactTest() throws IOException {
		
		manager.deleteArtifact("xx",ArtifactType.valueOf("Document"));
	}
	
	@Test 
	public void executeQueryNameTest()
	{

		QueryResultSet qs = manager.ExecuteQuery(manager.listAllArtifacts());
		
		assertEquals(0,qs.getTotalResults());
		
		 IFile f =  new MockIFile("testFile");
		String name1 = "file";
		String name2 = "file2";
		manager.uploadArtifact(f,name2,"",new ArrayList<PropData>());
		manager.uploadArtifact(f,name1,"",new ArrayList<PropData>());
		manager.uploadArtifact(f,name1,"File",new ArrayList<PropData>());
		manager.uploadArtifact(f,name2,"File",new ArrayList<PropData>());
		
		SrampClientQuery query = manager.buildQuery("/s-ramp[@name =?]");
		query.parameter(name1);
		qs = manager.ExecuteQuery(query);
		
		assertEquals(2,qs.getTotalResults());
		
		
		
		
	}
	
	@Test 
	public void executeQueryTypeTest()
	{

		QueryResultSet qs = manager.ExecuteQuery(manager.listAllArtifacts());
		
		assertEquals(0,qs.getTotalResults());
		
		 IFile f =  new MockIFile("testFile");
		String name1 = "file";
		String name2 = "file2";
		manager.uploadArtifact(f,name2,"",new ArrayList<PropData>());
		manager.uploadArtifact(f,name1,"",new ArrayList<PropData>());
		manager.uploadArtifact(f,name1,"",new ArrayList<PropData>());
		manager.uploadArtifact(f,name2,"File",new ArrayList<PropData>());
		
		SrampClientQuery query = manager.buildQuery("/s-ramp/ext/File");
		
		qs = manager.ExecuteQuery(query);
		
		assertEquals(1,qs.getTotalResults());
		
		query = manager.buildQuery("/s-ramp/core/Document");
		
		qs = manager.ExecuteQuery(query);
		
		assertEquals(3,qs.getTotalResults());
		
		
		
		
	}
	
	
	@Test
	public void importToWorkspaceBasicTest() throws IOException
	{
		 IFile f =  new MockIFile("testFile");
		manager.uploadArtifact(f,"","",new ArrayList<PropData>());
		
		SrampClientQuery query = manager.buildQuery("/s-ramp[@name =?]");
		query.parameter("testFile");
		QueryResultSet qs = manager.ExecuteQuery(query);
		
		File folder = File.createTempFile("test",".tmp");
		manager.importToWorkspace(qs.get(0), new MockIProject(folder));
		
		File ex = new File(folder + "testFile");
		assertTrue(ex.exists());
		
		InputStream fis = new FileInputStream(ex);
				 
		
		assertEquals("test", IOUtils.toString(fis, "UTF-8"));
		
		
	}
	
	
	
	private void assertcheckArtifact(String name, ArtifactType ba, ArtifactSummary as)
	{
		assertEquals(name,as.getName());
		assertEquals(ba.getType(),as.getType().getType()) ;
		
	}
	
	
}




 class MockIFile implements IFile {

	 private String name;
	 private File folder;
	 
	 public MockIFile(String name)
	 {
		 this.name = name;
	 }
	 
	 public MockIFile(String name,File folder)
	 {
		 this.name = name;
		 this.folder = folder;
	 }
	 
	public InputStream getContents()
	{
		 String s = "test";
		 try {
			return new ByteArrayInputStream(s.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		 
		 return null;
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean exists()
	{
		return true;
	}

	@Override
	public void accept(IResourceVisitor arg0) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accept(IResourceProxyVisitor arg0, int arg1)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accept(IResourceProxyVisitor arg0, int arg1, int arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accept(IResourceVisitor arg0, int arg1, boolean arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accept(IResourceVisitor arg0, int arg1, int arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearHistory(IProgressMonitor arg0) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copy(IPath arg0, boolean arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copy(IPath arg0, int arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copy(IProjectDescription arg0, boolean arg1,
			IProgressMonitor arg2) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copy(IProjectDescription arg0, int arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IMarker createMarker(String arg0) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResourceProxy createProxy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(boolean arg0, IProgressMonitor arg1)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int arg0, IProgressMonitor arg1) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteMarkers(String arg0, boolean arg1, int arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IMarker findMarker(long arg0) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IMarker[] findMarkers(String arg0, boolean arg1, int arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findMaxProblemSeverity(String arg0, boolean arg1, int arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getFileExtension() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getLocalTimeStamp() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IPath getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getLocationURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IMarker getMarker(long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getModificationStamp() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IContainer getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPathVariableManager getPathVariableManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<QualifiedName, String> getPersistentProperties()
			throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPersistentProperty(QualifiedName arg0)
			throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IProject getProject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPath getProjectRelativePath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPath getRawLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getRawLocationURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResourceAttributes getResourceAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<QualifiedName, Object> getSessionProperties()
			throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getSessionProperty(QualifiedName arg0) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IWorkspace getWorkspace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccessible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDerived() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDerived(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHidden() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHidden(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLinked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLinked(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLocal(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPhantom() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSynchronized(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTeamPrivateMember() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTeamPrivateMember(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isVirtual() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void move(IPath arg0, boolean arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(IPath arg0, int arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(IProjectDescription arg0, int arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(IProjectDescription arg0, boolean arg1, boolean arg2,
			IProgressMonitor arg3) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshLocal(int arg0, IProgressMonitor arg1)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void revertModificationStamp(long arg0) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDerived(boolean arg0) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDerived(boolean arg0, IProgressMonitor arg1)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHidden(boolean arg0) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLocal(boolean arg0, int arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long setLocalTimeStamp(long arg0) throws CoreException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPersistentProperty(QualifiedName arg0, String arg1)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setReadOnly(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setResourceAttributes(ResourceAttributes arg0)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSessionProperty(QualifiedName arg0, Object arg1)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTeamPrivateMember(boolean arg0) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void touch(IProgressMonitor arg0) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getAdapter(Class arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(ISchedulingRule arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isConflicting(ISchedulingRule arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void appendContents(InputStream arg0, int arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void appendContents(InputStream arg0, boolean arg1, boolean arg2,
			IProgressMonitor arg3) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(InputStream arg0, boolean arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(InputStream is, int arg1, IProgressMonitor arg2){
		
		
		File f = new File(folder + name);
		FileWriter s = null;
		try {
			s = new FileWriter(f);
			IOUtils.copy(is, s, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	
		
	}

	@Override
	public void createLink(IPath arg0, int arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createLink(URI arg0, int arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(boolean arg0, boolean arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCharset() throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCharset(boolean arg0) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCharsetFor(Reader arg0) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IContentDescription getContentDescription() throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getContents(boolean arg0) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getEncoding() throws CoreException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IPath getFullPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IFileState[] getHistory(IProgressMonitor arg0) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isReadOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void move(IPath arg0, boolean arg1, boolean arg2,
			IProgressMonitor arg3) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharset(String arg0) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharset(String arg0, IProgressMonitor arg1)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setContents(InputStream arg0, int arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setContents(IFileState arg0, int arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setContents(InputStream arg0, boolean arg1, boolean arg2,
			IProgressMonitor arg3) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setContents(IFileState arg0, boolean arg1, boolean arg2,
			IProgressMonitor arg3) throws CoreException {
		// TODO Auto-generated method stub
		
	}
	
	}
 
 class MockIProject implements IProject{

	private  File folder;
	public MockIProject(File folder2){
		 this.folder = folder2;
	 }
	@Override
	public IResourceFilterDescription createFilter(int arg0,
			FileInfoMatcherDescription arg1, int arg2, IProgressMonitor arg3)
			throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	@Override
	public boolean exists(IPath arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IFile[] findDeletedMembersWithHistory(int arg0, IProgressMonitor arg1)
			throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResource findMember(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResource findMember(IPath arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResource findMember(String arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResource findMember(IPath arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDefaultCharset() throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDefaultCharset(boolean arg0) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IFile getFile(IPath arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResourceFilterDescription[] getFilters() throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IFolder getFolder(IPath arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResource[] members() throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResource[] members(boolean arg0) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResource[] members(int arg0) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDefaultCharset(String arg0) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDefaultCharset(String arg0, IProgressMonitor arg1)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accept(IResourceVisitor arg0) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accept(IResourceProxyVisitor arg0, int arg1)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accept(IResourceProxyVisitor arg0, int arg1, int arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accept(IResourceVisitor arg0, int arg1, boolean arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accept(IResourceVisitor arg0, int arg1, int arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearHistory(IProgressMonitor arg0) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copy(IPath arg0, boolean arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copy(IPath arg0, int arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copy(IProjectDescription arg0, boolean arg1,
			IProgressMonitor arg2) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copy(IProjectDescription arg0, int arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IMarker createMarker(String arg0) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResourceProxy createProxy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(boolean arg0, IProgressMonitor arg1)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int arg0, IProgressMonitor arg1) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteMarkers(String arg0, boolean arg1, int arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IMarker findMarker(long arg0) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IMarker[] findMarkers(String arg0, boolean arg1, int arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findMaxProblemSeverity(String arg0, boolean arg1, int arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getFileExtension() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPath getFullPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getLocalTimeStamp() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IPath getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getLocationURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IMarker getMarker(long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getModificationStamp() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IContainer getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPathVariableManager getPathVariableManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<QualifiedName, String> getPersistentProperties()
			throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPersistentProperty(QualifiedName arg0)
			throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IProject getProject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPath getProjectRelativePath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPath getRawLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getRawLocationURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResourceAttributes getResourceAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<QualifiedName, Object> getSessionProperties()
			throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getSessionProperty(QualifiedName arg0) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IWorkspace getWorkspace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccessible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDerived() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDerived(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHidden() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isLinked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLinked(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLocal(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPhantom() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isReadOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSynchronized(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTeamPrivateMember() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTeamPrivateMember(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isVirtual() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void move(IPath arg0, boolean arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(IPath arg0, int arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(IProjectDescription arg0, int arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(IProjectDescription arg0, boolean arg1, boolean arg2,
			IProgressMonitor arg3) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshLocal(int arg0, IProgressMonitor arg1)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void revertModificationStamp(long arg0) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDerived(boolean arg0) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDerived(boolean arg0, IProgressMonitor arg1)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHidden(boolean arg0) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLocal(boolean arg0, int arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long setLocalTimeStamp(long arg0) throws CoreException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPersistentProperty(QualifiedName arg0, String arg1)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setReadOnly(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setResourceAttributes(ResourceAttributes arg0)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSessionProperty(QualifiedName arg0, Object arg1)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTeamPrivateMember(boolean arg0) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void touch(IProgressMonitor arg0) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getAdapter(Class arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(ISchedulingRule arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isConflicting(ISchedulingRule arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void build(int arg0, IProgressMonitor arg1) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void build(IBuildConfiguration arg0, int arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void build(int arg0, String arg1, Map<String, String> arg2,
			IProgressMonitor arg3) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close(IProgressMonitor arg0) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(IProgressMonitor arg0) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(IProjectDescription arg0, IProgressMonitor arg1)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(IProjectDescription arg0, int arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(boolean arg0, boolean arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IBuildConfiguration getActiveBuildConfig() throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IBuildConfiguration getBuildConfig(String arg0) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IBuildConfiguration[] getBuildConfigs() throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IContentTypeMatcher getContentTypeMatcher() throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IProjectDescription getDescription() throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IFile getFile(String name) {
		
		return new MockIFile(name,folder)
		{
			public boolean exists()
			{
				return false;
			}
		};
	}

	@Override
	public IFolder getFolder(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IProjectNature getNature(String arg0) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IBuildConfiguration[] getReferencedBuildConfigs(String arg0,
			boolean arg1) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IProject[] getReferencedProjects() throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IProject[] getReferencingProjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPath getWorkingLocation(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasBuildConfig(String arg0) throws CoreException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasNature(String arg0) throws CoreException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNatureEnabled(String arg0) throws CoreException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void loadSnapshot(int arg0, URI arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(IProjectDescription arg0, boolean arg1,
			IProgressMonitor arg2) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void open(IProgressMonitor arg0) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void open(int arg0, IProgressMonitor arg1) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveSnapshot(int arg0, URI arg1, IProgressMonitor arg2)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDescription(IProjectDescription arg0, IProgressMonitor arg1)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDescription(IProjectDescription arg0, int arg1,
			IProgressMonitor arg2) throws CoreException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isHidden(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public IPath getPluginWorkingLocation(IPluginDescriptor arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	 
 }


