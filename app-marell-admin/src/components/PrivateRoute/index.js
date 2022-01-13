/**
 * Created by Sherlock on 13.01.2022.
 */
import React from 'react';
import {Route} from "react-router-dom";
import AdminLayout from "../Layout";

const PrivateRoute = (props) => {
    return (
        <Route {...props} component={AdminLayout}/>
    );
};

export default PrivateRoute;