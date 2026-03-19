import { defineStore } from "pinia";
import { getClassroomsApi } from "@/api/classroom";
import type { Classroom } from "@/types";

export const useClassroomStore = defineStore("classroom", {
  state: () => ({
    classrooms: [] as Classroom[],
    activeClassId: Number(localStorage.getItem("activeClassId") || 0) || null
  }),
  actions: {
    async loadClassrooms() {
      this.classrooms = await getClassroomsApi();
      if (!this.activeClassId && this.classrooms.length > 0) {
        this.setActiveClass(this.classrooms[0].id);
      }
    },
    setActiveClass(id: number) {
      this.activeClassId = id;
      localStorage.setItem("activeClassId", String(id));
    }
  }
});
