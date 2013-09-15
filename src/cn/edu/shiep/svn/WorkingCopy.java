package cn.edu.shiep.svn;

import java.io.File;
import java.util.List;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNCopySource;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;


public class WorkingCopy {

	private static WorkingCopy copy;
	private SVNClientManager ourClientManager;
	private String svnUser = "vince.chen";
	private String svnPass = "Qwert,2004";
	private ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
	
	private WorkingCopy (){}
	
	public static WorkingCopy getInstance(){
		if(copy == null	){
			copy = new WorkingCopy();
		}
		return copy;
	}
	
	/**
	 * Initialize the WorkingCopy, so call it at beginning.
	 */
	public void initialize(String svnUser,String svnPass){
		this.svnUser = svnUser;
		this.svnPass = svnPass;
		
		ourClientManager = SVNClientManager.newInstance((DefaultSVNOptions) options,
				this.svnUser, this.svnPass);
	}
	
	//�½��ļ���
	public  SVNCommitInfo makeDirectory(SVNURL url, String commitMessage)
			throws SVNException {
		return ourClientManager.getCommitClient().doMkDir(new SVNURL[] { url },commitMessage);
	}

	//�����ļ���
	public  SVNCommitInfo importDirectory(File localPath, SVNURL dstURL,
		String commitMessage, boolean isRecursive) throws SVNException {
		return ourClientManager.getCommitClient().doImport(localPath, dstURL, commitMessage, new SVNProperties(), true, true, SVNDepth.fromRecurse(isRecursive));
	}
	
	//����
	public  long update( String wcPath , SVNRevision updateToRevision , SVNDepth depth,boolean allowUnversionedObstructions ) throws SVNException {
	 
	  	SVNUpdateClient updateClient = ourClientManager.getUpdateClient( );
	    updateClient.setIgnoreExternals( false );
	    return updateClient.doUpdate(new File(wcPath), updateToRevision, depth, allowUnversionedObstructions, true);
	}
		   
	//�ύ
	@SuppressWarnings("deprecation")
	public  SVNCommitInfo commit(File wcPath, boolean keepLocks,
		String commitMessage) throws SVNException {
        return ourClientManager.getCommitClient().doCommit( new File[] { wcPath } , keepLocks , commitMessage , false , true );
	}

	//���
	public  long checkout(String url, SVNRevision revision, SVNDepth depth,
			String destPath, boolean isRecursive, boolean allowUnversionedObstructions) throws SVNException {
		SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
		updateClient.setIgnoreExternals(false);
		return updateClient.doCheckout(SVNURL.parseURIEncoded(url), new File(destPath), revision, revision, depth, allowUnversionedObstructions);
	}
	
	//����
	public  void getLock( File wcPath , boolean isStealLock , String lockComment ) throws SVNException {
		ourClientManager.getWCClient( ).doLock( new File[] { wcPath } , isStealLock , lockComment );
	}
	
	//�ͷ���
	public  void releaseLock(File wcPath, boolean breakLock) throws SVNException{
		ourClientManager.getWCClient( ).doUnlock(new File[]{wcPath}, breakLock);
	}
	
	//��ӵ��汾���ƣ���һ���ύ(add����)
	public  void add( File wcPath ) throws SVNException {
		ourClientManager.getWCClient( ).doAdd(wcPath, false, false, false, SVNDepth.fromRecurse(true), false, false);
    }
	
	//ɾ�������ļ�
	public  void delete( File wcPath , boolean force ) throws SVNException {
        ourClientManager.getWCClient( ).doDelete( wcPath , force , false );
    }
	
	//����(��֧/���)
	public  void copy(SVNCopySource[] srcs,SVNURL dst,String commitMsg) throws SVNException{
		ourClientManager.getCopyClient().doCopy(srcs, dst, false, true, true, commitMsg, new SVNProperties());
	}
	
	//״̬��ȡ
	public  void showStatus( File wcPath , boolean isRecursive , boolean isRemote , boolean isReportAll ,
		 boolean isIncludeIgnored , boolean isCollectParentExternals ,List<String> changeFilterList) throws SVNException {
		 ourClientManager.getStatusClient( ).doStatus(wcPath, SVNRevision.HEAD, SVNDepth.fromRecurse(isRecursive), isRemote, isReportAll, isIncludeIgnored, isCollectParentExternals, new StatusHandler(isRemote), changeFilterList);
	}
	 
	//�����������Ϣ
	public  void showInfo( File wcPath , SVNRevision revision , boolean isRecursive, List<String>  changeLists) throws SVNException {
        ourClientManager.getWCClient( ).doInfo(wcPath, revision, revision, SVNDepth.fromRecurse(isRecursive), changeLists, new InfoHandler());        
	}

}
