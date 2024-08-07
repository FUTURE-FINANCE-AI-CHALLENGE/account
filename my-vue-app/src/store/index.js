import { createStore } from 'vuex';
import axios from 'axios';

export const store = createStore({
    state() {
        return {
            isAuthenticated: false,
            user: null
        };
    },
    mutations: {
        setAuthenticated(state, isAuthenticated) {
            state.isAuthenticated = isAuthenticated;
        },
        setUser(state, user) {
            state.user = user;
        }
    },
    getters: {
        isAuthenticated(state) {
            return state.isAuthenticated;
        },
        user(state) {
            return state.user;
        }
    },
    actions: {
        async login({ commit }, { userId, password }) {
            try {
                // 로그인 요청 URL을 /users/login으로 수정
                const response = await axios.post('http://localhost:8080/users/login', { userId, password });

                if (response.status === 200) {
                    // 로그인 성공
                    commit('setAuthenticated', true);
                    commit('setUser', response.data); // 서버 응답 데이터가 사용자 정보라고 가정
                    return response.data; // 로그인 성공 시 데이터 반환
                } else {
                    // 로그인 실패
                    commit('setAuthenticated', false);
                    commit('setUser', null);
                    throw new Error('Invalid credentials');
                }
            } catch (error) {
                // 로그인 오류 처리
                commit('setAuthenticated', false);
                commit('setUser', null);
                throw error;
            }
        },
        logout({ commit }) {
            commit('setAuthenticated', false);
            commit('setUser', null);
        }
    }
});
