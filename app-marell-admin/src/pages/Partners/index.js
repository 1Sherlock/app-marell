/**
 * Created by Sherlock on 19.01.2022.
 */
import React, {useEffect, useRef} from 'react';
import {AvField, AvForm} from "availity-reactstrap-validation";
import {Button, Input, Modal, Table, Tabs} from "antd";
import {ArrowLeftOutlined, ArrowRightOutlined, SearchOutlined} from '@ant-design/icons';
import {connect} from "react-redux";
import {
    save,
    updateState,
    deletePartner,
    getPartners,
    uploadPhoto
} from "../../redux/actions/partnerAction";
import {API_PATH} from "../../tools/constants";

const {TabPane} = Tabs;
const Partners = (props) => {

    useEffect(() => {
        props.getPartners();
    }, [])

    let form = useRef();

    const showModal = () => {
        props.updateState({isModalVisible: !props.isModalVisible, selectedPartner: null, photo: null});
    };

    const handleOk = () => {
        form.submit();
    };

    const searchPartners = (e) => {
        props.updateState({searchPartners: props.partners.filter(item => item.name.toLowerCase().includes(e.target.value.toLowerCase()))})
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
            title: 'Категория',
            dataIndex: 'category',
            key: 'category'
        },
        {
            title: 'Фото',
            dataIndex: 'photo',
            key: 'photo',
            render: (text, record) => (
                <>
                    <img src={API_PATH + "file/get/" + record.photo?.id} alt="photo" width="100"/>
                </>
            )
        },
        {
            title: 'Информация',
            dataIndex: 'description',
            key: 'description'
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
                        <Button type='primary' ghost className='mr-2' onClick={() => props.updateState({
                            selectedPartner: record,
                            isModalVisible: true,
                            photo: record.photo?.id
                        })}>Изменить</Button>
                        <Button type='primary' danger onClick={() => props.updateState({
                            selectedId: record.id,
                            isDeleteModalShow: true
                        })}>Удалить</Button>
                </>
            )
        }
    ]

    const getBeautiful =(number) => number.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1 ");

    return (
        <div>
            <div className="d-flex justify-content-end">
                <Button type="primary" onClick={showModal} className="ml-2">Добавить партнер</Button>
            </div>

            <Modal title="Добавление партнера" destroyOnClose={true} visible={props.isModalVisible} onOk={handleOk}
                   onCancel={showModal} cancelText='Отменить' okText='Сохранить' confirmLoading={props.isLoading}>
                {props.isModalVisible ?
                    <AvForm
                        ref={(c) => form = c}
                        model={props.selectedPartner}
                        onValidSubmit={props.save}
                        autoComplete="off"
                    >
                        <div className="uploadPhoto">
                            {props.photo ?
                                <img src={API_PATH + "file/get/" + props.photo}      name="photo1" className="w-100 photo"/> :
                                <></>
                            }
                            <label htmlFor="file" className="text-center w-100 bg-secondary text-white my-3 rounded py-2" style={{cursor: "pointer"}}>
                                <img src="/assets/icons/camera.png" alt="camera.svg" className="camera  mr-3" style={{marginTop: "-5px"}}/>
                                Загрузить фото товара</label>
                        </div>

                        <input type="file" className="d-none"   id="file"
                               onChange={(e) => props.uploadPhoto(e.target.files[0])}/>

                        <AvField
                            label="Название партнера"
                            name="name"
                            required
                            type="text"
                        />
                        <AvField
                            label="Категория партнера"
                            name="category"
                            required
                            type="text"
                        />
                        <AvField
                            label="Информация о партнере"
                            name="description"
                            required
                            type="textarea"
                        />
                    </AvForm> : ""
                }
            </Modal>

            <Modal title="Удаление партнера" destroyOnClose={true} visible={props.isDeleteModalShow}
                   onOk={props.deletePartner} onCancel={() => props.updateState({isDeleteModalShow: false})}
                   cancelText='Отменить' okText="Удалить" confirmLoading={props.isLoading}>
                <h3>Хотите ли вы удалить?</h3>
            </Modal>

            <div className="card-container">
                <Tabs type="card" size="large">
                    <TabPane tab="Партнеры" key="1">
                        <Input type="text" className="w-25 mb-3" placeholder="Поиск" prefix={<SearchOutlined/>}
                               onChange={searchPartners}/>
                        <Table columns={columns} dataSource={props.searchPartners}/>
                    </TabPane>
                </Tabs>
            </div>
        </div>
    );
};


const mapStateToProps = (state) => {
    return {
        isModalVisible: state.partner.isModalVisible,
        isLoading: state.partner.isLoading,
        partners: state.partner.partners,
        searchPartners: state.partner.searchPartners,
        isDeleteModalShow: state.partner.isDeleteModalShow,
        selectedPartner: state.partner.selectedPartner,
        selectedId: state.partner.selectedId,
        photo: state.partner.photo,
        me: state.auth.me,
    }
}

export default connect(mapStateToProps, {
    save,
    updateState,
    getPartners,
    deletePartner,
    uploadPhoto
})(Partners);