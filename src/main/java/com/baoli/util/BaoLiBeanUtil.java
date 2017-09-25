package com.baoli.util;

import com.baoli.model.UploadFileInfo;
import com.baoli.vo.UploadFileInfoVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-09-22 8:58
 ************************************************************/
public class BaoLiBeanUtil {

    public static UploadFileInfo convertUploadFileInfoVO2UploadFileInfo(UploadFileInfoVO uploadFileInfoVO) {
        UploadFileInfo uploadFileInfo = new UploadFileInfo();
        BeanUtils.copyProperties(uploadFileInfoVO, uploadFileInfo);
        return uploadFileInfo;
    }

    private static UploadFileInfoVO convertUploadFileInfo2UploadFileInfoVO(UploadFileInfo uploadFileInfo) {
        UploadFileInfoVO uploadFileInfoVO = new UploadFileInfoVO();
        BeanUtils.copyProperties(uploadFileInfo, uploadFileInfoVO);
        return uploadFileInfoVO;
    }

    public static List<UploadFileInfoVO> convertUploadFileInfos2UploadFileInfoVOs(List<UploadFileInfo> uploadFileList) {

        List<UploadFileInfoVO> uploadFileInfoVOs = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(uploadFileList)) {
            for (UploadFileInfo uploadFileInfo : uploadFileList) {
                UploadFileInfoVO uploadFileInfoVO = convertUploadFileInfo2UploadFileInfoVO(uploadFileInfo);
                uploadFileInfoVOs.add(uploadFileInfoVO);
            }
        }
        return uploadFileInfoVOs;
    }

}
