import request from '@/utils/request'

export function pickUp(id) {
  return request({
    url: '/pes/api/teachertime/'+id,
    method: 'get'
  })
}

export function get(args) {
  if (args==null) args='' ;
  console.log('/pes/api/teachertime'+args)
  return request({
    url: '/pes/api/teachertime'+args,
    method: 'get'
  })
}

export function getByTeacher(args) {
  if (args==null) args='' ;
  console.log('/pes/api/teachertime'+args)
  return request({
    url: '/pes/api/teachertime'+args,
    method: 'get'
  })
}

export function addOne(data) {
  return request({
    url: '/pes/api/teachertime',
    method: 'post',
    data
  })
}

export function delOne(id) {
  return request({
    url: '/pes/api/teachertime/'+id,
    method: 'delete'
  })
}

