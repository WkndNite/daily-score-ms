<template>
	<div class="common-layout">
		<el-container>
			<aside-menu :collapsed :activeMenu />
			<el-container :class="{ 'main-container': true, 'is-collapsed': collapsed }">
				<header-nav v-model:collapsed="collapsed" />
				<tab-control :activeTab v-model:visitedViews="visitedViews" />
				<el-main>
					<router-view v-slot="{ Component }">
						<keep-alive>
							<component :is="Component" />
						</keep-alive>
					</router-view>
				</el-main>
			</el-container>
		</el-container>
	</div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import AsideMenu from '@/components/aside/AsideMenu.vue'
import HeaderNav from '@/components/header/HeaderNav.vue'
import TabControl from '@/components/tabs/TabControl.vue'

const route = useRoute()
const collapsed = ref(false)
const activeMenu = ref('/dashboard')

const activeTab = ref('home')
const visitedViews = ref([
	{
		path: '/dashboard',
    name: 'dashboard',
    title: '工作台',
    icon: 'HomeFilled',
	},
])

watch(
	() => route.path,
	(newPath) => {
		activeMenu.value = newPath
		activeTab.value = route.name
    console.log(visitedViews.value)
		if (!visitedViews.value.some((item) => item.name === route.name)) {
			visitedViews.value.push({
				path: newPath,
				name: route.name,
				title: route.meta.title,
			})
		}
	},
	{ immediate: true },
)
</script>

<style lang="scss" scoped>
.common-layout {
	height: 100vh;
	min-width: 1200px;
}

.main-container {
	position: relative;
	margin-left: 240px;
	transition: margin-left 0.3s cubic-bezier(0.4, 0, 0.2, 1);
	min-height: 100vh;
	display: flex;
	flex-direction: column;

	&.is-collapsed {
		margin-left: 64px;
	}
}

.el-main {
	flex: 1;
	background-color: #f5f7fa;
	padding: 1rem;
}

:deep(.el-menu) {
	border-right: none !important;
	width: 100% !important;
}

:deep(.el-menu-item) {
	height: 50px;
	line-height: 50px;
	padding: 0 20px !important;
	transition:
		background-color 0.3s,
		color 0.3s;

	.el-icon {
		font-size: 18px;
		margin-right: 12px;
	}

	span {
		display: inline-block;
		white-space: nowrap;
	}
}
</style>
