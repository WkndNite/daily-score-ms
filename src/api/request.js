import axios from 'axios'

export const request = axios.create({
	baseURL: 'http://47.104.26.25:8081',
	timeout: 5000,
})

request.interceptors.request.use(
	(config) => {
		config.headers['Content-Type'] = 'application/json'
		return config
	},
	(error) => {
		return Promise.reject(error)
	},
)

request.interceptors.response.use(
	(response) => {
		return response.data
	},
	(error) => {
		return Promise.reject(error)
	},
)
