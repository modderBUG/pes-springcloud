package me.xiaowei.modules.system.service.mapper;

import me.xiaowei.base.BaseMapper;
import me.xiaowei.modules.system.domain.Job;
import me.xiaowei.modules.system.service.dto.JobSmallDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author Zheng Jie
* @date 2019-03-29
*/
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobSmallMapper extends BaseMapper<JobSmallDTO, Job> {}