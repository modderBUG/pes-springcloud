import request from '@/utils/request'

export function getAll(args) {
  return request({
    url: '/pes/api/daily'+args,
    method: 'get'
  })
}

export function delOne(id) {
  return request({
    url: '/pes/api/daily/'+id,
    method: 'delete'
  })
}

export function addOne(data) {
  return request({
    url: '/pes/api/daily',
    method: 'post',
    data
  })
}
