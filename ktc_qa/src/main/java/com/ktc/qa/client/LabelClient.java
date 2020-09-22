package com.ktc.qa.client;

import com.ktc.qa.client.impl.LabelClientImpl;
import com.xss.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author XSS
 * @date 2020/8/26
 * @desc
 */
@FeignClient(value = "ktc-base",fallback = LabelClientImpl.class)  //调用微服务的名字
public interface LabelClient {

    @RequestMapping(value = "/label/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id);
}
