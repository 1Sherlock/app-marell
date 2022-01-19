/**
 * Created by Sherlock on 19.01.2022.
 */
import React, {useEffect, useRef} from 'react';
import {AvField, AvForm} from "availity-reactstrap-validation";
import {Button, Input, Modal, Table, Tabs} from "antd";
import {ArrowLeftOutlined, ArrowRightOutlined, SearchOutlined} from '@ant-design/icons';
import {connect} from "react-redux";
import {deleteCategory, getCategories, save, updateState} from "../../redux/actions/storeAction";


const {TabPane} = Tabs;

const AdminStore = (props) => {

    useEffect(() => {
        props.getCategories(0);
    }, [])

    let form = useRef();

    const showModal = () => {
        props.updateState({isModalVisible: !props.isModalVisible, selectedCategory: null});
    };

    const handleOk = () => {
        form.submit();
    };

    const searchCategory = (e) => {
        props.updateState({searchCategories: props.categories.filter(item => item.name.toLowerCase().includes(e.target.value.toLowerCase()))})
    }

    const columns = [
        {
            title: '№',
            dataIndex: 'index',
            key: 'index'
        },
        {
            title: 'Название',
            dataIndex: 'name',
            key: 'name'
        },
        {
            title: 'Время создание',
            dataIndex: 'createdAt',
            key: 'createdAt'
        },
        {
            title: 'Действия',
            dataIndex: 'action',
            key: 'action',
            render: (text, record) => (
                <>
                    {/*{props.me.roles.filter(item => item.name === "ROLE_ADMIN").length > 0 ?*/}
                        <>
                            <Button type='primary' ghost className='me-2' onClick={() => props.updateState({
                                selectedCategory: record,
                                isModalVisible: true
                            })}>Изменить</Button>
                            <Button type='primary' danger onClick={() => props.updateState({
                                selectedId: record.id,
                                isDeleteModalShow: true
                            })}>Удалить</Button>
                        </>
                        {/*: ""}*/}
                </>
            )
        }
    ]

    return (
        <div>
            {/*{props.me.roles.filter(item => item.name === "ROLE_ADMIN").length > 0 ?*/}
            {/*    <>*/}
                    <div className="d-flex justify-content-end">
                        <Button type="primary" onClick={showModal} className="ml-2">Создать категорию</Button>
                    </div>
                {/*</> : ""*/}
            {/*}*/}

            <Modal title="Создание категории" destroyOnClose={true} visible={props.isModalVisible} onOk={handleOk}
                   onCancel={showModal} cancelText='Отменить' okText='Сохранить' confirmLoading={props.isLoading}>
                {props.isModalVisible ?
                    <AvForm
                        ref={(c) => form = c}
                        model={props.selectedCategory}
                        onValidSubmit={props.save}
                        autoComplete="off"
                    >

                        <AvField
                            label="Название категории"
                            name="name"
                            required
                            type="text"
                        />
                    </AvForm> : ""
                }
            </Modal>

            <Modal title="Удаление категории" destroyOnClose={true} visible={props.isDeleteModalShow}
                   onOk={props.deleteCategory} onCancel={() => props.updateState({isDeleteModalShow: false})}
                   cancelText='Отменить' okText="Удалить" confirmLoading={props.isLoading}>
                <h3>Хотите ли вы удалить?</h3>
            </Modal>


            <div className="card-container">
                <Tabs type="card" size="large">
                    <TabPane tab="Категории товаров" key="1">
                        <Input type="text" className="w-25 mb-3" placeholder="Поиск" prefix={<SearchOutlined/>}
                               onChange={searchCategory}/>
                        <Table columns={columns} dataSource={props.searchCategories}/>
                    </TabPane>
                    <TabPane tab="Товары" key="2">

                    </TabPane>
                </Tabs>
            </div>
        </div>
    );
};


const mapStateToProps = (state) => {
    return {
        isModalVisible: state.store.isModalVisible,
        isLoading: state.store.isLoading,
        categories: state.store.categories,
        searchCategories: state.store.searchCategories,
        isDeleteModalShow: state.store.isDeleteModalShow,
        selectedCategory: state.store.selectedCategory,
        selectedId: state.store.selectedId,
        me: state.auth.me,
    }
}

export default connect(mapStateToProps, {
    save,
    updateState,
    getCategories,
    deleteCategory,
})(AdminStore);