package com.baitaner.server.httpserver.init;

import com.baitaner.common.constant.ConstConfig;
import com.baitaner.common.mapper.base.GroupMapper;
import com.baitaner.common.service.ICacheService;
import com.baitaner.common.service.IIndentService;
import com.baitaner.common.service.IGoodsService;
import com.baitaner.common.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-3-3
 * Time: 上午9:56
 * To change this template use File | Settings | File Templates.
 */
@Component()
public class ClassLoad {
    @Autowired
    private IUserService userService;

    @Autowired
    private ICacheService cacheService;

    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private IGoodsService infoService;
    @Autowired
    private IIndentService indentService;

    @PostConstruct
    public void init(){
        System.out.println("Start init....");
        initDir();
        initDate();
    }

    private void initDate(){
        //初始化用户
    }

    private void initDir(){
        File file = new File(ConstConfig.PACKAGE_DIR);
        file.mkdirs();
        file = new File(ConstConfig.CONFIG_PATH);
        file.mkdirs();
        file = new File(ConstConfig.LOGS_DIR);
        file.mkdirs();
    }

}
