package com.tiyujia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.tiyujia.mapper.CityMapper;
import com.tiyujia.model.City;
/**
 * 
 * @title CityService.java
 * @package com.tiyujia.service
 * @description 此处以后换成dubbo接口
 * @author ZhangHuaRong   
 * @update 2016年5月17日 下午3:30:46
 * @version V1.0  
 * Copyright (c)2012 chantsoft-版权所有
 */
@Service
public class CityService {

    @Autowired
    private CityMapper cityMapper;

    public List<City> getAll(City city) {
        if (city.getPage() != null && city.getRows() != null) {
            PageHelper.startPage(city.getPage(), city.getRows(), "id");
        }
        return cityMapper.selectAll();
    }

    public City getById(Integer id) {
        return cityMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        cityMapper.deleteByPrimaryKey(id);
    }

    public void save(City country) {
        if (country.getId() != null) {
            cityMapper.updateByPrimaryKey(country);
        } else {
            cityMapper.insert(country);
        }
    }
}
