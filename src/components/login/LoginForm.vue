<template>
	<div class="login-card">
		<header>
			<img src="@/assets/logo.jpg" alt="Logo" class="logo" />
			<h1 class="title">HIT Daily-Score</h1>
			<h2 class="subtitle">
				{{ type[model].formTitle }}
			</h2>
		</header>

		<section class="form-container">
			<div class="form-content" key="form-content">
				<el-form :model="form" :rules="rules" ref="refForm" status-icon>
					<el-form-item prop="username" v-if="showNameInput">
						<el-input v-model="form.username" placeholder="请输入用户名">
							<template #prefix>
								<el-icon><User /></el-icon>
							</template>
						</el-input>
					</el-form-item>

					<el-form-item prop="phone" v-if="showPhoneInput">
						<el-input v-model.number="form.phone" placeholder="请输入手机号">
							<template #prefix>
								<el-icon><Phone /></el-icon>
							</template>
						</el-input>
					</el-form-item>

					<el-form-item prop="password" v-if="showPwdInput">
						<el-input
							v-model="form.password"
							type="password"
							placeholder="请输入密码"
							show-password
						>
							<template #prefix>
								<el-icon><Lock /></el-icon>
							</template>
						</el-input>
					</el-form-item>

					<el-form-item prop="confirmPassword" v-if="showConfirmPwdInput">
						<el-input
							v-model="form.confirmPassword"
							type="password"
							placeholder="请确认密码"
							show-password
						>
							<template #prefix>
								<el-icon><Lock /> </el-icon>
							</template>
						</el-input>
					</el-form-item>

					<el-form-item prop="identity" v-if="showIdentityInput">
						<el-select
							v-model="form.identity"
							placeholder="您的身份"
							@visible-change="handleChange"
						>
							<el-option label="老师" value="2" />
							<el-option label="学生" value="3" />
						</el-select>
					</el-form-item>

					<el-form-item class="code" prop="code" v-if="showCodeInput">
						<el-input v-model="form.code" placeholder="请输入短信验证码">
							<template #prefix>
								<el-icon><ChatDotSquare /></el-icon>
							</template>
						</el-input>
						<el-button
							class="send-msg-btn"
							type="primary"
							@click="sendMsg(form.phone)"
							:disabled="!sendCodeAccess"
						>
							{{ sendCodeAccess ? '发送验证码' : `${sendCodeTimer}s后重新发送` }}
						</el-button>
					</el-form-item>

					<div class="form-options" v-if="model === 'accountLogin'">
						<el-checkbox v-model="rememberMe" id="remember-me">记住我</el-checkbox>
						<el-link type="danger" @click="switchForm('forgotPassword')"
							>忘记密码？</el-link
						>
					</div>

					<el-form-item class="submit">
						<el-button type="primary" class="submit-btn" @click="submit">
							{{ type[model].formTitle }}
						</el-button>
					</el-form-item>
				</el-form>
			</div>
		</section>

		<footer>
			<div class="back-to-login" v-if="model !== 'accountLogin' && model !== 'codeLogin'">
				<el-link type="primary" @click="switchForm('accountLogin')"> 返回登录 </el-link>
			</div>
			<div class="register-link" v-else>
				<el-link type="primary" @click="switchForm('register')"> 去注册 </el-link>
			</div>
			<div class="message-login" v-if="model === 'accountLogin'">
				<el-divider direction="horizontal" content-position="center">或者</el-divider>
				<el-button circle @click="switchForm('codeLogin')" title="短信登录">
					<el-icon><ChatDotSquare /></el-icon>
				</el-button>
			</div>
		</footer>
	</div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { ChatDotSquare, User, Lock, Phone } from '@element-plus/icons-vue'
import { sendCode } from '@/api/sendCode'
import { register } from '@/api/register'
import { loginAccount } from '@/api/login'
import { loginPhone } from '@/api/loginPhone'
import { useUserStore } from '@/store/userInfo'

const model = defineModel('type')

let sendCodeAccess = ref(true)
let sendCodeTimer = ref(60)

// ① showNameInput ② showPhoneInput ③ showPwdInput
// ④ ShowConfirmPwdInput ⑤ showIdentityInput ⑥ showCodeInput & showSendBtn
// 用位元旗标来表示以上六个属性是否在表单出现
const BIT_FLAGS = {
	USER_NAME: 0b100000,
	PHONE: 0b010000,
	PASSWORD: 0b001000,
	CONFIRM_PASSWORD: 0b000100,
	IDENTITY: 0b000010,
	CODE: 0b000001,
}
const type = {
	accountLogin: {
		formTitle: '登录',
		flag: 0b101000,
		switchTitle: '还没有账号？去注册',
		action: '/loginAccount',
	},
	register: {
		formTitle: '注册',
		flag: 0b111111,
		switchTitle: '返回登录',
		action: '/register',
	},
	codeLogin: {
		formTitle: '短信登录',
		flag: 0b010001,
		switchTitle: '还没有账号？去注册',
		action: '/loginPhone',
	},
	// TODO: 重置密码接口联调
	forgotPassword: {
		formTitle: '重置密码',
		flag: 0b011101,
		switchTitle: '返回登录',
	},
}

const createVisibilityChecker = (flagName) => {
	return computed(() => {
		const currentType = type[model.value]
		return (currentType?.flag & BIT_FLAGS[flagName]) !== 0
	})
}

