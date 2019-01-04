package com.zzz.smo.commodityservice.VO;

import lombok.Data;

/**
 * @Author: Sean
 * @Date: 2019/1/1 23:19
 */
@Data
public class CommodityInfoVO {
    private String id;
    private String name;
    private String description;
    private int type;
    private int status;
}
