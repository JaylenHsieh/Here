package com.hdu.newe.here.page.main.variousdata.adapter;

import com.hdu.newe.here.page.main.variousdata.bean.ExpandDataBean;

/**
 *
 * @author pope
 * @date 2018/4/10
 */

public interface ParentItemClickListener {

    /**
     * 展开子Item
     * @param expandDataBean
     */
    void onExpandChildren(ExpandDataBean expandDataBean);

    /**
     * 隐藏子Item
     * @param expandDataBean
     */
    void onHideChildren(ExpandDataBean expandDataBean);

}
