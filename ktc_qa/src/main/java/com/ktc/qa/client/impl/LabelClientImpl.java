package com.ktc.qa.client.impl;

import com.ktc.qa.client.LabelClient;
import com.xss.entity.Result;
import com.xss.entity.StatusCode;
import org.springframework.stereotype.Component;

/**
 * @author XSS
 * @date 2020/8/27
 * @desc
 */
@Component
public class LabelClientImpl implements LabelClient {

    @Override
    public Result findById(String id) {

        return new Result(false, StatusCode.ERROR,"熔断器生效啦.....");
    }
}
