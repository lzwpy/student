<template>
  <div class="page-card p-6 border-rose-200">
    <h3 class="text-xl font-semibold text-rose-600">危险操作区</h3>
    <p class="text-slate-600 mt-2">
      可扩展：重置班级分值、清空日志、删除班级等高风险操作。当前版本仅保留占位提醒，避免误操作。
    </p>
    <el-alert class="mt-4" type="warning" :closable="false" title="请在确认数据备份后再开放危险操作。" />
  </div>
</template>
