/**
 * Created by Sherlock on 13.01.2022.
 */
import React, {useEffect} from 'react';
import {connect} from "react-redux";
import {getUsers, updateState} from "../../redux/actions/userAction";
import {Table} from "antd";

const Users = (props) => {

    useEffect(() => {
        props.getUsers();
    }, [])



    const columns = [
        {
            title: 'Name',
            dataIndex: 'firstName',
            // render: name => `${name.first} ${name.last}`,
            // width: '20%',
        },
        {
            title: 'Gender',
            dataIndex: 'lastName',
        },
        {
            title: 'Email',
            dataIndex: 'email',
        },
    ];

    const getRandomuserParams = params => ({
        results: params.pagination.pageSize,
        page: params.pagination.current,
        ...params,
    });

    const handleTableChange = (pagination, filters, sorter) => {
        this.fetch({
            sortField: sorter.field,
            sortOrder: sorter.order,
            pagination,
            ...filters,
        });
    };

    console.log(props.totalPages)

    return (
        <div>
            <Table
                columns={columns}
                dataSource={props.users}
                loading={props.isLoading}
            />
        </div>
    );
};

const mapStateToProps = (state) => {
    return {
        users: state.user.users,
        // totalPages: state.user.totalPages,
        // page: state.user.page,
    }
}

export default connect(mapStateToProps, {getUsers, updateState})(Users);