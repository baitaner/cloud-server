package com.baitaner.common.thread;

import com.baitaner.common.constant.DateConstant;
import com.baitaner.common.domain.base.Goods;
import com.baitaner.common.enums.GoodsEnums;
import com.baitaner.common.mapper.base.GoodsMapper;
import com.baitaner.common.service.impl.CacheServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zliu on 15/2/4.
 */
@Service("goodsTimerThread")
public class GoodsTimerThread {
    private final int MAX = 100;
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private CacheServiceImpl cacheService;

    private boolean isRunning = false;
    public synchronized void monitor(){
        if(isRunning){
            return;
        }
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Running(),0, DateConstant.ONE_MIN);
    }
    private class Running extends TimerTask {
        @Override
        public void run() {
            //todo 获取列表检测
            Long count = goodsMapper.findByStatusAndLockSize(GoodsEnums.STATUS.PUBLISHED,GoodsEnums.IS_LOCK.UN_LOCK);
            int index = 0;
            int end = MAX;
            List<Goods> goodsList = new ArrayList<Goods>();
            while(true){
                if(index<count){
                     if(index+end>count){
                         end = (int) (count-index);
                     }
                     goodsList= goodsMapper.findByStatusAndLock(GoodsEnums.STATUS.PUBLISHED,GoodsEnums.IS_LOCK.UN_LOCK,index,end);
                     for(Goods goods:goodsList){
                         try {
                             if (!goods.getExpireTime().before(new Timestamp(System.currentTimeMillis()))) {
                                 goods.setStatus(GoodsEnums.STATUS.TERMINATE);
                                 goods.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                                 goodsMapper.update(goods);
                                 cacheService.putGoods(goods);
                             }
                         }catch (Exception ex){
                             ex.printStackTrace();
                         }
                     }
                     index = index+end;
                }else{
                    break;
                }
            }
        }
    }

}


