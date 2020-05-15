import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '../layout/Layout'

/**
 * hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
 *                                if not set alwaysShow, only more than one route under the children
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
 **/

export const constantRouterMap = [
  {
    path: '/login',
    meta: {title: '登录', noCache: true},
    component: () => import('@/views/login'),
    hidden: true
  },
  {
    path: '/pesindex',
    meta: {title: '主页', noCache: true},
    component: () => import('@/views/pes/views/main/pesindex'),
    hidden: true
  },
  {
    path: '/waterfall',
    meta: {title: '阿潘瀑布流', noCache: true},
    component: () => import('@/views/pes/views/components/WaterfallFlow2'),
    hidden: true
  },
  {
    path: '/waterfall3',
    meta: {title: '瀑布流', noCache: true},
    component: () => import('@/views/pes/views/components/WaterfallFlowTest'),
    hidden: true
  },


  {
    path: '/commons',
    component: Layout,
    redirect: '/commons/commonsCourse',
    name: 'excel',
    meta: {
      title: '查询',
      icon: 'menu'
    },
    children: [
      {
        path: 'commonsCourse',
        component: () => import('@/views/pes/views/commons/commonsCourse.vue'),
        name: 'exportExcel',
        meta: {title: '学生成绩管理', icon: 'tools'}
      },
      {
        path: 'teacherSchedule',
        // component: () => import('@/views/pes/views/commons/commonsCourse.vue'),
        name: 'teacherSchedule',
        meta: {title: '教师课表', icon: 'date'}
      },
      {
        path: 'TeacherList',
        component: () => import('@/views/pes/views/tools/components/TeacherList.vue'),
        name: 'TeacherList',
        meta: {title: '教师抢课', icon: 'date'}
      },

    ]
  },
  {
    path: '/tools',
    component: Layout,
    redirect: '/tools/arrange',
    name: 'excel',
    meta: {
      title: '排课',
      icon: 'system'
    },
    children: [
      {
        path: 'arrange',
        component: () => import('@/views/pes/views/tools/arrange.vue'),
        name: 'arrange',
        meta: {title: '排课', icon: 'timing'}
      },
      {
        path: 'freearrange',
        component: () => import('@/views/pes/views/tools/freearrange.vue'),
        name: 'freearrange',
        meta: {title: '自选排课', icon: 'timing'}
      },
      {
        path: 'initsetting',
        component: () => import('@/views/pes/views/setting/initsetting.vue'),
        name: 'selectExcel',
        meta: {title: '设置', icon: 'system'}
      },
      {
        path: 'allexperment',
        component: () => import('@/views/pes/views/experment/AllExperment.vue'),
        name: 'uploadExcel',
        meta: {title: '查看全部实验', icon: 'search'}
      },
    ]
  },
  {
    path: '/stuindex',
    component: Layout,
    redirect: '/stuindex/pickup',
    name: 'stuindex',
    meta: {
      title: '选课',
      icon: 'dept'
    },
    children: [
      {
        path: 'pickup',
        component: () => import('@/views/pes/views/pickup/pickup.vue'),

        name: 'pickup',
        meta: {title: '学生选课', icon: 'search'}
      },
      {
        path: 'picked',
        component: () => import('@/views/pes/views/pickup/picked.vue'),
        name: 'picked',
        meta: {title: '已选课程', icon: 'log'}
      }
      ,
      {
        path: 'stuSchedule',
        component: () => import('@/views/pes/views/pickup/picked.vue'),
        name: 'stuSchedule',
        meta: {title: '学生课表', icon: 'date'}
      }
    ]
  },

  {
    path: '/message',
    component: Layout,
    redirect: '/stuindex/chatroom',
    name: 'chatroom',
    meta: {
      title: '消息发送',
      icon: 'email'
    },
    children: [

      {
        path: 'push',
        component: () => import('@/views/pes/views/websocket/MessagePush.vue'),
        name: 'chatroom',
        meta: {title: '消息推送', icon: 'gonggao'}
      },
      {
        path: 'chatroom',
        component: () => import('@/views/pes/views/websocket/Chatroom.vue'),
        name: 'chatroom',
        meta: {title: '聊天室1', icon: 'gonggao'}
      },
      {
        path: 'chatroom2',
        component: () => import('@/views/pes/views/websocket/Chatroom2.vue'),
        name: 'chatroom2',
        meta: {title: '聊天室2', icon: 'gonggao'}
      }

    ]
  },

  {
    path: '/404',
    component: () => import('@/views/features/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/features/401'),
    hidden: true
  },
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: () => import('@/views/features/redirect')
      }
    ]
  },
  {
    path: '/',
    component: Layout,
    redirect: 'dashboard',

    children: [

      {
        path: 'dashboard',
        component: () => import('@/views/home'),
        name: '首页',
        meta: {title: '首页', icon: 'index', noCache: true, affix: true}
      }
    ]
  },

  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'center',
        component: () => import('@/views/system/user/center'),
        name: '个人中心',
        meta: {title: '个人中心', icon: 'user'}
      }
    ]
  }
  // { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  mode: 'history',
  scrollBehavior: () => ({y: 0}),
  routes: constantRouterMap
})
