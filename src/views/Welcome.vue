<template>
	<div class="welcome-container">
		<div class="welcome-content">
			<el-icon class="welcome-icon"><HomeFilled /></el-icon>
			<h1>欢迎使用学生平时分管理系统</h1>
			<p>请从左侧菜单选择功能开始使用</p>
		</div>
	</div>
</template>

<script setup>
import { HomeFilled } from '@element-plus/icons-vue'
</script>

<style lang="scss" scoped>
.welcome-container {
	height: 100%;
	display: flex;
	align-items: center;
	justify-content: center;
	background-color: #fff;
	border-radius: 4px;
}

.welcome-content {
	text-align: center;

	.welcome-icon {
		font-size: 48px;
		color: var(--el-color-primary);
		margin-bottom: 16px;
	}

	h1 {
		font-size: 24px;
		color: var(--el-text-color-primary);
		margin-bottom: 8px;
		font-weight: 500;
	}

	p {
		font-size: 14px;
		color: var(--el-text-color-secondary);
	}
}
</style>
