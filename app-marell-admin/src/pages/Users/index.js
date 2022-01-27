/**
 * Created by Sherlock on 13.01.2022.
 */
import React, {useEffect, useRef} from 'react';
import {connect} from "react-redux";
import {getUsers, updateState, changeRole} from "../../redux/actions/userAction";
import {Button, Modal, Table} from "antd";
import {AvField, AvForm} from "availity-reactstrap-validation";
import {getPartners} from "../../redux/actions/partnerAction";

const Users = (props) => {

    useEffect(() => {
        props.getUsers();
        props.getPartners();
    }, [])

    let form = useRef();

    const showModal = () => {
        props.updateState({isModalVisible: !props.isModalVisible, selectedUser: null, selectedRole: null});
    };

    const handleOk = () => {
        form.submit();
    };

    const columns = [
        {
            title: 'Name',
            dataIndex: 'firstName',
        },
        {
            title: 'Gender',
            dataIndex: 'lastName',
        },
        {
            title: 'Email',
            dataIndex: 'email',
        },
        {
            title: 'Login',
            dataIndex: 'login',
        },
        {
            title: 'Phone number',
            dataIndex: 'phoneNumber'
        },
        {
            title: "Roles",
            dataIndex: 'role',
            key: 'role',
            render: (text, record) => (
                <>
                    {record.roles?.map(item => item.description).join(', ')}
                </>
            )
        },
        {
            title: 'Partner',
            dataIndex: 'partnerName'
        },
        {
            title: 'Action',
            dataIndex: 'action',
            key: 'action',
            render: (text, record) => (
                <>
                    {record.roles.length === 1 ?
                        <Button type='primary' ghost className='mr-2' onClick={() => props.updateState({
                            selectedUser: record,
                            isModalVisible: true,
                            selectedRole: record.roles[0].name
                        })}>Изменить Роль</Button> : ""
                    }
                </>
            )
        }
    ];

    return (
        <div>
            <Table
                columns={columns}
                dataSource={props.users}
                loading={props.isLoading}
            />

            <Modal title="Изменение роли" destroyOnClose={true} visible={props.isModalVisible} onOk={handleOk}
                   onCancel={showModal} cancelText='Отменить' okText='Сохранить' confirmLoading={props.isLoading}>
                {props.isModalVisible ?
                    <AvForm
                        ref={(c) => form = c}
                        model={{role: props.selectedUser?.roles[0].name}}
                        onValidSubmit={props.changeRole}
                        autoComplete="off"
                    >
                        <AvField
                            label="Роль"
                            name="role"
                            required
                            type="select"
                            onChange={(e) => props.updateState({selectedRole: e.target.value})}
                        >
                            <option value="ROLE_USER">Пользователь</option>
                            <option value="ROLE_PARTNER">Партнер</option>
                            <option value="ROLE_ADMIN">Администратор</option>
                        </AvField>

                        {props.selectedRole === "ROLE_PARTNER" &&
                        <AvField
                            label="Партнер"
                            name="partner"
                            required
                            type="select"
                            value={props.selectedUser.partnerId}
                        >
                            <option>Выберите</option>
                            {props.partners.map(item => (
                                <option value={item.id}>{item.name}</option>
                            ))}
                        </AvField>
                        }

                    </AvForm> : ""
                }
            </Modal>
        </div>
    );
};

const mapStateToProps = (state) => {
    return {
        users: state.user.users,
        isModalVisible: state.user.isModalVisible,
        selectedUser: state.user.selectedUser,
        selectedRole: state.user.selectedRole,
        isLoading: state.user.isLoading,
        partners: state.partner.partners
    }
}

export default connect(mapStateToProps, {getUsers, updateState, getPartners, changeRole})(Users);