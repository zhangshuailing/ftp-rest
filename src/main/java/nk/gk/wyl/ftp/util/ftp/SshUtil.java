package nk.gk.wyl.ftp.util.ftp;

import com.jcraft.jsch.*;
import nk.gk.wyl.ftp.entity.SshModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class SshUtil {
    private static Logger logger = LoggerFactory.getLogger(SshUtil.class);
    // 定义session
    public static Map<String,Session> SESSION = new HashMap<>();

    public static void main(String[] args) {
        String ip = "192.168.110.106";
        String username ="root";
        String password = "123456";
        int port = 22;
        /*String file_path = "g:/疫情-博客.xlsx";
        String path = "/opt/zsl/uploadFiles/zw/demo";
        File file = new File(file_path);
        try {
            upload(path,new FileInputStream(file),file.getName(),connect(ip,username,password,port));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
       // String filePath = "/opt/zsl/uploadFiles/zw/demo/疫情-博客.xlsx";
        // String saveFileath = "g:/zsl/1";
        /*try {
            download(filePath,saveFileath,connect(ip,username,password,port));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        String filePath = "/opt/zsl/uploadFiles/zw/";
        String saveFileath = "g:/zsl/4";
        try {
            downloadTask(filePath,saveFileath,connect(ip,username,password,port));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建session 链接
     * @param sshModel
     * @return
     */
    public static Session connect(SshModel sshModel) throws Exception {
        return connect(sshModel.getIp(),sshModel.getUsername(),sshModel.getPassword(),sshModel.getPort());
    }

    /**
     * 创建session 链接
     * @param ip 服务器ip
     * @param username 用户名
     * @param password 密码
     * @param port 端口号
     * @return 返回SESSION
     * @throws Exception
     */
    public static Session connect(String ip,
                                  String username,
                                  String password,
                                  int port) throws Exception {
        if(SESSION.get(ip)!=null){
            return SESSION.get(ip);
        }
        // 开启链接
        JSch jSch = new JSch();
        Session session = null;
        try {
            session = jSch.getSession(username,ip,port);
        } catch (JSchException e) {
            e.printStackTrace();
            throw new Exception("获取SESSION异常，"+e.getMessage());
        }
        session.setPassword(password);
        //设置第一次登陆的时候提示，可选值:(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");
        //30秒连接超时
        try {
            session.connect(30000);
        } catch (JSchException e) {
            e.printStackTrace();
            throw new Exception("获取连接发生错误，"+e.getMessage());
        }
        SESSION.put(ip,session);
        return session;
    }

    /**
     *
     * @param session
     * @return
     */
    public static ChannelSftp getChannelSftp(Session session) throws Exception {
        ChannelSftp sftp = null;
        // 通道
        Channel channel = null;
        try {
            channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            e.printStackTrace();
            throw new Exception("获取 channel通道失败，"+e.getMessage());
        }
        return sftp;
    }

    /**
     * 关闭
     * @param sftp
     */
    public static void colseChannelSftp(ChannelSftp sftp){
        if(sftp!=null){
            sftp.disconnect();
        }
    }
    /**
     * 文件上传到服务器指定的地址
     * @param uploadFilePath 上传的文件路径
     * @param inputStream 文件流
     * @param session session
     */
    public static void upload(String uploadFilePath, InputStream inputStream,String file_name,Session session) throws Exception {
        ChannelSftp sftp = null;
        try {
            sftp = getChannelSftp(session);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        // 上传
        upload(uploadFilePath,inputStream,file_name,sftp);
        colseChannelSftp(sftp);
    }

    /**
     * 文件上传到服务器指定的地址
     * @param uploadFilePath 上传的文件路径
     * @param inputStream 文件流
     * @param sftp sftp
     */
    public static void upload(String uploadFilePath, InputStream inputStream,String file_name,ChannelSftp sftp) throws Exception {
        //  判断 uploadFilePath 文件路径是否存在
        try {
            if(sftp.ls(uploadFilePath)==null){
                sftp.mkdir(uploadFilePath);
            }
        } catch (SftpException e) {
            sftp.mkdir(uploadFilePath);
        }
        //进入目标路径
        sftp.cd(uploadFilePath);
        // 开始上传文件
        logger.info(file_name+"开始上传文件");
        //中文名称的
        sftp.put(inputStream, new String(file_name.getBytes(),"UTF-8"));
        logger.info("上传结束");
    }

    /**
     * 下载
     * @param filePath 服务器上文件路径地址
     * @param saveFilePath 需要下载到本地的文件路径地址
     * @param session session
     */
    public static void download(String filePath,String saveFilePath,Session session) throws Exception {
        ChannelSftp sftp = null;
        try {
            sftp = getChannelSftp(session);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        // 上传
        download(filePath,saveFilePath,sftp);
        colseChannelSftp(sftp);
    }

    /**
     * 下载
     * @param filePath 服务器上文件路径地址
     * @param saveFilePath 需要下载到本地的文件路径地址
     * @param sftp
     */
    public static String download(String filePath,String saveFilePath,ChannelSftp sftp ) throws Exception {
        // 判断服务器上是否存在
        if(sftp.ls(filePath)==null){
            throw new Exception("文件不存在");
        }
        // 判断下载的路径是否存在，不存在就创建文件
        File file = new File(saveFilePath);
        if(!file.exists()){
            // 创建文件
            file.mkdirs();
        }
        // 开始上传文件
        logger.info("开始下载文件");
        sftp.get(filePath,saveFilePath);
        logger.info("下载完成");
        return filePath;
    }

    /**
     * 下载指定文件夹下面所有的文件
     * @param sshFilePath 服务器上面的文件夹
     * @param saveFilePath 本地保存的文件夹
     * @param session
     */
    public static void downloadTask(String sshFilePath,String saveFilePath,Session session) throws Exception {
        ChannelSftp sftp = null;
        try {
            sftp = getChannelSftp(session);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        // 上传
        downloadTask(sshFilePath,saveFilePath,sftp);
        colseChannelSftp(sftp);
    }

    /**
     * 下载指定文件夹下面所有的文件
     * @param sshFilePath 服务器上面的文件夹
     * @param saveFilePath 本地保存的文件夹
     * @param sftp
     */
    public static void downloadTask(String sshFilePath,String saveFilePath,ChannelSftp sftp) throws Exception {
        Vector conts = sftp.ls(sshFilePath);
        for (Iterator iterator = conts.iterator(); iterator.hasNext(); ) {
            ChannelSftp.LsEntry obj =  (ChannelSftp.LsEntry) iterator.next();
            SftpATTRS sftpATTRS = obj.getAttrs();
            // 文件名或者文件夹
            String filename = new String(obj.getFilename().getBytes(),"UTF-8");
            // 判断是否是文件夹还是文件
            boolean bl = sftpATTRS.isDir();
            if(bl){// 文件夹
                String[] arrs = filename.split("\\.");
                if((arrs.length > 0) && (arrs[0].length() > 0)){
                    downloadTask(sshFilePath+"/"+filename,saveFilePath+"/"+filename,sftp);
                }else{
                    continue;
                }

            }else{// 文件
                System.out.println("atime");
                System.out.println(sftpATTRS.getATime());
                System.out.println("mtime");
                System.out.println(sftpATTRS.getMTime());
                download(sshFilePath+"/"+filename,saveFilePath,sftp);
            }
        }
    }


}
