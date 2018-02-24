package com.baoli.exception;

/*******************************************  
 * @Description: TODO
 * @Author: jerry.zheng
 * @Date Created in 14:16 2018\2\24 0024        
 *******************************************/

public class BLServiceException extends BLBaseException {

    public BLServiceException(String errorCode) {
        super(errorCode, null);
    }

    public BLServiceException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }
}
