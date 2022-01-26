
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

export const saveProduct = (e, values) => (dispatch, getState) => {
    if (getState().store.photo){

        dispatch(updateState({isLoading: true}));

        axios.post(API_PATH + "product/save", {...values, id: getState().store.selectedProduct?.id, photo: getState().store.photo}, CONFIG)
            .then(res => {
                if (res.data.success){
                    toast.success(res.data.message);
                    dispatch(getProducts(0));
                    dispatch(updateState({isModalVisibleProduct: false, selectedProduct: null, photo: null}));
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
    } else {
        toast.error("Выберите фото товара")
    }
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


export const getProducts = (page) => (dispatch) => {
    axios.get(API_PATH + "product/all", CONFIG)
        .then(res => {
            dispatch(updateState({
                products: res.data.data?.map((item, index) => {
                    return {...item, index: (index + 1)}
                }),
                searchProducts: res.data.data?.map((item, index) => {
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

export const deleteProduct = () => (dispatch, getState) => {
    dispatch(updateState({isLoading: true}));

    axios.delete(API_PATH + "product/" + getState().store.selectedIdProduct, CONFIG)
        .then(res => {
            toast.success(res.data.message);
            dispatch(getProducts(0));
            dispatch(updateState({isDeleteModalShowProduct: false, selectedIdProduct: null}));
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

export const uploadPhoto = (file) => (dispatch) => {
    const data = new FormData();
    data.append("file", file);
    axios.post(API_PATH + "file/save", data)
        .then((res) => {
            dispatch(updateState({photo: res.data.id}));
        })
}

