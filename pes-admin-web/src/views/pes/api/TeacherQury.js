import request from '@/utils/request'

export function getAllTeachers() {
  return request({
    url: '/pes/api/Teachers',
    method: 'get'
  })
}

//
// export function getOne(id) {
//   return request({
//     url: '/pes/api/getExperiment/'+id,
//     method: 'get'
//   })
// }
//
// export function getOneById(id) {
//   return request({
//     url: '/pes/api/getExperimentById/'+id,
//     method: 'get'
//   })
// }
//
// export function add(data) {
//   return request({
//     url: '/pes/api/getAllExperiment',
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
