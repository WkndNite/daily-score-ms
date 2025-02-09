<template>
	<el-header>
		<div class="header-content">
			<div class="left">
				<el-icon class="collapse-btn" @click="collapsed = !collapsed">
					<Fold v-if="!collapsed" />
					<Expand v-else />
				</el-icon>
				<el-breadcrumb>
					<el-breadcrumb-item :to="{ path: '/' }">
						<el-icon><HomeFilled /></el-icon>
						<span>首页</span>
					</el-breadcrumb-item>
					<el-breadcrumb-item v-if="currentRoute.meta?.title">
						<span>{{ currentRoute.meta?.title }}</span>
					</el-breadcrumb-item>
				</el-breadcrumb>
			</div>
			<div class="right">
				<div class="right-menu">
					<el-tooltip :content="isFullscreen ? '退出全屏' : '全屏'" placement="bottom">
						<el-icon class="right-menu-item" @click="toggleFullScreen">
							<FullScreen v-if="!isFullscreen" />
							<Aim v-else />
						</el-icon>
					</el-tooltip>

					<el-dropdown class="user-dropdown" trigger="click">
						<div class="avatar-wrapper">
							<img src="@/assets/logo.jpg" class="user-avatar" />
							<span class="user-name">管理员</span>
							<el-icon><CaretBottom /></el-icon>
						</div>
						<template #dropdown>
							<el-dropdown-menu>
								<el-dropdown-item>
									<el-icon><User /></el-icon>个人信息
								</el-dropdown-item>
								<el-dropdown-item>
									<el-icon><Setting /></el-icon>系统设置
								</el-dropdown-item>
								<el-dropdown-item divided>
									<el-icon><SwitchButton /></el-icon>退出登录
								</el-dropdown-item>
							</el-dropdown-menu>
						</template>
					</el-dropdown>
				</div>
			</div>
		</div>
	</el-header>
</template>

<script setup>
import { ref, computed,defineModel } from 'vue'
import { useRoute } from 'vue-router'
import {
	HomeFilled,
	Setting,
	Fold,
	Expand,
	CaretBottom,
	FullScreen,
	Aim,
	User,
	SwitchButton,
} from '@element-plus/icons-vue'

const collapsed = defineModel('collapsed')

const route = useRoute()
const currentRoute = computed(() => route)
const isFullscreen = ref(false)


const toggleFullScreen = () => {
	if (!document.fullscreenElement) {
		document.documentElement.requestFullscreen()
		isFullscreen.value = true
	} else {
		if (document.exitFullscreen) {
			document.exitFullscreen()
			isFullscreen.value = false
		}
	}
}
</script>

<style lang="scss" scoped>
.el-header {
	background-color: #fff;
	padding: 0;
	height: 48px;
	border-bottom: 1px solid var(--el-border-color-light);
	position: relative;
}

.header-content {
	height: 100%;
	display: flex;
	align-items: center;
	justify-content: space-between;
	padding: 0 16px;

	.left {
		display: flex;
		align-items: center;

		.collapse-btn {
			font-size: 20px;
			margin-right: 16px;
			cursor: pointer;
			color: var(--el-text-color-primary);

			&:hover {
				color: var(--el-color-primary);
			}
		}

		:deep(.el-breadcrumb__item) {
			.el-breadcrumb__inner {
				display: flex;
				align-items: center;
				font-weight: normal;
				color: var(--el-text-color-regular);

				&.is-link {
					color: var(--el-text-color-primary);

					&:hover {
						color: var(--el-color-primary);
					}
				}

				.el-icon {
					margin-right: 4px;
					font-size: 16px;
				}
			}
		}
	}

	.right {
		.right-menu {
			display: flex;
			align-items: center;

			.right-menu-item {
				padding: 0 8px;
				font-size: 18px;
				color: var(--el-text-color-regular);
				cursor: pointer;
				transition: color 0.3s;

				&:hover {
					color: var(--el-color-primary);
				}
			}

			.user-dropdown {
				margin-left: 12px;

				.avatar-wrapper {
					display: flex;
					align-items: center;
					cursor: pointer;
					padding: 0 8px;

					.user-avatar {
						width: 24px;
						height: 24px;
						border-radius: 50%;
						margin-right: 8px;
					}

					.user-name {
						font-size: 14px;
						color: var(--el-text-color-primary);
						margin-right: 4px;
					}

					.el-icon {
						font-size: 12px;
						color: var(--el-text-color-regular);
					}
				}
			}
		}
	}
}

:deep(.el-dropdown-menu__item) {
	display: flex;
	align-items: center;
	padding: 8px 16px;
	font-size: 14px;
	line-height: 1.5;

	.el-icon {
		margin-right: 8px;
		font-size: 16px;
	}

	&:hover {
		color: var(--el-color-primary);
		background-color: var(--el-color-primary-light-9);
	}
}
</style>
