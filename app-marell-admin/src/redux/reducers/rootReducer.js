/**
 * Created by Sherlock on 13.01.2022.
 */
import {combineReducers} from "redux";
import {authReducer} from "./authReducer";

export const rootReducer = combineReducers({
    auth: authReducer
});