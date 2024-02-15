import { ref, onMounted, watch } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import { cookiesStorage, userCookiesStorage } from '@/utils/CookiesUtil';
import { useAlarmStore } from '@/stores/alarm';
import { useFriendStore } from '@/stores/friend';

axios.defaults.headers.post['Content-Type'] = 'application/json';

export const useUserStore = defineStore(
  'userStore',
  () => {
    const mySessionToken = ref('');
    const studyRoomSessionToken = ref('');
    const loginUserInfo = ref({});
    const isInSession = ref(false);
    const achievementRate = ref(0);
    const friendStore = useFriendStore();
    const userStore = useUserStore();
    //로그인 세션 test
    const login = async function () {
      friendStore.getLoginFriends(); // 로그인했을때 로그인한 친구들 목록 확인하기
      await loginSession();
      // alert('방입장 성공');
    };

    const OVMy = ref(undefined);
    const mySession = ref(undefined);
    const mainStreamManagerMySession = ref(undefined);
    const publisherMySession = ref(undefined);
    const APPLICATION_SERVER_URL =
      process.env.NODE_ENV === 'production'
        ? `${import.meta.env.VITE_API_BASE_URL}`
        : `${import.meta.env.VITE_API_BASE_URL}`;

    // 계정 방 입장
    const enterMyRoom = async () => {
      return await createMyRoom();
    };

    const logoutSignal = async () => {
      const response = await axios.post(
        APPLICATION_SERVER_URL + 'user',
        { sign: 'logoutSignal' },
        {
          headers: { 'Content-Type': 'application/json' },
        },
      );
      console.log(response.data.result);
      return response.data.result;
    };
    // 로그인 시그널 친구들한테 보내기
    const loginSignal = async () => {
      const response = await axios.post(
        APPLICATION_SERVER_URL + 'user',
        { sign: 'loginSignal' },
        {
          headers: { 'Content-Type': 'application/json' },
        },
      );
      return response.data.result;
    };
    // 계정 방 생성
    const createMyRoom = async () => {
      console.log('loginUser : ', loginUserInfo.value.userId);
      const response = await axios.post(
        APPLICATION_SERVER_URL + 'room',
        { sign: 'enterMyroom', userId: loginUserInfo.value.userId },
        {
          headers: { 'Content-Type': 'application/json' },
        },
      );
      return response.data.result;
    };

    const loginSession = async () => {
      OVMy.value = new OpenVidu();
      // 전체 참여 세션
      mySession.value = OVMy.value.initSession();
      let token = await enterMyRoom();
      mySession.value
        .connect(token, loginUserInfo.value.userId)
        .then(() => {
          loginSignal();
        })
        .catch((error) => {
          console.log(
            '다음 세션에 로그인하는데 오류가 발생했습니다!:',
            error.code,
            error.message,
          );
        });
      // 시그널 처리 문
      mySession.value.on('streamCreated', ({ stream }) => {
        mySession.value.subscribe(stream);
      });

      mySession.value.on('signal:login', async (stream) => {
        // 로그인 시그널 수신
        console.log(stream.data, '님이 로그인했습니다.');
        friendStore.getLoginFriends(); // 친구가 로그인했다면 다시한번 레디스에서 읽어오기
        alert('친구가 로그인했어요!');

        await axios.post(
          // 로그인 콜백
          'https://i10a404.p.ssafy.io/openvidu/api/signal',
          {
            session: stream.data,
            type: 'signal:login-callBack',
            data: loginUserInfo.value.userId,
          },
          {
            headers: {
              'Content-Type': 'application/json',
              Authorization: 'Basic T1BFTlZJRFVBUFA6TVlfU0VDUkVU',
            },
            withCredentials: false,
          },
        );
      });

      mySession.value.on('signal:login-callBack', async (stream) => {
        console.log('[콜백] 친구 ', stream.data, '님이 로그인했습니다.');
        friendStore.getLoginFriends();
        // alert('콜백이 왔어요');
      });

      mySession.value.on('signal:logout', async (stream) => {
        // 로그인 시그널 수신
        console.log(stream.data, '님이 로그아웃 했습니다.');
        friendStore.getLoginFriends();
        alert('친구가 로그아웃했어요!');
      });

      mySession.value.on('exception', (exception) => {
        console.warn(exception);
      });

      const alarmStore = useAlarmStore();
      mySession.value.on('signal:alarm', async (stream) => {
        console.log(stream.data, 'tete');
        const data = JSON.parse(stream.data);
        console.log(data, 'tete');
        alarmStore.updateAlarm(data);
      });
    };

    //로그인 즉시 유저정보 저장
    const getLoginUserInfo = async function () {
      const body = {
        sign: 'getMyPage',
      };

      axios
        .post(`${import.meta.env.VITE_API_BASE_URL}user`, body, {
          headers: {
            'Content-Type': 'application/json',
          },
        })
        .then((res) => {
          console.log(res.data.result);
          loginUserInfo.value = res.data.result;
          loginUserInfo.value.sub = 'SQLD';

          userStore.$patch({ loginUserInfo: loginUserInfo.value });
        })
        .then(() => {
          login();
        });
    };

    const deleteLoginUserInfo = () => {
      loginUserInfo.value = {};
      cookiesStorage.setItem('userStore', '');

      if (mySession.value) {
        mySession.value.disconnect();
        logoutSignal();
      }
      location.reload();
    };
    onMounted(async () => {
      if (loginUserInfo.value.userId) {
        await login();
      }
    });
    const updateProfile = (imgURL) => {
      loginUserInfo.value.userPicture = imgURL;
      let userStore = JSON.parse(userCookiesStorage.getItem('userStore'));
      userStore.loginUserInfo = loginUserInfo.value;
      userCookiesStorage.setItem('userStore', JSON.stringify(userStore));
    };
    const updatePoint = (point) => {
      loginUserInfo.value.userPoint = point;
      let userStore = JSON.parse(userCookiesStorage.getItem('userStore'));
      userStore.loginUserInfo = loginUserInfo.value;
      userCookiesStorage.setItem('userStore', JSON.stringify(userStore));
    };
    return {
      APPLICATION_SERVER_URL,
      login,
      OVMy,
      mySession,
      mainStreamManagerMySession,
      publisherMySession,
      enterMyRoom,
      createMyRoom,
      loginSession,
      loginUserInfo,
      getLoginUserInfo,
      deleteLoginUserInfo,
      mySessionToken,
      studyRoomSessionToken,
      isInSession,
      achievementRate,
      updateProfile,
      updatePoint,
    };
  },

  //store를 localStorage에 저장하기 위해서(새로고침 시 데이터 날라감 방지)
  {
    enabled: true,
    persist: {
      storage: userCookiesStorage,
    },
    path: [
      'loginUserInfo',
      'mySessionToken',
      'studyRoomSessionToken',
      'achievementRate',
      'isInSession',
    ],
  },
);
