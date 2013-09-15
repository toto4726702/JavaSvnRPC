package cn.edu.shiep.exception;

import org.tmatesoft.svn.core.SVNErrorCode;
import org.tmatesoft.svn.core.SVNErrorMessage;
import org.tmatesoft.svn.core.SVNException;

public class MySVNException extends SVNException{


	
	public MySVNException(SVNErrorMessage errorMessage) {
		super(errorMessage);
	}
	
	public MySVNException(SVNErrorCode code, String msg){
		super(SVNErrorMessage.create(code, msg));
	}

	private static final long serialVersionUID = 1L;
	
	
	
	@Override
	public String getMessage() {
		
		return super.getMessage();
	}

}
