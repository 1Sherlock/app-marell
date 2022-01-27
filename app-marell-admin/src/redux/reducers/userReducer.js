import {USER_UPDATE_STATE} from "../types/authType";

const initialState = {
    isLoading: false,
    users: [],
    isModalVisible: false,
    selectedUser: null,
    selectedRole: null
    // page: 0,
    // size: 2,
    // search: "",
    // totalPages: 3
}

export const userReducer = (state = initialState, action) => {
    switch (action.type){
        case USER_UPDATE_STATE:
            return {
                ...state,
                ...action.payload
            }
        default:
            return state
    }
}