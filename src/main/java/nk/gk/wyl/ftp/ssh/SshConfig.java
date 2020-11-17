package nk.gk.wyl.ftp.ssh;

import nk.gk.wyl.ftp.entity.SshModel;
import org.springframework.beans.factory.annotation.Value;

public class SshConfig {

    public static SshModel sshModel;

    public static SshModel getSshModel() {
        return sshModel;
    }

    public static void setSshModel(SshModel sshModel) {
        SshConfig.sshModel = sshModel;
    }
}
