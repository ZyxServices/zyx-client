package com.tiyujia.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.tiyujia.model.Player;

public interface PlayMapper {
	@Select("SELECT * FROM play WHERE id = #{id}")
	Player findByState(@Param("id") String id);

}
