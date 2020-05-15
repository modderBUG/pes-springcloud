package me.xiaowei.modules.pes.service.mapper;

import me.xiaowei.modules.pes.domain.*;
import me.xiaowei.modules.pes.service.dto.ExperimentInfoDto;
import me.xiaowei.modules.pes.service.dto.GradeInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
//@Mapper
public interface GradeMapper {
    //public static GoodInfoMapper MAPPER = Mappers.getMapper(GoodInfoMapper.class);

    @Mappings({
            @Mapping(source = "stu.stuName", target = "stuName"),
            @Mapping(source = "stu.stuClass", target = "stuClass"),
            @Mapping(source = "grade.deskId", target = "deskId"),
            @Mapping(source = "grade.stuNum", target = "stuNum"),
            @Mapping(source = "grade.attendance", target = "attendance"),
            @Mapping(source = "grade.testFlag", target = "testFlag"),
            @Mapping(source = "grade.score", target = "score"),
    })
    public GradeInfoDto from(T_student stu, T_grade grade);
}
