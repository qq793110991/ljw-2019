package com.jk.controller;

import com.jk.model.Shangpin;
import com.jk.service.ShangpinService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("caozuo")
public class ShangpinController {
    @Autowired
    private SolrClient client;

    @Autowired
    private ShangpinService spservice;

    @RequestMapping("toindex")
    public String toindex() {
        return "index";
    }

    @RequestMapping("toadd")
    public String toadd() {
        return "add";
    }

    /**
     * 新增/修改 索引
     * 当 id 存在的时候, 此方法是修改(当然, 我这里用的 uuid, 不会存在的), 如果 id 不存在, 则是新增
     * @return
     */
    @RequestMapping("addShangpin")
    @ResponseBody
    public String addShangpin(Shangpin sp) {
        try {
        //执行数据库新增
        spservice.insertShangpin(sp);
//      String uuid = UUID.randomUUID().toString().replaceAll("-", "");

            SolrInputDocument doc = new SolrInputDocument();
            doc.setField("id", sp.getSid());
            doc.setField("sname", sp.getSname());
            doc.setField("stype", sp.getStype());
            doc.setField("sprice", sp.getSprice());

            /* 如果spring.data.solr.host 里面配置到 core了, 那么这里就不需要传 collection1 这个参数
             * 下面都是一样的
             */
            client.add("core1", doc);
            //client.commit();
            client.commit("core1");
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "error";
    }
    /**
     * 根据id删除索引
     * @param sid
     * @return
     */
    @RequestMapping("deleteShangpin")
    @ResponseBody
    public String deleteShangpin(Integer sid)  {
        try {
            spservice.deleteShangpin(sid);

            client.deleteById("core1",sid.toString());
            client.commit("core1");

            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping("chaxiu")
    public String chaxiu(Integer sid, Model model) {
        Shangpin sp = spservice.queryShangpinById(sid);
        model.addAttribute("sp", sp);
        return "upd";
    }

    /**
     * 删除所有的索引
     * @return
     */
    @RequestMapping("deleteAll")
    public String deleteAll(){
        try {
            client.deleteByQuery("core1","*:*");
            client.commit("core1");
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    /**
     * 根据id查询索引
     * @return
     * @throws Exception
     */
    @RequestMapping("getById")
    public String getById() throws Exception {
        SolrDocument document = client.getById("core1", "7");
        System.out.println(document);
        return document.toString();
    }



    /**
     * 综合查询: 在综合查询中, 有按条件查询, 条件过滤, 排序, 分页, 高亮显示, 获取部分域信息
     * @return
     */
    @RequestMapping("search")
    @ResponseBody
    public Map<String, Object> search(Shangpin sp,Integer page,Integer rows){


        //返回到前台
        Map<String, Object> map1=new HashMap<>();

        try {
            //存放所有的查询条件
            SolrQuery params = new SolrQuery();

            //查询条件, 这里的 q 对应 下面图片标红的地方
            if(sp.getSname()!=null && !"".equals(sp.getSname())){
                params.set("q", sp.getSname());
            }else {
                params.set("q", "*:*");
            }


            //过滤条件
            // params.set("fq", "carPrice:["+car.get+" TO "++"]");

            //排序
            params.addSort("sprice", SolrQuery.ORDER.desc);

            //分页
            if(page==null){
                params.setStart(0);
            }else {
                params.setStart((page-1)*rows);
            }
            if(rows==null){
                params.setRows(5);
            }else {
                params.setRows(rows);
            }

            //默认域
            params.set("df", "sname");

            //只查询指定域
            //params.set("fl", "id,product_title,product_price");

            //高亮
            //打开开关
            params.setHighlight(true);
            //指定高亮域
            params.addHighlightField("sname");
            //设置前缀
            params.setHighlightSimplePre("<span style='color:red'>");
            //设置后缀
            params.setHighlightSimplePost("</span>");

            //查询后返回的对象
            QueryResponse queryResponse = client.query("core1",params);
            //查询后返回的对象 获得真正的查询结果
            SolrDocumentList results = queryResponse.getResults();
            //查询的总条数
            long numFound = results.getNumFound();

            System.out.println(numFound);

            //获取高亮显示的结果, 高亮显示的结果和查询结果是分开放的
            Map<String, Map<String, List<String>>> highlight = queryResponse.getHighlighting();

            //创建list集合接收我们循环的参数
            List<Shangpin> list1 =new ArrayList<>();
            for (SolrDocument result : results) {

                Shangpin sp1=new Shangpin();
                String highFile="";

                Map<String, List<String>> map = highlight.get(result.get("id"));
                List<String> list = map.get("sname");
                if(list==null){
                    highFile= (String) result.get("sname");
                }else {
                    highFile=list.get(0);
                }

                sp1.setSid(Integer.parseInt(result.get("id").toString()));
                sp1.setStype((String) result.get("stype"));
                sp1.setSprice((Integer) result.get("sprice"));
                sp1.setSname(highFile);

                list1.add(sp1);
            }
            map1.put("total",numFound);
            map1.put("rows",list1);
            return map1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
