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
                const response = await axios.post('http://localhost:8080/users/login', { userId, password });

                if (response.status === 200) {
                    commit('setAuthenticated', true);
                    commit('setUser', response.data);
                    return response.data;
                } else {
                    commit('setAuthenticated', false);
                    commit('setUser', null);
                    throw new Error('Invalid credentials');
                }
            } catch (error) {
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
