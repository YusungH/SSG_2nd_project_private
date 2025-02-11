package com.exam.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.exam.dto.RefrigeratorDTO;

@Mapper
public interface RefrigeratorMapper {

	public int refrigeratorAdd(RefrigeratorDTO dto);
	public List<RefrigeratorDTO> refrigeratorList(String userid);
	public int refrigeratorDelete(int num);
	public int updateRefrigeratorStock(RefrigeratorDTO dto);
	public int refrigeratorDeleteAll(List<String> list);
}
