package com.jk.service;

import com.jk.mapper.ShangpinMapper;
import com.jk.model.Shangpin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShangpinServiceImpl implements ShangpinService {
    @Autowired
    private ShangpinMapper smapper;

/*    @Override
    public Map<String,Object> queryShangpinList(int page, int rows) {
        Map<String,Object> map =  new HashMap<>();
        long zongshu = smapper.queryCount();
        int start = (page-1)*rows;
        List<Shangpin> list = smapper.queryShangpin(start,rows);
        map.put("rows", list);
        map.put("total", zongshu);
        return map;
    }*/

    @Override
    public void insertShangpin(Shangpin sp) {
        smapper.insertShangpin(sp);
    }

    @Override
    public void deleteShangpin(Integer sid) {
        smapper.deleteShangpin(sid);
    }

    @Override
    public Shangpin queryShangpinById(Integer sid) {
        return smapper.queryShangpinById(sid);
    }

    @Override
    public void updateShangpin(Shangpin sp) {
        smapper.updateShanghai(sp);
    }

    @Override
    public List<Shangpin> querySpList() {
        return smapper.querySpList();
    }

    @Override
    public void addSpList(List<Shangpin> list) {
        smapper.addSpList(list);
    }


/*
    @Component
    @Service(version = "1.0",interfaceClass =CarService.class)  application没加扫描语句的
    pom里添加依赖
            <dependency>
                <groupId>com.alibaba.spring.boot</groupId>
                <artifactId>dubbo-spring-boot-starter</artifactId>
                <version>2.0.0</version>
            </dependency>
*/
}
