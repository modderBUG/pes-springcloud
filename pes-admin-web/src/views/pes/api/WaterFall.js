import request from '@/utils/request'

export function getAllPic() {
  return request({
    url: '/pes/api/getAllPic',
    method: 'get'
  })
}

export function getPictureThum(index) {
  return request({
    url: '/pes/api/getPictureThum/'+index,
    method: 'get'
  })
}

export function getAllPic2(index) {
  return request({
    url: '/pes/api/getAllPic2/'+index,
    method: 'get'
  })
}

export function getPicturesByIndex(index) {
  return request({
    url: '/pes/api/getPicturesByIndex/'+index,
    method: 'get'
  })
}

// //
// export function getTimeById(id) {
//   return request({
//     url: '/pes/api/getTimeById/'+id,
//     method: 'get'
//   })
// }
// //
// export function getTimeByExpId(expId) {
//   return request({
//     url: '/pes/api/getTimeByExpId/'+expId,
//     method: 'get'
//   })
// }
// //
// export function setTimeItem(data) {
//   return request({
//     url: '/pes/api/setTimeItem',
//     method: 'post',
//     data
//   })
// }
//
// export function setTimeList(data) {
//   return request({
//     url: '/pes/api/setTimeList',
//     method: 'post',
//     data
//   })
// }
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
