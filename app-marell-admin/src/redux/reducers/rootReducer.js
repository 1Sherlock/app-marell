/**
 * Created by Sherlock on 13.01.2022.
 */
import {combineReducers} from "redux";
import {authReducer} from "./authReducer";
import {userReducer} from "./userReducer";
import {storeReducer} from "./storeReducer";

export const rootReducer = combineReducers({
    auth: authReducer,
    user:userReducer,
    store: storeReducer
});