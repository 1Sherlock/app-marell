import {USER_UPDATE_STATE} from "../types/authType";
import axios from "axios";
import {API_PATH, CONFIG} from "../../tools/constants";

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