
package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 设备
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/shebei")
public class ShebeiController {
    private static final Logger logger = LoggerFactory.getLogger(ShebeiController.class);

    private static final String TABLE_NAME = "shebei";

    @Autowired
    private ShebeiService shebeiService;


    @Autowired
    private TokenService tokenService;

    @Autowired
    private BingliService bingliService;//档案
    @Autowired
    private DictionaryService dictionaryService;//字典
    @Autowired
    private GonggaoService gonggaoService;//公告
    @Autowired
    private KeshiService keshiService;//科室
    @Autowired
    private YaopinService yaopinService;//药品
    @Autowired
    private YishengService yishengService;//医护
    @Autowired
    private YonghuService yonghuService;//用户
    @Autowired
    private UsersService usersService;//管理员


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("用户".equals(role))
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        else if("医护".equals(role))
            params.put("yishengId",request.getSession().getAttribute("userId"));
        CommonUtil.checkMap(params);
        PageUtils page = shebeiService.queryPage(params);

        //字典表数据转换
        List<ShebeiView> list =(List<ShebeiView>)page.getList();
        for(ShebeiView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        ShebeiEntity shebei = shebeiService.selectById(id);
        if(shebei !=null){
            //entity转view
            ShebeiView view = new ShebeiView();
            BeanUtils.copyProperties( shebei , view );//把实体数据重构到view中
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody ShebeiEntity shebei, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,shebei:{}",this.getClass().getName(),shebei.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");

        Wrapper<ShebeiEntity> queryWrapper = new EntityWrapper<ShebeiEntity>()
            .eq("shebei_name", shebei.getShebeiName())
            .eq("shengchanchangjia", shebei.getShengchanchangjia())
            .eq("shebei_types", shebei.getShebeiTypes())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ShebeiEntity shebeiEntity = shebeiService.selectOne(queryWrapper);
        if(shebeiEntity==null){
            shebei.setInsertTime(new Date());
            shebei.setCreateTime(new Date());
            shebeiService.insert(shebei);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody ShebeiEntity shebei, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.debug("update方法:,,Controller:{},,shebei:{}",this.getClass().getName(),shebei.toString());
        ShebeiEntity oldShebeiEntity = shebeiService.selectById(shebei.getId());//查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
        if("".equals(shebei.getShebeiPhoto()) || "null".equals(shebei.getShebeiPhoto())){
                shebei.setShebeiPhoto(null);
        }

            shebeiService.updateById(shebei);//根据id更新
            return R.ok();
    }



    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        List<ShebeiEntity> oldShebeiList =shebeiService.selectBatchIds(Arrays.asList(ids));//要删除的数据
        shebeiService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName, HttpServletRequest request){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //.eq("time", new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
        try {
            List<ShebeiEntity> shebeiList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            ShebeiEntity shebeiEntity = new ShebeiEntity();
//                            shebeiEntity.setShebeiName(data.get(0));                    //设备名称 要改的
//                            shebeiEntity.setShebeiUuidNumber(data.get(0));                    //设备编号 要改的
//                            shebeiEntity.setShebeiPhoto("");//详情和图片
//                            shebeiEntity.setShengchanchangjia(data.get(0));                    //生产厂家 要改的
//                            shebeiEntity.setShebeiTypes(Integer.valueOf(data.get(0)));   //设备类型 要改的
//                            shebeiEntity.setShebeiGongxiaoContent("");//详情和图片
//                            shebeiEntity.setShebeiJinjiContent("");//详情和图片
//                            shebeiEntity.setShebeiZhuyiContent("");//详情和图片
//                            shebeiEntity.setShebeiContent("");//详情和图片
//                            shebeiEntity.setInsertTime(date);//时间
//                            shebeiEntity.setCreateTime(date);//时间
                            shebeiList.add(shebeiEntity);


                            //把要查询是否重复的字段放入map中
                                //设备编号
                                if(seachFields.containsKey("shebeiUuidNumber")){
                                    List<String> shebeiUuidNumber = seachFields.get("shebeiUuidNumber");
                                    shebeiUuidNumber.add(data.get(0));//要改的
                                }else{
                                    List<String> shebeiUuidNumber = new ArrayList<>();
                                    shebeiUuidNumber.add(data.get(0));//要改的
                                    seachFields.put("shebeiUuidNumber",shebeiUuidNumber);
                                }
                        }

                        //查询是否重复
                         //设备编号
                        List<ShebeiEntity> shebeiEntities_shebeiUuidNumber = shebeiService.selectList(new EntityWrapper<ShebeiEntity>().in("shebei_uuid_number", seachFields.get("shebeiUuidNumber")));
                        if(shebeiEntities_shebeiUuidNumber.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(ShebeiEntity s:shebeiEntities_shebeiUuidNumber){
                                repeatFields.add(s.getShebeiUuidNumber());
                            }
                            return R.error(511,"数据库的该表中的 [设备编号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                        shebeiService.insertBatch(shebeiList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }




    /**
    * 前端列表
    */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        CommonUtil.checkMap(params);
        PageUtils page = shebeiService.queryPage(params);

        //字典表数据转换
        List<ShebeiView> list =(List<ShebeiView>)page.getList();
        for(ShebeiView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段

        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        ShebeiEntity shebei = shebeiService.selectById(id);
            if(shebei !=null){


                //entity转view
                ShebeiView view = new ShebeiView();
                BeanUtils.copyProperties( shebei , view );//把实体数据重构到view中

                //修改对应字典表字段
                dictionaryService.dictionaryConvert(view, request);
                return R.ok().put("data", view);
            }else {
                return R.error(511,"查不到数据");
            }
    }


    /**
    * 前端保存
    */
    @RequestMapping("/add")
    public R add(@RequestBody ShebeiEntity shebei, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,shebei:{}",this.getClass().getName(),shebei.toString());
        Wrapper<ShebeiEntity> queryWrapper = new EntityWrapper<ShebeiEntity>()
            .eq("shebei_name", shebei.getShebeiName())
            .eq("shebei_uuid_number", shebei.getShebeiUuidNumber())
            .eq("shengchanchangjia", shebei.getShengchanchangjia())
            .eq("shebei_types", shebei.getShebeiTypes())
//            .notIn("shebei_types", new Integer[]{102})
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ShebeiEntity shebeiEntity = shebeiService.selectOne(queryWrapper);
        if(shebeiEntity==null){
            shebei.setInsertTime(new Date());
            shebei.setCreateTime(new Date());
        shebeiService.insert(shebei);

            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

}

