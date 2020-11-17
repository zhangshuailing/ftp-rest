package nk.gk.wyl.ftp.impl;

import nk.gk.wyl.ftp.api.FtpService;
import nk.gk.wyl.ftp.entity.SshModel;
import nk.gk.wyl.ftp.util.ftp.SshUtil;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class FtpServiceImpl implements FtpService {

    /**
     * 通过ssh上传到服务器上
     *
     * @param inputStream    文件流
     * @param uploadFilePath 文件上传路径
     * @param ip             服务器ip
     * @param username       用户名
     * @param password       密码
     * @param port           端口号
     * @throws Exception 异常信息
     */
    @Override
    public void uploadSsh(InputStream inputStream, String fileName, String uploadFilePath, String ip, String username, String password, int port) throws Exception {
        SshUtil.upload(uploadFilePath,inputStream,fileName,SshUtil.connect(ip,username,password,port));
    }

    /**
     * ssh 下载文件
     *
     * @param filePath     服务文件路径
     * @param saveFilePath 下载文件存放的文件夹路径
     * @param ip           服务器ip
     * @param username     用户名
     * @param password     密码
     * @param port         端口号
     * @throws Exception
     */
    @Override
    public void downSsh(String filePath, String saveFilePath, String ip, String username, String password, int port) throws Exception {
        SshUtil.download(filePath,saveFilePath,SshUtil.connect(ip,username,password,port));
    }

    /**
     * ssh 下载文件
     *
     * @param saveFilePath 下载文件存放的文件夹路径
     * @param sshModel
     * @throws Exception
     */
    @Override
    public void downloadSsh(String saveFilePath, SshModel sshModel) throws Exception {
        SshUtil.downloadTask(sshModel.getPath(),saveFilePath,SshUtil.connect(sshModel));
    }
}
