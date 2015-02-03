package com.baitaner.common.service.impl;

import com.baitaner.common.cache.ICache;
import com.baitaner.common.constant.ConstConfig;
import com.baitaner.common.domain.base.Goods;
import com.baitaner.common.domain.base.User;
import com.baitaner.common.domain.request.user.BindGroup;
import com.baitaner.common.mapper.base.UserMapper;
import com.baitaner.common.service.ICacheService;
import com.baitaner.common.utils.CacheKeyUtil;
import com.baitaner.common.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("cacheService")
public class CacheServiceImpl implements ICacheService {

    private static final String TAG = CacheServiceImpl.class.getSimpleName();

    private final int BIND_TIME_OUT = 5 * 60;//sec

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ICache cache;

    /* (non-Javadoc)
     * @see com.baitaner.common.service.ICacheService#getUser(long)
     */
    @Override
    public User getUser(long userId) {
        try {
            String userKey = CacheKeyUtil.getUserKey(userId);
            return JsonUtil.string2Object(cache.get(userKey), User.class);
        } catch (Exception ex) {
            return null;
        }
    }

    /* (non-Javadoc)
     * @see com.baitaner.common.service.ICacheService#deleteUser(long)
     */
    @Override
    public void deleteUser(long userId) {
        try {
            String userKey = CacheKeyUtil.getUserKey(userId);
            cache.delete(userKey);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see com.baitaner.common.service.ICacheService#putGoods(com.baitaner.common.domain.base.User)
     */
    @Override
    public void putUser(User user) {
        try {
            String userKey = CacheKeyUtil.getUserKey(user.getId());
            String value = JsonUtil.object2String(user);
            cache.put(userKey, value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //商品缓存
    @Override
    public Goods getGoods(long goodsId) {
        try {
            String goodsKey = CacheKeyUtil.getGoodsKey(goodsId);
            return JsonUtil.string2Object(cache.get(goodsKey), Goods.class);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void deleteGoods(long goodsId) {
        try {
            String goodsKey = CacheKeyUtil.getGoodsKey(goodsId);
            cache.delete(goodsKey);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void putGoods(Goods goods) {
        try {
            String goodsKey = CacheKeyUtil.getGoodsKey(goods.getId());
            cache.put(goodsKey, JsonUtil.object2String(goods));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public Long getUserSession(String session) {
        try {
            String msgKey = CacheKeyUtil.getUserSessionKey(session);
            return Long.valueOf(cache.get(msgKey));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /* (non-Javadoc)
     * @see com.baitaner.common.service.ICacheService#putUserSession(long, java.lang.String)
     */
    @Override
    public void putUserSession(long userId, String session) {
        try {
            String msgKey = CacheKeyUtil.getUserSessionKey(session);
            cache.putEx(msgKey, ConstConfig.AUTH_TIMEOUT, String.valueOf(userId));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteUserSession(String session) {
        try {
            String msgKey = CacheKeyUtil.getUserSessionKey(session);
            cache.delete(msgKey);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //公司的前十条发布的并解锁的物品的id列表
    @Override
    public void putPublishList(Long groupId, Long goodsId) {
        if(goodsId==null ||groupId ==null){
            return;
        }
        try {
            String msgKey = CacheKeyUtil.getPublishKey(groupId);
            if (cache.getSetLen(msgKey) > ConstConfig.GET_INFO_MAX) {
                cache.popFromSet(msgKey);
            }
            cache.putToSet(msgKey,String.valueOf(goodsId));

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //临时密码缓存
    @Override
    public void putTempPassword(long userId, String password) {
        try {
            String msgKey = CacheKeyUtil.getTempPasswordKey(userId);
            cache.putEx(msgKey, ConstConfig.TEMP_TIMEOUT, password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public String getTempPassword(long userId) {
        try {
            String msgKey = CacheKeyUtil.getTempPasswordKey(userId);
            return cache.get(msgKey);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
    @Override
    public void deleteTempPassword(Long userId) {
        try {
            String msgKey = CacheKeyUtil.getTempPasswordKey(userId);
            cache.delete(msgKey);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //公司认证验证码
    @Override
    public void putGroupAuth(BindGroup bindGroup, String rcode) {
        try {
            String msgKey = CacheKeyUtil.getGroupAuthKey(rcode);
            cache.putEx(msgKey, ConstConfig.TEMP_TIMEOUT, JsonUtil.object2String(bindGroup));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public BindGroup getGroupAuth(String rcode) {
        try {
            String msgKey = CacheKeyUtil.getGroupAuthKey(rcode);
            return JsonUtil.string2Object(cache.get(msgKey), BindGroup.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    @Override
    public void deleteGroupAuth(String rcode) {
        try {
            String msgKey = CacheKeyUtil.getGroupAuthKey(rcode);
            cache.delete(msgKey);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //校验码
    @Override
    public void putCheckCode(Long userId, String code) {
        try {
            String msgKey = CacheKeyUtil.getCheckCode(code);
            cache.putEx(msgKey, ConstConfig.TEMP_TIMEOUT, String.valueOf(userId));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public Long getCheckCode(String rcode) {
        try {
            String msgKey = CacheKeyUtil.getCheckCode(rcode);
            return  Long.parseLong(cache.get(msgKey));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0l;
    }
    @Override
    public void deleteCheckCode(String rcode) {
        try {
            String msgKey = CacheKeyUtil.getCheckCode(rcode);
            cache.delete(msgKey);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}