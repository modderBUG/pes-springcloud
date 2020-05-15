package me.xiaowei.modules.pes.service.mapper;

import me.xiaowei.modules.pes.domain.T_experiment;
import me.xiaowei.modules.pes.domain.T_time;
import me.xiaowei.modules.pes.service.dto.ExperimentInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * 映射，实验名称   周次  第几周     上午下午
 * **/

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
//@Mapper
public interface ExperimentMapper {
    //public static GoodInfoMapper MAPPER = Mappers.getMapper(GoodInfoMapper.class);

    @Mappings({
            @Mapping(source = "exp.expName", target = "expName"),
            @Mapping(source = "time.timeTimes", target = "timeTimes"),
            @Mapping(source = "time.timeWeek", target = "timeWeek"),
            @Mapping(source = "time.timeSchedule", target = "timeSchedule")
    })
    public ExperimentInfoDto from(T_experiment exp, T_time time);
}


