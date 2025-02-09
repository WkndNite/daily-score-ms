import { createRouter, createWebHashHistory } from 'vue-router'
import DashboardPage from '@/views/DashboardPage.vue'

const routes = [
  {
    path: '/',
    component: () => import('../layouts/MainLayout.vue'),
    children: [
      {
        path: '',
        redirect: 'dashboard',
      },
      {
        path: 'dashboard',
        name: 'dashboard',
        component: DashboardPage,
        meta: {
          title: '工作台',
          icon: 'HomeFilled',
        },
      },
      {
        path: 'score-definition',
        name: 'ScoreDefinition',
        component: () => import('../views/ScoreDefinition.vue'),
        meta: {
          title: '平时分定义',
          icon: 'Setting',
        },
      },
      {
        path: 'score-entry',
        name: 'ScoreEntry',
        component: () => import('../views/ScoreEntry.vue'),
        meta: {
          title: '平时分录入',
          icon: 'EditPen',
        },
      },
      {
        path: 'score-analysis',
        name: 'ScoreAnalysis',
        component: () => import('../views/ScoreAnalysis.vue'),
        meta: {
          title: '成绩分析',
          icon: 'DataLine',
        },
      },
    ],
  },
  {
    path: '/login',
    name: 'LoginAndSign',
    component: () => import('../views/LoginPage.vue'),
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/dashboard',
  },
]

const router = createRouter({
	history: createWebHashHistory(),
	routes,
})

// router.beforeEach((to, from, next) => {
//   // 获取用户信息
// 应该是从仓库获取 开发过程可以模拟本地存储
//   const userInfo = localStorage.getItem('userInfo')
//   if (userInfo) {
//     next()
//   } else {
//     if (to.path === '/login') {
//       next()
//     } else {
//       next('/login')
//     }
//   }
// })

export default router
