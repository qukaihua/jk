package com.qu.common;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by 瞿凯华 on 2018/6/8 0008.
 */
public class TokenCache {
   public static Logger logger = LoggerFactory.getLogger(TokenCache.class);

   private static LoadingCache <String,Object>cache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(3000).expireAfterWrite(12, TimeUnit.HOURS).build(new CacheLoader<String, Object>() {

        public Object load(String name){
            return "null";
        }
   });

    public static void setKey(String key,String token){
        cache.put(key,token);
    }

    public static  String getKey(String key){
        String token = null;
        try {
            token = (String) cache.get(key);
        } catch (ExecutionException e) {
           logger.error(e.toString());
        }
        return token;

    }
}
