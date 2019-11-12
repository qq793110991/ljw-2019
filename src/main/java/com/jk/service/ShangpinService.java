package com.jk.service;

import com.jk.model.Shangpin;

import java.util.List;
import java.util.Map;

public interface ShangpinService {

  /*  Map queryShangpinList(int page, int rows);*/

    void insertShangpin(Shangpin sp);

    void deleteShangpin(Integer sid);

    Shangpin queryShangpinById(Integer sid);

    void updateShangpin(Shangpin sp);

    List<Shangpin> querySpList();

    void addSpList(List<Shangpin> list);
}
