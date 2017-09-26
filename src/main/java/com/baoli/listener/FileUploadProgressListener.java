package com.baoli.listener;

import com.baoli.vo.Progress;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-09-26 14:02
 ************************************************************/

@Component
public class FileUploadProgressListener implements ProgressListener {
    private HttpSession session;

    public void setSession(HttpSession session) {
        this.session = session;
        Progress status = new Progress();//保存上传状态
        session.setAttribute("status", status);
    }

    @Override
    public void update(long bytesRead, long contentLength, int items) {
        Progress status = (Progress) session.getAttribute("status");
        status.setBytesRead(bytesRead);
        status.setContentLength(contentLength);
        status.setItems(items);
    }
}
