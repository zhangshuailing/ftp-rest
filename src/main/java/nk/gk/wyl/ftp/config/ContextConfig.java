package nk.gk.wyl.ftp.config;

import nk.gk.wyl.ftp.entity.SshModel;
import nk.gk.wyl.ftp.ssh.SshConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 文件初始化数据
 */
@Component
public class ContextConfig {

    @Value("${ssh1.ip}")
    private String ip;

    @Value("${ssh1.username}")
    private String username;

    @Value("${ssh1.password}")
    private String password;

    @Value("${ssh1.port}")
    private int port;

    @Value("${ssh1.path}")
    private String path;


    @Bean
    public int initStatic(){
        SshConfig.setSshModel(getSshModel());
        return 0;
    }


    public SshModel getSshModel(){
        SshModel sshModel = new SshModel();
        sshModel.setIp(ip);
        sshModel.setUsername(username);
        sshModel.setPassword(password);
        sshModel.setPort(port);
        sshModel.setPath(path);
        return sshModel;
    }
    /**
     * 获取项目路径
     * @return
     */
    public String getContextPath(){
        String path = System.getProperty("user.dir");
        return path;
    }
}
