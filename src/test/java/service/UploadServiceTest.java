package service;

import com.baoli.SpringBootRunApplication;
import com.baoli.model.UploadFileInfo;
import com.baoli.service.IUploadFileInfoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-09-22 9:45
 ************************************************************/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootRunApplication.class)
public class UploadServiceTest {

    @Autowired
    private IUploadFileInfoService uploadFileInfoService;

    @Test
    public void testInsert() {
        UploadFileInfo uploadFileInfo = new UploadFileInfo();
        uploadFileInfo.setFileName("test");
        uploadFileInfo.setFilePath("http://172.19.10.44/group1/M00/00/00/rBMKLFnDO3OAXDzyAABLOgxub-Q914_big.jpg");
        UploadFileInfo insert = uploadFileInfoService.insert(uploadFileInfo);
        Assert.assertNotNull(insert);
    }

    @Test
    public void testGetAll() throws JsonProcessingException {
        PageInfo<UploadFileInfo> uploadFileList = uploadFileInfoService.getUploadFileList(null, null, null, null, 1, 5);
        System.out.println(new ObjectMapper().writeValueAsString(uploadFileList));
    }

    @Test
    public void testDelete() {
        uploadFileInfoService.deleteFile(1);
    }

}
