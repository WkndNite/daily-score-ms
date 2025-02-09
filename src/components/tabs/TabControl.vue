<template>
	<div class="tabs-bar">
		<el-tabs
			:model-value="activeTab"
			type="card"
			@tab-click="handleTabClick"
			@tab-remove="handleTabRemove"
		>
			<el-tab-pane
				v-for="tab in visitedViews"
				:key="tab.path"
				:label="tab.title"
				:name="tab.name"
				:closable="closable"
			/>

		</el-tabs>
	</div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { computed, defineModel} from 'vue'

const router = useRouter()
const activeTab = defineModel('activeTab')
const visitedViews = defineModel('visitedViews')

const closable = computed(() => visitedViews.value.length > 1)

const handleTabClick = (tab) => {
	activeTab.value = tab.props.name
	const view = visitedViews.value.find((v) => v.name === tab.props.name)
	if (view) {
		router.push(view.path)
	}
}

const handleTabRemove = (tabName) => {
	const tabs = visitedViews.value
	const activeName = activeTab.value

	if (activeName === tabName) {
		tabs.forEach((tab, index) => {
			if (tab.name === tabName) {
				const nextTab = tabs[index + 1] || tabs[index - 1]
				if (nextTab) {
					activeTab.value = nextTab.name
					router.push(nextTab.path)
				}
			}
		})
	}

	visitedViews.value = tabs.filter((tab) => tab.name !== tabName)

}
</script>

<style lang="scss" scoped>
.tabs-bar {
	padding: 1rem;
	position: relative;
	border-bottom: 1px solid #e4e7ed;

	.el-tabs {
		--el-tabs-header-height: 1rem;
	}

	:deep(.el-tabs__header) {
		margin: 0;
		border-bottom: none;
	}

	:deep(.el-tabs__nav) {
		border: none !important;
	}

	:deep(.el-tabs__item) {
		height: 2rem;
		line-height: 2rem;
		padding: 0 1rem !important;
		border: 1px solid #e4e7ed !important;
		margin-right: 0.25rem;
		border-radius: 3px;
		transition: all 0.3s;
		box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);

		&:hover {
			color: #18a058;
			box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
		}

		&.is-active {
			color: #18a058;
			border-color: #18a058 !important;
			background-color: #e8f5e9;

			&::before {
				content: '';
				position: absolute;
				left: -1px;
				right: -1px;
				top: -1px;
				height: 2px;
				background-color: #18a058;
				border-radius: 2px 2px 0 0;
			}
		}

		.el-icon-close {
			font-size: 12px;
			height: 14px;
			width: 14px;
			vertical-align: middle;
			margin-left: 4px;
			border-radius: 50%;
			transition: all 0.3s;
			transform-origin: center;

			&:hover {
				background-color: #666;
				color: #fff;
				transform: rotate(90deg);
			}
		}
	}
}
</style>
