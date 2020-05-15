import request from '@/utils/request'

export function getTermInfo() {
  return request({
    url: '/pes/api/getTermInfo',
    method: 'get'
  })
}

export function add(data) {
  return request({
    url: '/pes/api/getTermInfo',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: '/pes/api/getTermInfo',
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: '/pes/api/setTermInfo',
    method: 'put',
    data
  })
}

