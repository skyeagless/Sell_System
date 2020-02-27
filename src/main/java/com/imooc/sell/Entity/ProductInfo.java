package com.imooc.sell.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imooc.sell.Utils.EnumUtil;
import com.imooc.sell.enums.ProductStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class ProductInfo {
    @Id
    private String productId;
    private String productName;
    private BigDecimal productPrice;
    //库存量
    private Integer productStock;
    private String productDescription;
    //商品图连接地址
    private String productIcon;
    //状态：0正常 1下架
    private Integer productStatus = ProductStatusEnum.UP.getCode();
    //类目编号
    private Integer categoryType;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum(){
        return EnumUtil.getByCode(productStatus,ProductStatusEnum.class);
    }
}
