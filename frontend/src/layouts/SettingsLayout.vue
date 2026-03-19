<template>
  <div class="grid grid-cols-12 gap-4">
    <aside class="col-span-12 md:col-span-3 lg:col-span-2">
      <div class="page-card p-3 space-y-2">
        <router-link class="block p-2 rounded-lg hover:bg-slate-50" to="/settings/account">账号管理</router-link>
        <router-link class="block p-2 rounded-lg hover:bg-slate-50" to="/settings/class-students">班级学生</router-link>
        <router-link class="block p-2 rounded-lg hover:bg-slate-50" to="/settings/rules">分值管理</router-link>
        <router-link class="block p-2 rounded-lg hover:bg-slate-50" to="/settings/logs">操作日志</router-link>
        <router-link class="block p-2 rounded-lg hover:bg-slate-50 text-rose-600" to="/settings/danger">危险操作区</router-link>
      </div>
    </aside>
    <section class="col-span-12 md:col-span-9 lg:col-span-10">
      <router-view />
    </section>
  </div>
</template>

<style scoped>
a.router-link-active {
  background: #eef2ff;
  color: #3730a3;
}
</style>
