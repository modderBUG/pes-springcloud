package me.xiaowei.modules.system.service.mapper;

import me.xiaowei.base.BaseMapper;
import me.xiaowei.modules.system.domain.Menu;
import me.xiaowei.modules.system.service.dto.MenuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Zheng Jie
 * @date 2018-12-17
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper extends BaseMapper<MenuDTO, Menu> {

}