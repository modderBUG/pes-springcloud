package me.xiaowei.modules.pes.service.mapper;

import me.xiaowei.modules.pes.domain.T_experiment;
import me.xiaowei.modules.pes.domain.T_teacher;
import me.xiaowei.modules.pes.domain.T_time;
import me.xiaowei.modules.pes.service.dto.ClassScheduleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
 * 映射，实验名称 实验id 实验教室 实验时间 实验周次 实验星期 实验时段 教师id
 * **/

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
//@Mapper
public interface ClassScheduleMapper {
    //public static GoodInfoMapper MAPPER = Mappers.getMapper(GoodInfoMapper.class);

    @Mappings({
            @Mapping(source = "exp.expName", target = "expName"),
            @Mapping(source = "exp.expId", target = "expId"),
            @Mapping(source = "exp.expLab", target = "expLab"),
            @Mapping(source = "time.timeTimes", target = "timeTimes"),
            @Mapping(source = "time.timeWeek", target = "timeWeek"),
            @Mapping(source = "time.expTime", target = "expTime"),
            @Mapping(source = "time.timeSchedule", target = "timeSchedule"),
            @Mapping(source = "time.teacherId", target = "teacherName"),
    })
    public ClassScheduleDTO from(T_experiment exp, T_time time);
}


