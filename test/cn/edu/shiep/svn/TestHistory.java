package cn.edu.shiep.svn;

import org.junit.Test;
import org.tmatesoft.svn.core.SVNException;

public class TestHistory {

	@Test
	public void testHistory() throws SVNException {
		History.history("http://?-hydrang-2/research/Hydra_research","vince.chen","Qwert,2005",0, -1);
	}	
}
