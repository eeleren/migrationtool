package com.ericsson.pc.migrationtool.sftp;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ericsson.pc.migrationtool.PhoneManualParser;
import com.ericsson.pc.migrationtool.util.ApplicationPropertiesReader;
import com.ericsson.pc.migrationtool.util.ZipUtil;
import com.jcraft.jsch.*;

public class SftpClient {
	
	final static Logger logger = Logger.getLogger(SftpClient.class);
	final static String hostname = ApplicationPropertiesReader.getInstance().getProperty("3pi.ip.address");
	final static String user = ApplicationPropertiesReader.getInstance().getProperty("3pi.username");
	final static String password = ApplicationPropertiesReader.getInstance().getProperty("3pi.password");
	final static String ingestionPath = ApplicationPropertiesReader.getInstance().getProperty("3pi.ingestion.path");
	
	public void startIngestion() {
		
		logger.debug("ASSET INGESTION STARTING...");
		
		ZipUtil appZip = new ZipUtil();
    	appZip.generateFileList(new File(appZip.getSourceFolder()));
    	appZip.zipIt(appZip.getOutputZipFile());
    	logger.debug("Asset output directory compressed and ready to be transferred via sftp");
    	
    	sftpTransfer(new File(appZip.getOutputZipFile()));
		
	}

    public static void main(String args[]) {
    	SftpClient client = new SftpClient();
    	client.runCommand() ;
    }
    	
    private void sftpTransfer(File fileToTransfer) {
    		//zip files
        	ZipUtil appZip = new ZipUtil();
        	appZip.generateFileList(new File(appZip.getSourceFolder()));
        	appZip.zipIt(appZip.getOutputZipFile());
        	
            JSch jsch = new JSch();
            Session session = null;

            Channel channel  = null;
            try {
                session = jsch.getSession(user, hostname, 22);
                session.setConfig("StrictHostKeyChecking", "no");
                session.setPassword(password);
                session.connect();

                channel = session.openChannel("sftp");
                channel.connect();
                ChannelSftp sftpChannel = (ChannelSftp) channel;
              //  sftpChannel.p
                sftpChannel.put(fileToTransfer.getAbsolutePath(), ingestionPath);
                logger.debug("Content transferred");
            } catch (JSchException e) {
                e.printStackTrace();
            } catch (SftpException e) {
                e.printStackTrace();	
            } finally {
                if (channel != null) {
                    channel.disconnect();
                }
                if (session != null) {
                    session.disconnect();
                }
            }
    		
    	}

    	private void runCommand() {
    		List<String> result = new ArrayList<String>();

    	 	 String command1="/home/msdp/ingestion/test.sh";
    	 	 JSch jsch = new JSch();
             Session session = null;
             Channel channel = null;
    	 	 try {      	 		 
                 session = jsch.getSession("msdp", "127.0.0.2", 22);
                 session.setConfig("StrictHostKeyChecking", "no");
                 session.setPassword("msdp");
                 session.connect();
    	 		
    	 		 System.out.println("Connected");
    	 		 
    	 		ChannelExec channelExec = (ChannelExec)session.openChannel("exec");
    	 		
    	        InputStream in = channelExec.getInputStream();
    	        
    	        channelExec.setCommand(command1);
    	        channelExec.setErrStream(System.err);
    	 		channelExec.connect();
    	 		
    	 		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    	 		String line;   	 		          
    	 		int index = 0;
    	 		
    	 		while ((line = reader.readLine()) != null)
    	 		{
    	 			 System.out.println(++index + " : " + line);  	 		
    	 		}   	 		 
    	 		int exitStatus = channelExec.getExitStatus();
    	 		channelExec.disconnect();
    	 		session.disconnect();
    	 		       
    	 		if (exitStatus < 0) {
    	 			System.out.println("Done, but exit status not set!");
    	 	  }  else if(exitStatus > 0) {    	 		
    	 		    System.out.println("Done, but with error!");
    	 	  } else {    	 		
    	 		    System.out.println("Done!");
    	 	  }
    	 	
    	 	   } catch (JSchException e) {
                   e.printStackTrace();
               }  catch (IOException ioe) {
                   ioe.printStackTrace();
               } finally {
                   if (channel != null) {
                       channel.disconnect();
                   }
                   if (session != null) {
                       session.disconnect();
                   }
               }
}

	
}