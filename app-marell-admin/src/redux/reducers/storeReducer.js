/**
 * Created by Sherlock on 04.09.2021.
 */
import {STORE_UPDATE_STATE} from "../types/authType";

const initialState = {
    isLoading: false,
    isModalVisible: false,
    categories: [],
    searchCategories: [],
    selectedId: null,
    isDeleteModalShow: false,
}

export const storeReducer = (state = initialState, action) => {
    switch (action.type){
        case STORE_UPDATE_STATE:
            return {
                ...state,
                ...action.payload
            }
        default:
            return state
    }
}