
/**
 * Created by Sherlock on 04.09.2021.
 */

import {STORE_UPDATE_STATE} from "../types/authType";
import axios from "axios";
import {API_PATH, CONFIG} from "../../tools/constants";
import {toast} from "react-toastify";




export function updateState(state) {
    return {
        type: STORE_UPDATE_STATE,
        payload: state
    }
}

export const save = (e, values) => (dispatch, getState) => {
    dispatch(updateState({isLoading: true}));

    axios.post(API_PATH + "category/save", {...values, id: getState().store.selectedCategory?.id}, CONFIG)
        .then(res => {
            if (res.data.success){
                toast.success(res.data.message);
                dispatch(getCategories(0));
                dispatch(updateState({isModalVisible: false, selectedCategory: null}));
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

export const getCategories = (page) => (dispatch) => {
    axios.get(API_PATH + "category")
        .then(res => {
            dispatch(updateState({
                categories: res.data.data?.map((item, index) => {
                    return {...item, index: (index + 1)}
                }),
                searchCategories: res.data.data?.map((item, index) => {
                    return {...item, index: (index + 1)}
                }),
                page: page
            }))
        })
}

export const deleteCategory = () => (dispatch, getState) => {
    dispatch(updateState({isLoading: true}));

    axios.delete(API_PATH + "category/" + getState().store.selectedId, CONFIG)
        .then(res => {
            toast.success(res.data.message);
            dispatch(getCategories(0));
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

