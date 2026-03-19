<script setup lang="ts">
const eggOptions = [
  { label: "小猫", value: "cat_1" },
  { label: "小狗", value: "dog_1" },
  { label: "小企鹅", value: "penguin_1" },
  { label: "小老虎", value: "tiger_1" }
];

defineProps<{ modelValue: string }>();
const emit = defineEmits<{ "update:modelValue": [value: string] }>();
</script>

<template>
  <el-select :model-value="modelValue" placeholder="选择宠物蛋" @update:model-value="(v:string)=>emit('update:modelValue', v)">
    <el-option v-for="item in eggOptions" :key="item.value" :label="item.label" :value="item.value" />
  </el-select>
</template>
