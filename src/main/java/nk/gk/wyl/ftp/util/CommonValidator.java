package nk.gk.wyl.ftp.util;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: 参数校验
 * @Author: zhangshuailing
 * @CreateDate: 2020/8/29 9:45
 * @UpdateUser: zhangshuailing
 * @UpdateDate: 2020/8/29 9:45
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class CommonValidator {



    /**
     * 校验doc docx
     * @param file 文件
     * @throws Exception 异常信息
     */
    public static String checkFile(MultipartFile file) throws Exception {
        String name = file.getOriginalFilename();
        String suffix = name.substring(name.lastIndexOf(".")+1,name.length());
        long size = file.getSize();
        if(size==0){
            throw new Exception("上传的文件为空");
        }
        return suffix.toLowerCase();
    }

}
