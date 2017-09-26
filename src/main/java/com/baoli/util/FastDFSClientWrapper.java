package com.baoli.util;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.proto.storage.DownloadCallback;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-09-21 16:23
 ************************************************************/

@Component
public class FastDFSClientWrapper {

    private final Logger logger = LoggerFactory.getLogger(FastDFSClientWrapper.class);

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private Config config;   // 项目参数配置

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public StorePath uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return storePath;
    }

    /**
     * 将一段字符串生成一个文件上传
     *
     * @param content       文件内容
     * @param fileExtension
     * @return
     */
    public StorePath uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = fastFileStorageClient.uploadFile(stream, buff.length, fileExtension, null);
        return storePath;
    }

    // 封装http访问到的图片完整URL地址
    public String getResAccessUrl(StorePath storePath) {
        return config.getFastDfsNginxHosProtocol().
                concat("://").
                concat(config.getFastDfsNginxHost()).
                concat(":").
                concat(config.getFastDfsNginxPort()).
                concat("/").
                concat(storePath.getFullPath());
    }

    /**
     * 删除文件
     *
     * @param fileUrl 文件访问地址
     * @return
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            fastFileStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            logger.warn(e.getMessage());
        }
    }

    public void downloadFile(String groupName, String storePath, final OutputStream outputStream) throws IOException {
        fastFileStorageClient.downloadFile(groupName, storePath, new DownloadCallback<OutputStream>() {
            @Override
            public OutputStream recv(InputStream inputStream) throws IOException {
                byte[] b = new byte[1024];
                int length;
                while ((length = inputStream.read(b)) != -1) {
                    outputStream.write(b, 0, length);
                }
                return outputStream;
            }
        });
        outputStream.close();
    }

}
