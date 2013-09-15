package cn.edu.shiep.svn;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNErrorCode;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.SVNCopySource;
import org.tmatesoft.svn.core.wc.SVNRevision;


import cn.edu.shiep.exception.MySVNException;
import cn.edu.shiep.svn.WorkingCopy;

public class TestWorkingCopy {

	private WorkingCopy copy;
	
	@Before
	public void setup(){
		copy = WorkingCopy.getInstance();
		copy.initialize("vince.chen", "Qwert,2005");
	}
	
	@Test
	public void testCopyTag() throws SVNException{
		String tagName = "MyRepo02";
		copy.copy(new SVNCopySource[]{new SVNCopySource(SVNRevision.HEAD, SVNRevision.HEAD, SVNURL.parseURIEncoded("http://?-hydrang-2/research/Hydra_research/MyRepos"))}, SVNURL.parseURIEncoded("http://?-hydrang-2/research/Hydra_research/TAG/"+tagName), "svn test");
	}
	 
	@Test
	public void testLock() throws SVNException{
		copy.getLock(new File("C:/Documents and Settings/Vince.Chen/Desktop/Test2/Test/test.txt"), true, "svn test");
	}
	
	@Test
	public void testUnlock() throws SVNException{
		copy.releaseLock(new File("C:/Documents and Settings/Vince.Chen/Desktop/Test2/Test/test.txt"), true);
	}
	 
	@Test
	public void testAdd() throws SVNException{
		copy.add(new File("C:/Documents and Settings/Vince.Chen/Desktop/Copy2/test3/add.txt"));
	}
	
	@Test
	public void testDelete() throws SVNException{
		try {
			copy.delete(new File("D:/Documents and Settings/Vince.Chen/Desktop/Copy2/test3/add.txt"), false);
		} catch (Exception e) {
			throw new MySVNException(SVNErrorCode.CHECKSUM_MISMATCH, "?:É¾³ý´íÎó");
		}
	}
	
	@Test
	public void testCommit() throws SVNException{
		copy.commit(new File("C:/Documents and Settings/Vince.Chen/Desktop/Test/Copy2/test3/SVNKit Guide.doc"), false, "svn test");
	}
	
	@Test
	public void testUpdate() throws SVNException{
		copy.update("C:/Documents and Settings/Vince.Chen/Desktop/Copy2/test3/SVNKit Guide.doc", SVNRevision.HEAD, SVNDepth.fromRecurse(true), true);
	}
	
	@Test
	public void testCreateFolder() throws SVNException{
		copy.makeDirectory(SVNURL.parseURIEncoded("http://?-hydrang-2/research/Hydra_research/Copy/test5"), "svn test");
	}
	
	@Test
	public void testUploadFolder()throws SVNException{
		copy.importDirectory(new File("C:/Documents and Settings/Vince.Chen/Desktop/Test"), SVNURL.parseURIEncoded("http://?-hydrang-2/research/Hydra_research/Copy/Test"), "svn test", true);
	}
	
	@Test
	public void testShowStatus() throws SVNException{
		copy.showStatus(new File("C:/Documents and Settings/Vince.Chen/Desktop/Test/test3/"), true, true, true, true, false, new ArrayList<String>());
	}
	
	@Test
	public void testShowInfo() throws SVNException{
		copy.showInfo(new File("C:/Documents and Settings/Vince.Chen/Desktop/Test2"), SVNRevision.HEAD, true,new ArrayList<String>());
	}
	
	@Test
	public void testRepairSVN() throws SVNException{
		//copy.checkout(SVNURL.parseURIEncoded("http://?-hydrang-2/research/Hydra_research/MyRepos/importFiles"), SVNRevision.HEAD, new File("C:/Documents and Settings/Vince.Chen/Desktop/Test2/"), true, true);
		//copy.update(new File("C:/Documents and Settings/Vince.Chen/Desktop/Test2/importFiles"), SVNRevision.HEAD, true, true);
	}
	
	@Test
	public void testRepairSVN2() throws SVNException, IOException{
		copy.checkout("http://?-hydrang-2/research/Hydra_research/MyRepos", SVNRevision.HEAD,SVNDepth.EMPTY, "C:/Documents and Settings/Vince.Chen/Desktop/Test2", true, true);
		File newFile = new File("C:/Documents and Settings/Vince.Chen/Desktop/Test2/importFileC.txt");
		newFile.createNewFile();
		copy.update("C:/Documents and Settings/Vince.Chen/Desktop/Test2/importFileC.txt", SVNRevision.HEAD, SVNDepth.FILES, true);
	}
	
	@Test
	public void testProcess() throws Exception{
		//make a directory
		copy.makeDirectory(SVNURL.parseURIEncoded("http://?-hydrang-2/research/Hydra_research/MyRepos"), "svn test");
		//import a directory
		copy.importDirectory(new File("C:/Documents and Settings/Vince.Chen/Desktop/Test/ImportDir"), SVNURL.parseURIEncoded("http://?-hydrang-2/research/Hydra_research/MyRepos/"), "svn test", true);
		//check out repo
		copy.checkout("http://?-hydrang-2/research/Hydra_research/MyRepos", SVNRevision.HEAD,SVNDepth.fromRecurse(true), "C:/Documents and Settings/Vince.Chen/Desktop/Test/MyRepos", true ,false);
	}
	
	//Error
	@Test
	public void testCheckout() throws SVNException{
		copy.checkout("http://?-hydrang-2/research/Hydra_research/Copy/test3", SVNRevision.HEAD,SVNDepth.fromRecurse(true), "D:/good", true,false);
	}
	
}
