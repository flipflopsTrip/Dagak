import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('@/views/HomeView.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/LoginView.vue')
    },
    {
      path: '/regist',
      name: 'regist',
      component: () => import('@/components/user/UserRegist.vue')
    },
    {
      path: '/findid',
      name: 'findId',
      component: () => import('@/components/user/UserFindId.vue')
    },
    {
      path: '/findpw',
      name: 'findPw',
      component: () => import('@/components/user/UserFindPw.vue')
    },
    {
      path: '/store',
      name: 'store',
      component: () => import('@/views/StoreView.vue')
    },
    {
      path: '/mypage',
      name: 'myPage',
      component: () => import('@/views/MyPageView.vue'),
      children: [
        {
          path: '',
          name: 'myPageSchedule',
          component: () => import('@/components/mypage/MyPageSchedule.vue')
        },
        {
          path: 'friend',
          name: 'myPageFriend',
          component: () => import('@/components/mypage/MyPageFriend.vue')
        },
        {
          path: 'information',
          name: 'myPageInformation',
          component: () => import('@/components/mypage/MyPageInformation.vue')
        },
        {
          path: 'qna',
          name: 'myPageQnA',
          component: () => import('@/components/mypage/MyPageQnA.vue')
        },
        {
          path: 'alarm',
          name: 'myPageAlarm',
          component: () => import('@/components/mypage/MyPageAlarm.vue')
        },
        {
          path: 'inventory',
          name: 'myPageInventory',
          component: () => import('@/components/mypage/MyPageInventory.vue')
        }
      ]
    },
    {
      path: '/studyroom',
      name: 'studyroom',
      component: () => import('@/views/StudyRoomView.vue')
    },
    {
      // :모꼬지pk 나중에 넣자
      path: '/mokkoji/:id',
      name: 'mokkoji',
      component: () => import('@/views/MokkojiView.vue')
    },
    {
      path: '/apply',
      name: 'apply',
      component: () => import('@/views/ApplyView.vue')
    },
    {
      path: '/posts/list',
      name: 'PostList',
      component: () => import('@/views/PostListView.vue')
    },
    {
      path: '/posts/create',
      name: 'PostCreate',
      component: () => import('@/views/PostCreateView.vue')
    },
    {
      path: '/posts/:id',
      name: 'PostDetail',
      component: () => import('@/views/PostDetailView.vue')
    },
    {
      path: '/posts/:id/edit',
      name: 'PostEdit',
      component: () => import('@/views/PostEditView.vue')
    }
  ]
})

export default router
