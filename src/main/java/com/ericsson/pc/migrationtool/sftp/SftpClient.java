package com.ericsson.pc.migrationtool.sftp;
import java.io.File;
import com.ericsson.pc.migrationtool.util.ZipUtil;
import com.jcraft.jsch.*;

public class SftpClient {

    public static void main(String args[]) {
    	
    	//zip files
    	ZipUtil appZip = new ZipUtil();
    	appZip.generateFileList(new File(appZip.getSourceFolder()));
    	appZip.zipIt(appZip.getOutputZipFile());
    	
        JSch jsch = new JSch();
        Session session = null;

        Channel channel  = null;
        try {
            session = jsch.getSession("msdp", "127.0.0.2", 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword("msdp");
            session.connect();

            channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
          //  sftpChannel.p
            sftpChannel.put("C:\\xml\\output\\zipOutput\\assets.zip", "/home/msdp/ingestion/");
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
}