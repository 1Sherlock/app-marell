import {UPDATESTATE} from "../types/authType";

const initialState = {
    isLoading: false,
    me: {}
}

export const authReducer = (state = initialState, action) => {
    switch (action.type){
        case UPDATESTATE:
            return {
                ...state,
                ...action.payload
            }
        default:
            return state
    }
}