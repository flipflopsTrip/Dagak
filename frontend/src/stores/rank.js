import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";

export const useRankStore = defineStore('counter', () => {
const API_URL = `${process.env.VITE_API_BASE_URL}/`;
const mokkojiRank = ref([])
// const getMokkojiRank = function () {
//     axios({
//       method: "get",
//       url: `${API_URL}/dagak/mokkoji/rank10`,
//     })
//       .then((response) => {
//         console.log('js.mokkojirank: ',response.data.data[0].mokkoji)
//         mokkojiRank.value = response.data.data[0].mokkoji;
//       })
//       .catch((error) => {
//         console.log(error);
//       });
//   };
const getMokkojiRank = async function () {
  try {
    const response = await axios.get(`${process.env.VITE_API_BASE_URL}/mokkoji/rank10`);
    console.log('js.mokkojirank: ', response.data.data[0].mokkoji);
    console.log('js.mokkojirank: ', response.data.data);
    mokkojiRank.value = response.data.data;
  } catch (error) {
    console.error(error);
  }
};

  return { API_URL, mokkojiRank, getMokkojiRank }
}, { persist: true })