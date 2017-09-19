/**  
 * Project Name:mababy  
 * File Name:PageUtil.java  
 * Package Name:com.soa.common.util  
 * Date:2015年12月9日上午9:53:05  
 * Copyright (c) 2015, modernavenue.com All Rights Reserved.  
 *  
*/

package com.ma.wallet.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.Assert;

/**
 * ClassName:PageUtil <br/>
 * Function: 分页相关工具类<br/>
 * @see
 */
public class PageUtil {
    public static final int DEFAULT_PAGE_NO = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;

    public static RowBounds createRowBounds(Integer pageNo, Integer pageSize) {
        if (pageNo == null) {
            pageNo = DEFAULT_PAGE_NO;
        }
        if (pageSize == null) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        Assert.isTrue(pageNo > 0, "pageNo error");
        Assert.isTrue(pageSize > 0, "pageSize error");
        return new RowBounds((pageNo - 1) * pageSize, pageSize);
    }

    public static int[] createOffsetAndEnd(Integer pageNo, Integer pageSize) {
        if (pageNo == null) {
            pageNo = DEFAULT_PAGE_NO;
        }
        if (pageSize == null) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return new int[] { (pageNo -1) * pageSize, pageNo * pageSize };
    }

    public static int getPageNo(String pageno){
		if (StringUtils.isEmpty(pageno)) {
			return 1;
		}
		try {
			return Integer.parseInt(pageno);
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}
	
	public static int getPageSize(String pagesize){
		if (StringUtils.isEmpty(pagesize)) {
			return 10;
		}
		try {
			return Integer.parseInt(pagesize);
		} catch (Exception e) {
			e.printStackTrace();
			return 10;
		}
	}
}
