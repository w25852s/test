package com.wangshuai.enum1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ExceptionEnum {
    PRICE_IS_NULL(400,"价格不能为空"),
    BRAND_NOT_FOUND(404,"品牌没有查询到"),
    SPEC_GROUP_NOT_FOUND(404,"规格组没查询到"),
    CATEGORY_NOT_FOUND(404,"类型没查询到"),
    SPEC_PARAMS_NOT_FOUND(404,"规格参数没查询到"),
    GOODS_NOT_FOUND(404,"商品没查询到"),
    GOODS_DETAIL_NOT_FOUND(404,"商品详情没查询到"),
    STOCK_NOT_FOUND(404,"库存没查询到"),
    SKU_NOT_FOUND(404,"商品SKU没查询到"),
    BRAND_SAVE_FAILURE(505,"商品存入失败"),
    SPEC_GROUP_SAVE_FAILURE(505,"规格组存入失败"),
    PARAMS_SAVE_FAILURE(505,"商品规格参数存入失败"),
    GOOD_SAVE_FAILURE(505,"商品存入失败"),
    INVALID_FILE(400,"无效的文件"),
    UPLOAD_IMAGE_FAIL(500,"上传图片失败"),
    USER_USERNAME_ISUSED(400,"用户名被占用"),
    PHONE_USERNAME_ISUSED(400,"手机用被占用"),
    INVALID_TYPE(400,"数据类型有误"),
    ;
    private int code;
    private String message;
}
