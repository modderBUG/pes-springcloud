package me.xiaowei.modules.system.service.mapper;

import me.xiaowei.base.BaseMapper;
import me.xiaowei.modules.system.domain.Dept;
import me.xiaowei.modules.system.service.dto.DeptSmallDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeptSmallMapper extends BaseMapper<DeptSmallDTO, Dept> {

}