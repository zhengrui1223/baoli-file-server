package com.baoli.util;

import org.springframework.stereotype.Component;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-09-22 15:56
 ************************************************************/

@Component
public class ImageCheck {

    private MimetypesFileTypeMap fileTypeMap;

    public ImageCheck() {
        fileTypeMap = new MimetypesFileTypeMap();
        /* 不添加下面的类型会造成误判 详见：http://stackoverflow.com/questions/4855627/java-mimetypesfiletypemap-always-returning-application-octet-stream-on-android-e*/
        fileTypeMap.addMimeTypes("image png tif jpg jpeg bmp");
    }

    public boolean isImage(File file) {
        String mimeType = fileTypeMap.getContentType(file);
        String type = mimeType.split("/")[0];
        return type.equals("image");
    }

    public boolean isImage(String fileName) {
        String mimeType = fileTypeMap.getContentType(fileName);
        String type = mimeType.split("/")[0];
        return type.equals("image");
    }

}
