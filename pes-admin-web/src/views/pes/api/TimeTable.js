import request from '@/utils/request'

export function getAllExprimentTime() {
  return request({
    url: '/pes/api/TimeTable',
    method: 'get'
  })
}



//
export function getTimeById(id) {
  return request({
    url: '/pes/api/getTimeById/'+id,
    method: 'get'
  })
}
//
export function getTimeByExpId(expId) {
  return request({
    url: '/pes/api/TimeTable?id='+expId,
    method: 'get'
  })
}
//
export function setTimeItem(data) {
  return request({
    url: '/pes/api/TimeTable',
    method: 'post',
    data
  })
}

export function setTimeList(data) {
  return request({
    url: '/pes/api/TimeTable/list',
    method: 'post',
    data
  })
}

//删除排课
export function delExprimentTime(expId) {
  return request({
    url: '/pes/api/TimeTable?expId='+expId,
    method: 'delete'
  })
}
//
// export function del(id) {
//   return request({
//     url: '/pes/api/getAllExperiment',
//     method: 'delete'
//   })
// }
//
// export function edit(data) {
//   return request({
//     url: '/pes/api/setExperiment',
//     method: 'post',
//     data
//   })
// }
//
