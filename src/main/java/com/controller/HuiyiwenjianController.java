package com.controller;


import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.StringUtil;
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

import com.entity.HuiyiwenjianEntity;

import com.service.HuiyiwenjianService;
import com.entity.view.HuiyiwenjianView;
import com.service.HuiyiService;
import com.entity.HuiyiEntity;
import com.utils.PageUtils;
import com.utils.R;

/**
 * 会议文件管理
 * 后端接口
 * @author
 * @email
 * @date
*/
@RestController
@Controller
@RequestMapping("/huiyiwenjian")
public class HuiyiwenjianController {
    private static final Logger logger = LoggerFactory.getLogger(HuiyiwenjianController.class);

    @Autowired
    private HuiyiwenjianService huiyiwenjianService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;


    //级联表service
    @Autowired
    private HuiyiService huiyiService;


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(StringUtil.isNotEmpty(role) && "用户".equals(role)){
//            params.put("yonghuId",request.getSession().getAttribute("userId"));
//        }
        PageUtils page = huiyiwenjianService.queryPage(params);

        //字典表数据转换
        List<HuiyiwenjianView> list =(List<HuiyiwenjianView>)page.getList();
        for(HuiyiwenjianView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c);
        }
        return R.ok().put("data", page);
    }
    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        HuiyiwenjianEntity huiyiwenjian = huiyiwenjianService.selectById(id);
        if(huiyiwenjian !=null){
            //entity转view
            HuiyiwenjianView view = new HuiyiwenjianView();
            BeanUtils.copyProperties( huiyiwenjian , view );//把实体数据重构到view中

            //级联表
            HuiyiEntity huiyi = huiyiService.selectById(huiyiwenjian.getHuiyiId());
            if(huiyi != null){
                BeanUtils.copyProperties( huiyi , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                view.setHuiyiId(huiyi.getId());
            }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody HuiyiwenjianEntity huiyiwenjian, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,huiyiwenjian:{}",this.getClass().getName(),huiyiwenjian.toString());
        Wrapper<HuiyiwenjianEntity> queryWrapper = new EntityWrapper<HuiyiwenjianEntity>()
            .eq("huiyi_id", huiyiwenjian.getHuiyiId())
            .eq("huiyiwenjian_name", huiyiwenjian.getHuiyiwenjianName())
            .eq("huiyiwenjian_content", huiyiwenjian.getHuiyiwenjianContent())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        HuiyiwenjianEntity huiyiwenjianEntity = huiyiwenjianService.selectOne(queryWrapper);
        if(huiyiwenjianEntity==null){
            huiyiwenjian.setInsertTime(new Date());
            huiyiwenjian.setCreateTime(new Date());
        //  String role = String.valueOf(request.getSession().getAttribute("role"));
        //  if("".equals(role)){
        //      huiyiwenjian.set
        //  }
            huiyiwenjianService.insert(huiyiwenjian);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody HuiyiwenjianEntity huiyiwenjian, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,huiyiwenjian:{}",this.getClass().getName(),huiyiwenjian.toString());
        //根据字段查询是否有相同数据
        Wrapper<HuiyiwenjianEntity> queryWrapper = new EntityWrapper<HuiyiwenjianEntity>()
            .notIn("id",huiyiwenjian.getId())
            .eq("huiyi_id", huiyiwenjian.getHuiyiId())
            .eq("huiyiwenjian_name", huiyiwenjian.getHuiyiwenjianName())
            .eq("huiyiwenjian_content", huiyiwenjian.getHuiyiwenjianContent())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        HuiyiwenjianEntity huiyiwenjianEntity = huiyiwenjianService.selectOne(queryWrapper);
        if("".equals(huiyiwenjian.getHuiyiwenjianFile()) || "null".equals(huiyiwenjian.getHuiyiwenjianFile())){
                huiyiwenjian.setHuiyiwenjianFile(null);
        }
        if(huiyiwenjianEntity==null){
            //  String role = String.valueOf(request.getSession().getAttribute("role"));
            //  if("".equals(role)){
            //      huiyiwenjian.set
            //  }
            huiyiwenjianService.updateById(huiyiwenjian);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }


    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        huiyiwenjianService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }


}

