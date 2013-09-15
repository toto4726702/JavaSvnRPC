package cn.edu.shiep.svn;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class History {

	//private static String url = "http://?-hydrang-2/research/Hydra_research";
	//private static String user = "vince.chen";
	//private static String pass = "Qwert,2005";

	/**
	 * 
	 * @param startReversion a revision to start from
	 * @param endReversion  a revision to end at(-1 means the latest)
	 * @throws SVNException 
	 */
	@SuppressWarnings("rawtypes")
	public static void history(String url, String user, String pass,long startReversion, long endReversion)
			throws SVNException {
		
		DAVRepositoryFactory.setup();
		SVNRepository repository = null;

		repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
		ISVNAuthenticationManager authManager = SVNWCUtil
				.createDefaultAuthenticationManager(user, pass);
		repository.setAuthenticationManager(authManager);
		Collection logEntries = repository.log(new String[] { "" }, null,
				startReversion, endReversion, true, true);

		for (Iterator entries = logEntries.iterator(); entries.hasNext();) {
			SVNLogEntry logEntry = (SVNLogEntry) entries.next();
			System.out.println("---------------------------------------------");
			System.out.println("revision: " + logEntry.getRevision());
			System.out.println("author: " + logEntry.getAuthor());
			System.out.println("date: " + logEntry.getDate());
			System.out.println("log message: " + logEntry.getMessage());

			if (logEntry.getChangedPaths().size() > 0) {
				System.out.println();
				System.out.println("changed paths:");
				Set changedPathsSet = logEntry.getChangedPaths().keySet();

				for (Iterator changedPaths = changedPathsSet.iterator(); changedPaths
						.hasNext();) {
					SVNLogEntryPath entryPath = (SVNLogEntryPath) logEntry
							.getChangedPaths().get(changedPaths.next());
					System.out.println(" "
							+ entryPath.getType()
							+ " "
							+ entryPath.getPath()
							+ ((entryPath.getCopyPath() != null) ? " (from "
									+ entryPath.getCopyPath() + " revision "
									+ entryPath.getCopyRevision() + ")" : ""));
				}
			}
		}

	}


}
