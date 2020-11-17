package nk.gk.wyl.ftp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import nk.gk.wyl.ftp.api.FtpService;
import nk.gk.wyl.ftp.entity.SshModel;
import nk.gk.wyl.ftp.entity.result.Response;
import nk.gk.wyl.ftp.ssh.SshConfig;
import nk.gk.wyl.ftp.util.CommonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/excel")
@Api(tags = "FTP接口")
public class FtpController {
    @Autowired
    private FtpService ftpService;

    @PostMapping(value = "upload",consumes = "multipart/*",headers = "content-type=multipart/form-data")
    @ApiOperation(value = "上传文件")
    public @ResponseBody
    Response uploadTxt(MultipartFile file){
        try {
            SshModel sshModel = SshConfig.getSshModel();
            CommonValidator.checkFile(file);
            ftpService.uploadSsh(file.getInputStream(),file.getOriginalFilename(),sshModel.getPath(),sshModel.getIp(),sshModel.getUsername(),sshModel.getPassword(),sshModel.getPort());
            return new Response().success("success");
        } catch (Exception e) {
            e.printStackTrace();
            return new Response().error(e.getMessage());
        }
    }
}
