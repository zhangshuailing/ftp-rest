package nk.gk.wyl.ftp.task;

import nk.gk.wyl.ftp.api.FtpService;
import nk.gk.wyl.ftp.entity.SshModel;
import nk.gk.wyl.ftp.ssh.SshConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JobTask {
    private Logger logger = LoggerFactory.getLogger(JobTask.class);
    @Autowired
    private FtpService ftpService;
    @Value("${uploadPaths}")
    private String uploadPaths;

    @Scheduled(cron = "0 0/1 * * * *")
    public void run(){
        SshModel sshModel = SshConfig.getSshModel();
        try {
            logger.info("开始拷贝文件");
            ftpService.downloadSsh(uploadPaths,sshModel);
            logger.info("拷贝完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
