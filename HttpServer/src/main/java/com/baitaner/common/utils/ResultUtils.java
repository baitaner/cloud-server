package com.baitaner.common.utils;

import com.baitaner.common.constant.ErrorCodeConfig;
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
}
