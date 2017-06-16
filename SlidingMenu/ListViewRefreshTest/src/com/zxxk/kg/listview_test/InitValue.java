package com.zxxk.kg.listview_test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InitValue {
    /**
     * 模拟数据分页加载，
     * @param pageStart  起始数
     * @param pageSize   每页显示数目
     * @return
     */
    public static List<Map<String,Object>> initValue(int pageStart,int pageSize){
        Map<String,Object> map;
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        for(int i=0;i<pageSize;i++){
            map=new HashMap<String,Object>();
            map.put("text", "zxxk_demo修改");
            map.put("title", pageStart+"_ListView分页显示");
            ++pageStart;
            list.add(map);
        }
        return list;
    }
    
    
    /**
     * 刷新的数据
     * @param pageStart
     * @param pageSize
     * @param a 表示是刷新的内容
     * @return
     */
    public static List<Map<String,Object>> initValue(int pageStart,int pageSize , List<Map<String,Object>> data){
    	Map<String,Object> map;
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        for(int i=0;i<pageSize;i++){
            map=new HashMap<String,Object>();
            map.put("text", "zxxk_demo");
            map.put("title", pageStart +"_ListView刷新之后的内容");
            ++pageStart;
            list.add(map);
        }
        return list;
    }
}