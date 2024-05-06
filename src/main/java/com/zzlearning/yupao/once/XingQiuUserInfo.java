package com.zzlearning.yupao.once;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 星球表格信息
 */
@Data
public class XingQiuUserInfo {
    /**
     * id
     */
    @ExcelProperty("planetCode")
    private String planetCode;
    /**
     * 用戶
     */
    @ExcelProperty("username")
    private String username;

}