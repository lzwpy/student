<script setup lang="ts">
import { computed } from "vue";

const props = withDefaults(defineProps<{
  name:
    | "pet"
    | "shop"
    | "trophy"
    | "gift"
    | "settings"
    | "history"
    | "star"
    | "warning"
    | "coin"
    | "student"
    | "refresh"
    | "edit"
    | "remove"
    | "spark";
  tone?: "mint" | "gold" | "rose" | "slate" | "dark";
  size?: number;
}>(), {
  tone: "mint",
  size: 18
});

const toneClass = computed(() => {
  return {
    mint: "bg-mint-soft text-mint-dark",
    gold: "bg-gold-soft text-gold-dark",
    rose: "bg-rosey-soft text-rosey-dark",
    slate: "bg-slate-100 text-slate-600",
    dark: "bg-slate-800 text-white"
  }[props.tone];
});
</script>

<template>
  <span
    class="inline-flex items-center justify-center rounded-2xl"
    :class="toneClass"
    :style="{ width: `${size + 18}px`, height: `${size + 18}px` }"
  >
    <svg
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      stroke-width="1.8"
      stroke-linecap="round"
      stroke-linejoin="round"
      :style="{ width: `${size}px`, height: `${size}px` }"
      aria-hidden="true"
    >
      <g v-if="name === 'pet'">
        <ellipse cx="12" cy="16.2" rx="4.4" ry="3" />
        <circle cx="7.4" cy="10.2" r="2.15" />
        <circle cx="12" cy="8.1" r="2.15" />
        <circle cx="16.6" cy="10.2" r="2.15" />
        <circle cx="9.3" cy="5.9" r="1.75" />
        <circle cx="14.7" cy="5.9" r="1.75" />
      </g>

      <g v-else-if="name === 'shop'">
        <path d="M4 9.5h16" />
        <path d="M6 9.5v8.5h12V9.5" />
        <path d="M5.5 9.5 7 5h10l1.5 4.5" />
        <path d="M10 18v-4h4v4" />
      </g>

      <g v-else-if="name === 'trophy'">
        <path d="M8 5h8v3c0 2.2-1.8 4-4 4s-4-1.8-4-4V5Z" />
        <path d="M10 12v3M14 12v3M8 19h8M9 15h6" />
        <path d="M8 6H5c0 2.2 1.1 4 3 4M16 6h3c0 2.2-1.1 4-3 4" />
      </g>

      <g v-else-if="name === 'gift'">
        <rect x="4.5" y="9" width="15" height="10" rx="2.5" />
        <path d="M12 9v10M4.5 12.5h15" />
        <path d="M12 9s-1.2-3-3.5-3C7 6 6 7 6 8.2 6 9.2 7 9 7 9h5Z" />
        <path d="M12 9s1.2-3 3.5-3C17 6 18 7 18 8.2 18 9.2 17 9 17 9h-5Z" />
      </g>

      <g v-else-if="name === 'settings'">
        <circle cx="12" cy="12" r="3.2" />
        <path d="M12 3.8v2.2M12 18v2.2M20.2 12H18M6 12H3.8M17.8 6.2l-1.6 1.6M7.8 16.2l-1.6 1.6M17.8 17.8l-1.6-1.6M7.8 7.8 6.2 6.2" />
      </g>

      <g v-else-if="name === 'history'">
        <path d="M5.2 11a6.8 6.8 0 1 1 2.3 5.1" />
        <path d="M5 7.5v3.8h3.8M12 8.6v3.8l2.5 1.7" />
      </g>

      <g v-else-if="name === 'star'">
        <path d="m12 4 2.2 4.5 5 .7-3.6 3.5.9 5-4.5-2.4-4.5 2.4.9-5L4.8 9.2l5-.7L12 4Z" />
      </g>

      <g v-else-if="name === 'warning'">
        <path d="M12 4.2 20 18H4l8-13.8Z" />
        <path d="M12 9v3.8M12 15.8h.01" />
      </g>

      <g v-else-if="name === 'coin'">
        <ellipse cx="12" cy="16" rx="7.2" ry="2.4" />
        <circle cx="12" cy="9.5" r="6.2" />
        <path d="M8.8 9.2h6.4M12 6.2v6" stroke-width="1.55" />
      </g>

      <g v-else-if="name === 'student'">
        <circle cx="12" cy="8.2" r="3.1" />
        <path d="M6.5 18c1.1-2.7 3.2-4.1 5.5-4.1s4.4 1.4 5.5 4.1" />
      </g>

      <g v-else-if="name === 'refresh'">
        <path d="M19 11a7 7 0 0 0-12-3.8M5 13a7 7 0 0 0 12 3.8" />
        <path d="M6.5 5.8H7v4.2H2.8M17.5 18.2H17v-4.2h4.2" />
      </g>

      <g v-else-if="name === 'edit'">
        <path d="m6 18 1.2-4.4L15.8 5a1.6 1.6 0 0 1 2.2 0l1 1a1.6 1.6 0 0 1 0 2.2l-8.6 8.6L6 18Z" />
        <path d="M14.2 6.6 17.4 9.8" />
      </g>

      <g v-else-if="name === 'remove'">
        <path d="M5.5 7h13M9.2 7V5.3h5.6V7M8 7l.8 11h6.4L16 7" />
      </g>

      <g v-else-if="name === 'spark'">
        <path d="M12 4.4 13.6 9 18 10.4l-4.4 1.4-1.6 4.6-1.6-4.6L6 10.4 10.4 9 12 4.4Z" />
        <path d="M18.5 4.8 19.1 6.4 20.7 7l-1.6.6-.6 1.6-.6-1.6-1.6-.6 1.6-.6.6-1.6ZM5.3 15.8l.6 1.6 1.6.6-1.6.6-.6 1.6-.6-1.6-1.6-.6 1.6-.6.6-1.6Z" />
      </g>
    </svg>
  </span>
</template>
