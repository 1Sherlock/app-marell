import React from 'react';
import {BrowserRouter, Switch, Route} from "react-router-dom";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import Users from "./pages/Users";
import PrivateRoute from "./components/PrivateRoute";
import AdminLayout from "./components/Layout";
import {ToastContainer} from "react-toastify";

const App = () => {

    return (
        <BrowserRouter>
            <Switch>
                <Route path="/" exact component={Login}/>
                <PrivateRoute path="/admin"/>
            </Switch>

            <ToastContainer/>
        </BrowserRouter>
    );
};

export default App;