const showNameInput = createVisibilityChecker('USER_NAME')
const showPhoneInput = createVisibilityChecker('PHONE')
const showPwdInput = createVisibilityChecker('PASSWORD')
const showConfirmPwdInput = createVisibilityChecker('CONFIRM_PASSWORD')
const showIdentityInput = createVisibilityChecker('IDENTITY')
const showCodeInput = createVisibilityChecker('CODE')

const form = ref({
	username: '',
	password: '',
	confirmPassword: '',
	phone: '',
	identity: '',
	code: '',
})
const rememberMe = ref(false)

const switchForm = (target) => {
	model.value = target
	refForm.value.resetFields()
}

const refForm = ref()

const validateConfirmPassword = (rule, value, callback) => {
	if (value === '') {
		callback(new Error('请再次输入密码'))
	} else if (value !== form.value.password) {
		callback(new Error('两次输入密码不一致!'))
	} else {
		callback()
	}
}

// 打开下拉框但不选任何选项
// 默认组件不触发校验
// 所以需要手动触发校验
const handleChange = (visible) => {
	if (!visible) {
		refForm.value.validateField('identity')
	}
}

const rules = ref({
	username: [
		{ required: true, message: '请输入用户名', trigger: ['blur', 'change'] },
		{ min: 0, max: 20, message: '用户名长度不能超过20个字符', trigger: 'change' },
	],
	password: [
		{ required: true, message: '请输入密码', trigger: ['blur', 'change'] },
		{ min: 6, max: 20, message: '用户密码长度必须在6到20位之间', trigger: 'change' },
	],
	confirmPassword: [
		{ required: true, message: '请再次输入密码', trigger: ['blur', 'change'] },
		{ validator: validateConfirmPassword, trigger: 'change' },
	],
	phone: [
		{ required: true, message: '请输入手机号', trigger: ['blur', 'change'] },
		{ type: 'number', message: '手机号必须为数字', trigger: 'change' },
		{ pattern: /^1[3456789]\d{9}$/, message: '手机号格式错误', trigger: 'change' },
	],
	identity: [
		{ required: true, message: '请选择身份', trigger: ['blur', 'change', 'visible-change'] },
	],
	code: [{ required: true, message: '请输入短信验证码', trigger: ['blur', 'change'] }],
})

const sendMsg = async (phone) => {
	refForm.value.validateField('phone', async (valid) => {
		if (!valid) {
			ElMessage.error('请先正确填写手机号码')
			return
		}
		const response = await sendCode({ phone })
    console.log(response)
		sendCodeAccess.value = false
		let timer = setInterval(() => {
			if(sendCodeTimer.value === 0) {
        sendCodeAccess.value = true
				sendCodeTimer.value = 60
				clearInterval(timer)
				return
			}else if(sendCodeTimer.value > 0) {
				sendCodeTimer.value--
			}
		}, 1000)
		ElMessage({
			message: response.code ? `验证码已发送至${phone}` : '发送失败，请重试',
			type: response.code ? 'success' : 'error',
		})
	})
}

// 验证合法 然后发送请求 最后弹出提示
const submit = () => {
	const source = model.value
	const target = `/user${type[source].action}`
	console.log('target:', target)
	refForm.value.validate(async (valid) => {
		if (!valid) {
			ElMessage.error('请先正确填写表单')
			return
		}
		switch (target) {
			case '/user/loginAccount':
				await loginAccountReq()
				break
			case '/user/register':
				await registerReq()
				break
			case '/user/loginPhone':
				await loginPhoneReq()
				break
			default:
				console.log('待处理 重置密码 等等事项')
		}
	})
}

const userStore = useUserStore()

const loginAccountReq = async () => {
	console.log('登录请求')
	const { username, password, phone } = form.value
	const payload = {
		username,
		password,
	}
	try{
		const response = await loginAccount(payload)
		console.log(response)
		if(response.code === 1) {
			ElMessage.success('登录成功')
			const userInfo = {
        username,
        phone,
        identity: response.data.identity,
				token: response.data.token,
      }
			userStore.saveUserInfo(userInfo)
			console.log('userInfo:', userStore.userInfo)
		}else {
			ElMessage.error('登录失败')
		}
	}catch(error) {
		ElMessage.error('登录失败')
	}
}

const loginPhoneReq = async () => {
	console.log('短信登录请求')
	const { username, phone, code } = form.value
	const payload = {
		phone,
		code,
	}
	try {
		const response = await loginPhone(payload)
		console.log(response)
		if(response.code === 1) {
			ElMessage.success('登录成功')
			const userInfo = {
        username,
        phone,
        identity: response.data.identity,
				token: response.data.token,
      }
			userStore.saveUserInfo(userInfo)
		}else {
			ElMessage.error('登录失败')
		}
	} catch (error) {
		ElMessage.error('登录失败')
	}
}

const registerReq = async () => {
	const { username, password, phone, identity, code } = form.value
	const payload = {
		username,
		password,
		phone,
		identity,
		code,
	}
	try {
		const response = await register(payload)
		console.log(response)
		response.code === 1 ? ElMessage.success('注册成功') : ElMessage.error('注册失败')
	} catch (error) {
		ElMessage.error('注册失败')
	}
}
</script>

<style lang="scss" scoped>
@use './loginForm.scss' as loginFormStyles;
</style>
