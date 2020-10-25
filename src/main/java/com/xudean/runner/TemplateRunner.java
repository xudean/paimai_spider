package com.xudean.runner;

import com.xudean.util.PathUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;

/**
 * 因为SpringBoot项目中easyexcel
 */
@Component
public class TemplateRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        File file = new File(PathUtils.getTempatePath());
        File parent = file.getParentFile();
        if(!parent.exists()){
            parent.mkdir();
        }
        ClassPathResource classPathResource = new ClassPathResource("template/template.xlsx");
        InputStream inputStream =classPathResource.getInputStream();
        FileUtils.copyToFile(inputStream,file);

        //复制pingan模板
        File pingAnFile = new File(PathUtils.getPingAnTempatePath());
        File parentApingAn = pingAnFile.getParentFile();
        if(!parentApingAn.exists()){
            parentApingAn.mkdir();
        }
        ClassPathResource classPathResourcePingAn = new ClassPathResource("template/template_pingan.xls");
        InputStream inputStreamPingAn =classPathResourcePingAn.getInputStream();
        FileUtils.copyToFile(inputStreamPingAn,pingAnFile);
    }
}
