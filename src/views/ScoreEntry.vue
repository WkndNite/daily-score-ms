<template>
  <div class="score-entry">
    <el-card>
      <template #header>
        <div class="card-header">
          <div class="filters">
            <el-select v-model="selectedClass" placeholder="选择班级">
              <el-option
                v-for="item in classes"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
            <el-select v-model="selectedScoreItem" placeholder="选择评分项">
              <el-option
                v-for="item in scoreItems"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </div>
        </div>
      </template>

      <el-table :data="students" border>
        <el-table-column prop="studentId" label="学号" width="120" />
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column label="分数">
          <template #default="scope">
            <el-input-number v-model="scope.row.score" :min="0" :max="100" size="small" />
          </template>
        </el-table-column>
        <el-table-column prop="comment" label="评语">
          <template #default="scope">
            <el-input v-model="scope.row.comment" />
          </template>
        </el-table-column>
      </el-table>

      <div class="actions">
        <el-button type="primary" @click="handleSave">保存</el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'

const selectedClass = ref('')
const selectedScoreItem = ref('')

const classes = ref([
  { value: '1', label: '计算机科学1班' },
  { value: '2', label: '计算机科学2班' },
])

const scoreItems = ref([
  { id: 1, name: '课堂表现' },
  { id: 2, name: '作业完成' },
  { id: 3, name: '出勤情况' },
])

const students = ref([
  { studentId: '2021001', name: '张三', score: 85, comment: '' },
  { studentId: '2021002', name: '李四', score: 90, comment: '' },
  { studentId: '2021003', name: '王五', score: 88, comment: '' },
])

const handleSave = () => {
  ElMessage.success('保存成功')
}
</script>

<style lang="scss" scoped>
.score-entry {
  .filters {
    display: flex;
    gap: 20px;
  }
  .actions {
    margin-top: 20px;
    text-align: center;
  }
}
</style>
