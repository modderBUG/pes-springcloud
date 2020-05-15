package me.xiaowei.modules.pes.service.mapper;

import me.xiaowei.modules.pes.domain.T_experiment;
import me.xiaowei.modules.pes.domain.T_freetime;
import me.xiaowei.modules.pes.domain.T_grade;
import me.xiaowei.modules.pes.domain.T_student;
import me.xiaowei.modules.pes.service.dto.FreetimeDTO;
import me.xiaowei.modules.pes.service.dto.GradeInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
//@Mapper
public interface FreetimeMapper {
    //public static GoodInfoMapper MAPPER = Mappers.getMapper(GoodInfoMapper.class);

    @Mappings({
            @Mapping(source = "ftm.id", target = "id"),
            @Mapping(source = "ftm.expId", target = "expId"),
            @Mapping(source = "exp.expName", target = "expName"),
            @Mapping(source = "ftm.timeTimes", target = "timeTimes"),
            @Mapping(source = "ftm.timeWeek", target = "timeWeek"),
            @Mapping(source = "ftm.timeSchedule", target = "timeSchedule"),
            @Mapping(source = "ftm.teacherId", target = "teacherId"),
    })
    public FreetimeDTO from(T_experiment exp, T_freetime ftm);
}