package com.baitaner.common.utils;

import com.baitaner.common.constant.ErrorCodeConfig;
import com.baitaner.common.domain.result.IDResult;
import com.baitaner.common.domain.result.Result;

/**
 * Created by zliu on 15/1/31.
 */
public class ResultUtils {
   public static Result getSuccess(){
       Result result = new Result();
       result.setErrorCode(ErrorCodeConfig.SUCCESS);
       result.setMsg("OK");
       return result;
   }
    public static IDResult getIDSuccess(Long id){
        IDResult result = new IDResult();
        result.setErrorCode(ErrorCodeConfig.SUCCESS);
        result.setMsg("OK");
        result.setPayload(id);
        return result;
    }
}
