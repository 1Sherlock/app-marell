/**
 * Created by Sherlock on 04.09.2021.
 */
import {PARTNER_UPDATE_STATE} from "../types/authType";

const initialState = {
    isLoading: false,
    isModalVisible: false,
    partners: [],
    searchPartners: [],
    selectedId: null,
    isDeleteModalShow: false,
    selectedPartner: null,
}

export const partnerReducer = (state = initialState, action) => {
    switch (action.type){
        case PARTNER_UPDATE_STATE:
            return {
                ...state,
                ...action.payload
            }
        default:
            return state
    }
}