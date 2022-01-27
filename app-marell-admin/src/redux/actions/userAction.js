import {USER_UPDATE_STATE} from "../types/authType";
import axios from "axios";
import {API_PATH, CONFIG} from "../../tools/constants";
import {toast} from "react-toastify";

export function updateState(state) {
    return {
        type: USER_UPDATE_STATE,
        payload: state
    }
}

export const getUsers = (page, size, search) => (dispatch) => {
    // axios.get(API_PATH + "user/getUsers?page=" + page + "&size=" + size + "&search=" +search, CONFIG)
    axios.get(API_PATH + "user/getUsers", CONFIG)
        .then(res => {
            dispatch(updateState({users: res.data.data}))
        })
}

export const changeRole = (e, v) => (dispatch, getState) => {
    dispatch(updateState({isLoading: true}))

    axios.post(API_PATH + "user/changeRole", {...v,userId: getState().user.selectedUser.id, partner: (v.role === "ROLE_ADMIN" ? getState().partner.partners.filter(item => item.name === "ADMIN")[0].id : v.partner)}, CONFIG)
        .then(res => {
            toast.success(res.data.message);
            dispatch(updateState({selectedRole: null, selectedUser: null, isModalVisible: false}))
            dispatch(getUsers());
        })
        .catch((err) => {
            if (err.response){
                toast.error(err.response.data.message)
            }
        })
        .finally(() => {
            dispatch(updateState({isLoading: false}))
        })
}