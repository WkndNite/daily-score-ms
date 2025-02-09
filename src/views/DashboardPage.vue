<template>
  <div class="dashboard-page">
    <!-- 数据概览卡片 -->
    <el-row :gutter="20">
      <el-col :span="6" v-for="(item, index) in statistics" :key="index">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <el-icon class="stat-icon" :class="item.color">
              <component :is="item.icon" />
            </el-icon>
            <div class="stat-info">
              <div class="stat-value">{{ item.value }}</div>
              <div class="stat-label">{{ item.label }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" class="chart-row">
      <el-col :span="16">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>周访问量统计</span>
            </div>
          </template>
          <div class="chart" ref="weeklyChart"></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>用户分布</span>
            </div>
          </template>
          <div class="chart" ref="pieChart"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import * as echarts from 'echarts'
import { useDark } from '@vueuse/core'

const isDark = useDark()
const lineChart = ref(null)
const distributionChart = ref(null)
const weeklyChart = ref(null)
const pieChart = ref(null)

const statistics = ref([
  { label: '用户总数', value: '1,234', icon: 'User', color: 'blue' },
  { label: '商品总数', value: '456', icon: 'Goods', color: 'green' },
  { label: '订单总数', value: '789', icon: 'Tickets', color: 'yellow' },
  { label: '店铺总数', value: '56', icon: 'Shop', color: 'red' },
])

onMounted(() => {
  initCharts()
})

const initCharts = () => {
  lineChart.value = echarts.init(weeklyChart.value)
  distributionChart.value = echarts.init(pieChart.value)
  updateCharts()
}

const updateCharts = () => {
  const theme = isDark.value
    ? {
        textStyle: { color: '#fff' },
        backgroundColor: 'transparent',
      }
    : {
        textStyle: { color: '#333' },
        backgroundColor: 'transparent',
      }

  lineChart.value?.setOption({
    ...theme,
    tooltip: { trigger: 'axis' },
    xAxis: {
      type: 'category',
      data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
    },
    yAxis: { type: 'value' },
    series: [
      {
        data: [820, 932, 901, 934, 1290, 1330, 1320],
        type: 'line',
        smooth: true,
      },
    ],
  })

  distributionChart.value?.setOption({
    ...theme,
    tooltip: { trigger: 'item' },
    series: [
      {
        type: 'pie',
        radius: '70%',
        data: [
          { value: 335, name: '直接访问' },
          { value: 310, name: '邮件营销' },
          { value: 234, name: '联盟广告' },
          { value: 135, name: '视频广告' },
          { value: 1548, name: '搜索引擎' },
        ],
      },
    ],
  })
}

watch(isDark, () => {
  updateCharts()
})
</script>

<style lang="scss" scoped>
.dashboard-page {
  .stat-card {
    margin-bottom: 20px;
    .stat-content {
      display: flex;
      align-items: center;
      .stat-icon {
        font-size: 3rem;
        margin-right: 1rem;
        &.blue {
          color: #409eff;
        }
        &.green {
          color: #67c23a;
        }
        &.yellow {
          color: #e6a23c;
        }
        &.red {
          color: #f56c6c;
        }
      }
      .stat-info {
        .stat-value {
          font-size: 1.5rem;
          font-weight: bold;
        }
        .stat-label {
          color: #909399;
        }
      }
    }
  }

  .chart-row {
    margin-top: 20px;
    .chart {
      height: 350px;
    }
  }
}
</style>
