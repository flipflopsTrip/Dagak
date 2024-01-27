import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";

export const useRankStore = defineStore('counter', () => {
const API_URL = `${import.meta.env.VITE_API_BASE_URL}/`;
const mokkojiRank = ref([])

const getMokkojiRank = async function () {
  try {
    const response = await axios.get(`${import.meta.env.VITE_API_BASE_URL}/mokkoji/rank10`);
    console.log('mokkojirank : ', response.result);
    console.log('js.mokkojirank: ', response.data.result[0].mokkoji);
    console.log('js.mokkojirank: ', response.data.result);
    mokkojiRank.value = response.data.result;
  } catch (error) {
    console.error(error);
  }
};

  return { API_URL, mokkojiRank, getMokkojiRank }
}, { persist: true })