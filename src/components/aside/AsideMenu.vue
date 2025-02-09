<template>
    <el-aside :width="collapsed ? '4rem' : '15rem'" class="aside-menu">
				<div class="logo">
					<el-icon><School /></el-icon>
					<span v-if="!collapsed">学生平时分管理</span>
				</div>
				<el-menu
					:default-active="activeMenu"
					class="side-menu"
					router
					:collapse="collapsed"
					:collapse-transition="false"
					active-text-color="#67C23A"
				>
					<el-menu-item index="/dashboard">
						<el-icon><HomeFilled /></el-icon>
						<template #title>工作台</template>
					</el-menu-item>
					<el-menu-item index="/score-definition">
						<el-icon><Setting /></el-icon>
						<template #title>平时分定义</template>
					</el-menu-item>
					<el-menu-item index="/score-entry">
						<el-icon><EditPen /></el-icon>
						<template #title>平时分录入</template>
					</el-menu-item>
					<el-menu-item index="/score-analysis">
						<el-icon><DataLine /></el-icon>
						<template #title>成绩分析</template>
					</el-menu-item>
				</el-menu>
			</el-aside>
</template>

<script setup>
const collapsed = defineModel('collapsed')
const activeMenu = defineModel('activeMenu')
import {
	HomeFilled,
	Setting,
	EditPen,
	DataLine,
	School,
} from '@element-plus/icons-vue'
</script>

<style lang="scss" scoped>
.aside-menu {
	position: fixed;
	left: 0;
	top: 0;
	bottom: 0;
	z-index: 1001;
	transition: width 0.3s cubic-bezier(0.4, 0, 0.2, 1);
	overflow: hidden;
	background-color: #fff;
	border-right: 1px solid #f0f0f0;

	.logo {
		transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
		white-space: nowrap;

		span {
			display: inline-block;
			white-space: nowrap;
		}
	}
}

// Logo 样式
.logo {
	height: 48px;
	display: flex;
	align-items: center;
	padding: 0 20px;
	color: #252525;
	font-size: 18px;
	font-weight: bold;
	border-bottom: 1px solid #f0f0f0;
	background-color: #fff;
	transition: padding 0.3s;

	.el-icon {
		margin-right: 12px;
		font-size: 24px;
		color: #18a058;
		transition: margin-right 0.3s;
	}

	span {
		display: inline-block;
		opacity: 1;
		transition: opacity 0.2s;
	}

	&.collapsed {
		padding: 0;
		justify-content: center;

		.el-icon {
			margin-right: 0;
		}

		span {
			width: 0;
			opacity: 0;
		}
	}
}
</style>
