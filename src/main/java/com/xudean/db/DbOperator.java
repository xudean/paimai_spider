package com.xudean.db;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xudean.pojo.SpiderItem;
import com.xudean.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class DbOperator {
    public static final String DB_PATH = "db/db.json";
    private Map<Long, SpiderItem> datas = null;

    public DbOperator() {
        try {
            File file = new File(DB_PATH);
            if(!file.exists()){
                file.createNewFile();
                FileUtils.write(file,"{}",Charset.defaultCharset());
            }
            String contents = FileUtils.readFileToString(file, Charset.defaultCharset());
            datas = JSONUtil.toObject(contents, new TypeReference<Map<Long, SpiderItem>>() {
            });
        } catch (IOException e) {
            datas = new HashMap<>();
        }
    }

    public synchronized Long addItem(SpiderItem spiderItem) {
        Long id = System.currentTimeMillis();
        spiderItem.setTaskId(id);
        datas.put(id, spiderItem);
        //添加之后持久化一下
        try {
            FileUtils.write(new File(DB_PATH), JSONUtil.toPrettyJSON(datas), Charset.defaultCharset());
        } catch (IOException e) {
            log.error("数据持久化失败");
        }
        return id;
    }

    public synchronized void updateItem(SpiderItem spiderItem) {
        datas.put(spiderItem.getTaskId(), spiderItem);
        //添加之后持久化一下
        try {
            FileUtils.write(new File(DB_PATH), JSONUtil.toPrettyJSON(datas), Charset.defaultCharset());
        } catch (IOException e) {
            log.error("数据持久化失败");
        }
    }

    public synchronized void updateItem(SpiderItem spiderItem, Long id) {
        datas.put(id, spiderItem);
        //添加之后持久化一下
        try {
            FileUtils.write(new File(DB_PATH), JSONUtil.toPrettyJSON(datas), Charset.defaultCharset());
        } catch (IOException e) {
            log.error("数据持久化失败");
        }
    }

    public synchronized List<SpiderItem> selectAll() {
        Map<Long, SpiderItem> longSpiderItemMap = sortByKey(datas, false);
        return new ArrayList<>(longSpiderItemMap.values());
    }

    public synchronized void clear(){
        datas.clear();
        try {
            FileUtils.write(new File(DB_PATH), JSONUtil.toPrettyJSON(datas), Charset.defaultCharset());
        } catch (IOException e) {
            log.error("清空失败！");
        }
    }

    /**
     * 根据map的key排序
     *
     * @param map    待排序的map
     * @param isDesc 是否降序，true：降序，false：升序
     * @return 排序好的map
     * @author zero 2019/04/08
     */
    public static <K extends Comparable<? super K>, V> Map<K, V> sortByKey(Map<K, V> map, boolean isDesc) {
        Map<K, V> result = new HashMap<>();
        if (isDesc) {
            map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByKey().reversed())
                    .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        } else {
            map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByKey())
                    .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        }
        return result;
    }

    public SpiderItem getById(Long id){
        return datas.get(id);
    }
}



