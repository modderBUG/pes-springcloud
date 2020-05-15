import request from '@/utils/request'

export function getAll() {
  return request({
    url: '/pes/api/comments',
    method: 'get'
  })
}

export function pageableGetAll(pageNum,pageSize,keywords,stuNum) {
  var args='?';
  if (pageNum!=''&&pageNum!=undefined&&pageNum!=null){
    args+='pageNum='+pageNum-1
  }
  if (pageSize!=''&&pageSize!=undefined&&pageSize!=null){
    args+='&pageSize='+pageSize
  }
  if (keywords!=''&&keywords!=undefined&&keywords!=null){
    args+='&keywords='+keywords
  }
  if (stuNum!=''&&stuNum!=undefined&&stuNum!=null){
    args+='&stuNum='+stuNum
  }
  console.log(args)
  args.replace("?&","?")
  return request({
    url: '/pes/api/comments'+args,
    method: 'get'
  })
}

export function pageableGetAllData(pageNum) {

  return request({
    url: '/pes/api/comments?pageNum='+(pageNum-1),
    method: 'get'
  })
}

export function addOne(data) {
  return request({
    url: '/pes/api/comments',
    method: 'post',
    data
  })
}



export function delOne(id) {
  return request({
    url: '/pes/api/comments/'+id,
    method: 'delete'
  })
}

export function editOne(data) {
  return request({
    url: '/pes/api/comments',
    method: 'post',
    data
  })
}

export function addOneReply(data) {
  return request({
    url: '/pes/api/reply',
    method: 'post',
    data
  })
}

export function delOneReply(id) {
  return request({
    url: '/pes/api/reply/'+id,
    method: 'delete'
  })
}
