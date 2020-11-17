package nk.gk.wyl.ftp.api;

import nk.gk.wyl.ftp.entity.SshModel;

import java.io.InputStream;

public interface FtpService {
    /**
     * 通过ssh上传到服务器上
     * @param inputStream 文件流
     * @param fileName 文件名称
     * @param uploadFilePath 文件上传路径
     * @param ip 服务器ip
     * @param username 用户名
     * @param password 密码
     * @param port 端口号
     * @throws Exception 异常信息
     */
    void uploadSsh(InputStream inputStream,
                   String fileName,
                   String uploadFilePath,
                   String ip,
                   String username,
                   String password,
                   int port) throws Exception;

    /**
     * ssh 下载文件
     * @param filePath 服务文件路径
     * @param saveFilePath 下载文件存放的文件夹路径
     * @param ip 服务器ip
     * @param username 用户名
     * @param password 密码
     * @param port 端口号
     * @throws Exception
     */
    void downSsh(String filePath,
                 String saveFilePath,
                 String ip,
                 String username,
                 String password,
                 int port) throws Exception;

    /**
     * ssh 下载文件
     * @param saveFilePath 下载文件存放的文件夹路径
     * @throws Exception
     */
    void downloadSsh(String saveFilePath,
                     SshModel sshModel) throws Exception;
}
