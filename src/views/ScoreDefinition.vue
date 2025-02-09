<template>
  <div class="score-definition">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>平时分项目定义</span>
          <el-button type="primary" @click="dialogVisible = true"> 添加评分项 </el-button>
        </div>
      </template>

      <el-table :data="scoreItems" border>
        <el-table-column prop="name" label="评分项目" />
        <el-table-column prop="percentage" label="占比(%)" width="120" />
        <el-table-column prop="description" label="描述" />
        <el-table-column label="操作" width="150">
          <template #default="scope">
            <el-button type="primary" link @click="handleEdit(scope.row)"> 编辑 </el-button>
            <el-button type="danger" link @click="handleDelete(scope.row)"> 删除 </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="editingItem ? '编辑评分项' : '添加评分项'"
      width="500px"
    >
      <el-form :model="form" label-width="100px">
        <el-form-item label="评分项目">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="占比">
          <el-input-number v-model="form.percentage" :min="0" :max="100" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSave">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ElMessageBox, ElMessage } from 'element-plus'
import { ref } from 'vue'

const scoreItems = ref([
  { id: 1, name: '课堂表现', percentage: 30, description: '包括课堂参与度、回答问题等' },
  { id: 2, name: '作业完成', percentage: 40, description: '日常作业的完成情况' },
  { id: 3, name: '出勤情况', percentage: 30, description: '课堂出勤记录' },
])

const dialogVisible = ref(false)
const editingItem = ref(null)
const form = ref({
  name: '',
  percentage: 0,
  description: '',
})

const handleEdit = (row) => {
  editingItem.value = row
  form.value = { ...row }
  dialogVisible.value = true
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该评分项吗？', '提示', {
    type: 'warning',
  }).then(() => {
    scoreItems.value = scoreItems.value.filter((item) => item.id !== row.id)
    ElMessage.success('删除成功')
  })
}

const handleSave = () => {
  if (editingItem.value) {
    const index = scoreItems.value.findIndex((item) => item.id === editingItem.value.id)
    scoreItems.value[index] = { ...form.value, id: editingItem.value.id }
  } else {
    scoreItems.value.push({
      id: Date.now(),
      ...form.value,
    })
  }
  dialogVisible.value = false
  editingItem.value = null
  form.value = { name: '', percentage: 0, description: '' }
}
</script>

<style lang="scss" scoped>
.score-definition {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
</style>
