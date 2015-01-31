package com.baitaner.common.service.impl;

import com.baitaner.common.cache.ICache;
import com.baitaner.common.constant.ConstConfig;
import com.baitaner.common.domain.base.User;
import com.baitaner.common.mapper.IUserMapper;
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
    private IUserMapper userMapper;

    @Autowired
    private ICache cache;

    //每个公司最新的前5条物品ID


    //物品缓存


    /* (non-Javadoc)
     * @see com.baitaner.common.service.ICacheService#getUser(long)
     */
    @Override
    public User getUser(long userId) {
        try {
            String userKey = CacheKeyUtil.getUserKey(userId);
            return JsonUtil.string2Object(cache.get(userKey),User.class);
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
     * @see com.baitaner.common.service.ICacheService#putUser(com.baitaner.common.domain.base.User)
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

    @Override
    public String getUserSession(String session) {
        try {
            String msgKey = CacheKeyUtil.getUserSessionKey(session);
            return cache.get(msgKey);
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
    public void deleteUserSession(long userId) {

    }

    @Override
    public void clearUserSession(Long userId) {

    }
}
