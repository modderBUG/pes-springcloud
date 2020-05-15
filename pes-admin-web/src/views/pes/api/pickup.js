import request from '@/utils/request'

//选课逻辑
export function pickUpCourse(args) {
  return request({
    url: '/pes/api/course?'+args,
    method: 'get'
  })
}

//获取课程表
export function getSchedule() {
  return request({
    url: '/pes/api/course/schedule',
    method: 'get'
  })
}

//获取可选课程和时间
export function geAllowSchedule() {
  return request({
    url: '/pes/api/TimeTable?stuChannel=1',
    method: 'get'
  })
}

//获取学生课程和时间
export function getStuSchedule() {
  return request({
    url: '/pes/api/TimeTable?stuChannel=1&returnChannel=True',
    method: 'get'
  })
}

//退课逻辑
// export function returnCourse(expId) {
//   return request({
//     url: '/pes/api/course?expId='+expId,
//     method: 'delete'
//   })
// }

//退课逻辑
export function returnCourse(args) {
  return request({
    url: '/pes/api/course?'+args,
    method: 'delete'
  })
}
