<%@page import="org.tmatesoft.svn.core.SVNDepth"%>
<%@page import="org.tmatesoft.svn.core.wc.SVNRevision"%>
<%@page import="com.bleum.svn.WorkingCopy"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.UUID"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		System.out.println("come!");
		String savePath = this.getServletConfig().getServletContext().getRealPath("");
	    //assumed user name is Vince
	    String username = "Vince";
		savePath = savePath +"/uploads/"+ username+"/";
		
		//Get Copy Folder Name
		String folderName = request.getParameter("parentFolder");
		//Here can modify the path of file saved
		folderName =  folderName.substring(folderName.lastIndexOf("/")+1);
		//System.out.println(folderName);
		File folderFile = new File(savePath+folderName);
		if(!folderFile.exists()){
			//Create Copy Folder
			folderFile.mkdirs();
			//Synchronize the Copy(Only Folder)
			WorkingCopy.checkout(request.getParameter("parentFolder"), SVNRevision.HEAD, SVNDepth.EMPTY, savePath+folderName, true, true);
			System.out.println("Copy create at:"+savePath+folderName);
		}
		
		//Iterator the files uploaded
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		List fileList = upload.parseRequest(request);
		Enumeration en = request.getParameterNames();
		Iterator i = fileList.iterator();
		while(i.hasNext()){
			FileItem item = (FileItem) i.next();
			//System.out.println(item);
			 if (!item.isFormField()) {
			     String name = item.getName(); 
			     if (name == null || name.trim().equals("")) {
				        continue;
				 }
			     System.out.println(name);
			     /*
			     String extName = null;
			     if (name.lastIndexOf(".") >= 0) {
				    extName = name.substring(name.lastIndexOf("."));
				 }*/
			     long size = item.getSize();
			     String type = item.getContentType();
			     File file = null;
			     //do {
			     //name = UUID.randomUUID().toString();
		         //SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		         //name = format.format(new Date())+name;
		         file = new File(savePath);
		         if(!file.exists()){
		        	 file.mkdirs();
		         }
			     // } while (file.exists());
			     File saveFile = new File(savePath + folderName +"/"+ name);
			     try {
			      item.write(saveFile);
			     } catch (Exception e) {
			      e.printStackTrace();
			     }
			     
			   //update Copy(Only Update This File)
				WorkingCopy.update(savePath+folderName+"/"+ name, SVNRevision.HEAD, SVNDepth.FILES, true);
			}
		}
		
		
		
		System.out.println("success!");
	%>
</body>
</html>