<template>
  <div class="score-analysis">
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>班级平均分分布</span>
            </div>
          </template>
          <div class="chart" ref="averageChart"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>成绩区间分布</span>
            </div>
          </template>
          <div class="chart" ref="distributionChart"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="mt-20">
      <template #header>
        <div class="card-header">
          <span>成绩趋势分析</span>
        </div>
      </template>
      <div class="chart" ref="trendChart"></div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'

const averageChart = ref(null)
const distributionChart = ref(null)
const trendChart = ref(null)

onMounted(() => {
  // 班级平均分柱状图
  const avgChart = echarts.init(averageChart.value)
  avgChart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: {
      type: 'category',
      data: ['课堂表现', '作业完成', '出勤情况'],
    },
    yAxis: { type: 'value' },
    series: [
      {
        data: [85, 88, 92],
        type: 'bar',
      },
    ],
  })

  // 成绩分布饼图
  const distChart = echarts.init(distributionChart.value)
  distChart.setOption({
    tooltip: { trigger: 'item' },
    series: [
      {
        type: 'pie',
        radius: '70%',
        data: [
          { value: 10, name: '90-100分' },
          { value: 20, name: '80-89分' },
          { value: 15, name: '70-79分' },
          { value: 5, name: '60-69分' },
          { value: 2, name: '60分以下' },
        ],
      },
    ],
  })

  // 成绩趋势折线图
  const lineChart = echarts.init(trendChart.value)
  lineChart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: {
      type: 'category',
      data: ['第1周', '第2周', '第3周', '第4周', '第5周'],
    },
    yAxis: { type: 'value' },
    series: [
      {
        data: [82, 85, 88, 86, 90],
        type: 'line',
        smooth: true,
      },
    ],
  })
})
</script>

<style lang="scss" scoped>
.score-analysis {
  .chart {
    height: 300px;
  }
  .mt-20 {
    margin-top: 20px;
  }
}
</style>
