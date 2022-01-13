import React from 'react';
import Form from "./components/Form";

const Login = (props) => {
    return (
        <div className="container vh-100">
            <div className="row vh-100 align-items-center">
                <div className="col-md-4 offset-md-4">
                  <div className="card">
                      <div className="card-body">
                          <Form history={props.history}/>
                      </div>
                  </div>
                </div>
            </div>
        </div>
    );
};

export default Login;