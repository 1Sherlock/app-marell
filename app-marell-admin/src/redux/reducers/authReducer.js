import {AUTH_UPDATE_STATE} from "../types/authType";

const initialState = {
    isLoading: false,
    me: {}
}

export const authReducer = (state = initialState, action) => {
    switch (action.type){
        case AUTH_UPDATE_STATE:
            return {
                ...state,
                ...action.payload
            }
        default:
            return state
    }
}