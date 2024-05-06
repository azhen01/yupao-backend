package com.zzlearning.yupao.once;


import com.alibaba.excel.EasyExcel;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 导入excel数据
 * @author 3647
 */
public class ImportExcel {

    public static void main(String[] args) {
        String fileName = "E:/javaweb_ppt/testExcel.xlsx";
        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行
        // 具体需要返回多少行可以在`PageReadListener`的构造函数设置
        List<XingQiuUserInfo> userInfoList =
                EasyExcel.read(fileName).head(TableListener.class).sheet().doReadSync();
        System.out.println("总数：" + userInfoList.size());
        Map<String,List<XingQiuUserInfo>> listMap = userInfoList.stream().filter(userInfo -> StringUtils.isNotEmpty(userInfo.getUsername())).collect(Collectors.groupingBy(XingQiuUserInfo::getUsername));
        for (Map.Entry<String, List<XingQiuUserInfo>> stringListEntry : listMap.entrySet()) {
            if (stringListEntry.getValue().size() > 1){
                System.out.println("username = " + stringListEntry.getKey());
            }
        }
        System.out.println("不重复的昵称数量" + listMap.keySet().size());
    }

}
