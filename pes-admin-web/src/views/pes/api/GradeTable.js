import request from '@/utils/request'

export function getAll() {
  return request({
    url: '/pes/api/GradeInfoDTO',
    method: 'get'
  })
}

export function getOne(id) {
  return request({
    url: '/pes/api/getGrade/'+id,
    method: 'get'
  })
}
//expId
export function getOneById(id) {
  return request({
    url: '/pes/api/TimeTable/'+id,
    method: 'get'
  })
}

export function add(data) {
  return request({
    url: '/pes/api/setGrade',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: '/pes/api/delGrade/'+id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: '/pes/api/setGrade',
    method: 'put',
    data
  })
}

