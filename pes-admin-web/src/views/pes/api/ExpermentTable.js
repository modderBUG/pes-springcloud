import request from '@/utils/request'

export function getAll() {
  return request({
    url: '/pes/api/Experiments',
    method: 'get'
  })
}

export function getOne(id) {
  return request({
    url: '/pes/api/Experiments/'+id,
    method: 'get'
  })
}

export function getOneById(id) {
  return request({
    url: '/pes/api/Experiments?expId='+id,
    method: 'get'
  })
}

export function add(data) {
  return request({
    url: '/pes/api/Experiments',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: '/pes/api/Experiments',
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: '/pes/api/Experiments/setExperiment',
    method: 'post',
    data
  })
}

