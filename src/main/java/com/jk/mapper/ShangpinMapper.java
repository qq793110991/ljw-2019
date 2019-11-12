package com.jk.mapper;

import com.jk.model.Shangpin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShangpinMapper {

/*    long queryCount();

    List<Shangpin> queryShangpin(@Param("sta") int start, @Param("rows") int rows);*/

    void insertShangpin(Shangpin sp);

    void deleteShangpin(@Param("sid") Integer sid);

    Shangpin queryShangpinById(Integer sid);

    void updateShanghai(Shangpin sp);

    List<Shangpin> querySpList();

    void addSpList(List<Shangpin> list);
}