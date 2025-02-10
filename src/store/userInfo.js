import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    userInfo: {
      username: '',
      phone: '',
      identity: '',
      token: '',
    },
    loginTime: 0,
  }),
  actions: {
    setUserInfo(userInfo) {
      this.userInfo = userInfo
    },
    setLoginTime(loginTime) {
      this.loginTime = loginTime 
    }
  }
})