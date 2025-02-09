import '@/assets/reset.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

// 引入组件样式 防止样式丢失
import 'element-plus/dist/index.css'

const app = createApp(App)

app.use(router)

app.mount('#app')
