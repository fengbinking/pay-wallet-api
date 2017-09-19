/**  
 * Project Name:mababy  
 * File Name:ICache.java  
 * Package Name:com.soa.common.cache  
 * Date:2015年12月18日下午2:11:54  
 * Copyright (c) 2015, modernavenue.com All Rights Reserved.  
 *  
*/

package com.ma.wallet.core.cache;

/**
 * ClassName:Cache <br/>
 * Date: 2015年12月18日 下午2:11:54 <br/>
 */
public interface Cache {

    public <K, V> boolean set(K key, V value);

    public <K, V> boolean set(K key, V value, int exp);

    public <K> Object get(K key);

    public <K, V> V get(K key, Class<V> valueType);

    public <K> long delete(K key);

}
