import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    userInfo: {
      username: '',
      phone: '',
      identity: '',
      token: '',
    }
  }),
  actions: {
    saveUserInfo(userInfo) {
      this.userInfo = userInfo
    }
  }
})