package me.xiaowei.modules.system.service.mapper;

import me.xiaowei.base.BaseMapper;
import me.xiaowei.modules.system.domain.Dict;
import me.xiaowei.modules.system.service.dto.DictDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictMapper extends BaseMapper<DictDTO, Dict> {

}