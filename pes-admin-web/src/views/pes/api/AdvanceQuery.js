import request from '@/utils/request'



//根据周次查实验
export function getExperimentByWeek(week) {
  return request({
    url: '/pes/api/getExperimentThisWeek/'+week,
    method: 'get'
  })
}



//查询全部实验对应时间：
export function findAllExperimentTime() {
  return request({
    url: '/pes/api/findAllExperimentTime',
    method: 'get'
  })
}

//根据周次查询实验时间：
export function getExperimentByTimeTimes(timeTimes) {
  return request({
    url: '/pes/api/TimeTable?times='+timeTimes,
    method: 'get'
  })
}
//根据周次查询实验名称：
export function findExperimentByTimeTimes(timeTimes) {
  return request({
    url: '/pes/api/ExperimentAndTime?timeTimes='+timeTimes,
    method: 'get'
  })
}

//根据实验时间查询所有同学实验成绩：
export function getGradeByExpTime(data) {
  return request({
    url: '/pes/api/getGradeByTimesAndExpId/',
    method: 'post',
    data
  })
}

//根据实验名称和时间查询所有同学实验成绩：
export function getGradeByDto(data) {
  return request({
    url: '/pes/api/GradeInfoDTO/',
    method: 'post',
    data
  })
}

//修改学生成绩：这个操作比较复杂
export function editGradeByDto(data) {
  return request({
    url: '/pes/api/GradeInfoDTO/',
    method: 'put',
    data
  })
}


//根据实验查成绩
export function getGradeByExp(expId) {
  return request({
    url: '/pes/api/getGradeByExperiment/'+expId,
    method: 'get'
  })
}

// export function add(data) {
//   return request({
//     url: '/pes/api/getTermInfo',
//     method: 'post',
//     data
//   })
// }
//
// export function del(id) {
//   return request({
//     url: '/pes/api/getTermInfo',
//     method: 'delete'
//   })
// }
//
// export function edit(data) {
//   return request({
//     url: '/pes/api/setTermInfo',
//     method: 'put',
//     data
//   })
// }
//
