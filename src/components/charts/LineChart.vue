// 在图表配置中使用这些变量
const chartOption = {
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    axisLine: {
      lineStyle: {
        color: 'var(--chart-grid-color)'
      }
    },
    axisLabel: {
      color: 'var(--chart-text-color)'
    }
  },
  yAxis: {
    type: 'value',
    axisLine: {
      lineStyle: {
        color: 'var(--chart-grid-color)'
      }
    },
    splitLine: {
      lineStyle: {
        color: 'var(--chart-grid-color)',
        opacity: 0.1
      }
    },
    axisLabel: {
      color: 'var(--chart-text-color)'
    }
  },
  series: [{
    lineStyle: {
      color: 'var(--chart-line-color)'
    },
    // ... 其他配置
  }]
}
