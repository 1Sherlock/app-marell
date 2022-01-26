
/**
 * Created by Sherlock on 04.09.2021.
 */

import {PARTNER_UPDATE_STATE} from "../types/authType";
import axios from "axios";
import {API_PATH, CONFIG} from "../../tools/constants";
import {toast} from "react-toastify";

export function updateState(state) {
    return {
        type: PARTNER_UPDATE_STATE,
        payload: state
    }
}

export const save = (e, values) => (dispatch, getState) => {
    dispatch(updateState({isLoading: true}));

    axios.post(API_PATH + "partner/save", {...values, id: getState().partner.selectedPartner?.id}, CONFIG)
        .then(res => {
            if (res.data.success){
                toast.success(res.data.message);
                dispatch(getPartners(0));
                dispatch(updateState({isModalVisible: false, selectedPartner: null}));
            }
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

export const getPartners = (page) => (dispatch) => {
    axios.get(API_PATH + "partner")
        .then(res => {
            dispatch(updateState({
                partners: res.data.data?.map((item, index) => {
                    return {...item, index: (index + 1)}
                }),
                searchPartners: res.data.data?.map((item, index) => {
                    return {...item, index: (index + 1)}
                }),
                page: page
            }))
        })
}

export const deletePartner = () => (dispatch, getState) => {
    dispatch(updateState({isLoading: true}));

    axios.delete(API_PATH + "partner/" + getState().partner.selectedId, CONFIG)
        .then(res => {
            toast.success(res.data.message);
            dispatch(getPartners(0));
            dispatch(updateState({isDeleteModalShow: false, selectedId: null}));
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