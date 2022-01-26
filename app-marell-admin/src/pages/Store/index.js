/**
 * Created by Sherlock on 19.01.2022.
 */
import React, {useEffect, useRef} from 'react';
import {AvField, AvForm} from "availity-reactstrap-validation";
import {Button, Input, Modal, Table, Tabs} from "antd";
import {ArrowLeftOutlined, ArrowRightOutlined, SearchOutlined} from '@ant-design/icons';
import {connect} from "react-redux";
import {
    deleteCategory, deleteProduct,
    getCategories, getProducts,
    save,
    saveProduct,
    updateState,
    uploadPhoto
} from "../../redux/actions/storeAction";
import {API_PATH} from "../../tools/constants";


const {TabPane} = Tabs;

const AdminStore = (props) => {

    useEffect(() => {
        props.getCategories(0);
        props.getProducts(0);
    }, [])

    let form = useRef();
    let formProduct = useRef();

    const showModal = () => {
        props.updateState({isModalVisible: !props.isModalVisible, selectedCategory: null});
    };

    const showModalProduct = () => {
        props.updateState({isModalVisibleProduct: !props.isModalVisibleProduct, selectedProduct: null});
    };

    const handleOk = () => {
        form.submit();
    };

    const handleOkProduct = () => {
        formProduct.submit();
    };

    const searchCategory = (e) => {
        props.updateState({searchCategories: props.categories.filter(item => item.name.toLowerCase().includes(e.target.value.toLowerCase()))})
    }

    const searchProduct = (e) => {
        props.updateState({searchProducts: props.products.filter(item => item.name.toLowerCase().includes(e.target.value.toLowerCase()))})
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
                            <Button type='primary' ghost className='mr-2' onClick={() => props.updateState({
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
    const columnsProduct = [
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
            title: 'Информация',
            dataIndex: 'description',
            key: 'description'
        },
        {
            title: 'Цена (RUB/BTC)',
            dataIndex: 'price',
            key: 'price',
            render: (text, record) => (
                <>
                    {getBeautiful(record.price)} / {getBeautiful(record.btc)}
                </>
            )
        },
        {
            title: 'Количество',
            dataIndex: 'count',
            key: 'count'
        },
        {
            title: 'Категория / Тип',
            dataIndex: 'category',
            key: 'category',
            render: (text, record) => (
                <>
                    {record.category?.name} / {record.type === "MEN" ? "Мужской" : "Женский"}
                </>
            )
        },
        {
            title: 'Партнер',
            dataIndex: 'partner',
            key: 'partner'
        },
        {
            title: 'Действия',
            dataIndex: 'action',
            key: 'action',
            render: (text, record) => (
                <>
                    {/*{props.me.roles.filter(item => item.name === "ROLE_ADMIN").length > 0 ?*/}
                    <>
                        <Button type='primary' ghost className='mr-2' onClick={() => props.updateState({
                            selectedProduct: {...record, category: record.category?.id},
                            isModalVisibleProduct: true,
                            photo: record.photo?.id
                        })}>Изменить</Button>
                        <Button type='primary' danger onClick={() => props.updateState({
                            selectedIdProduct: record.id,
                            isDeleteModalShowProduct: true
                        })}>Удалить</Button>
                    </>
                    {/*: ""}*/}
                </>
            )
        }
    ]

    const getBeautiful =(number) => number.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1 ");

    return (
        <div>
            {/*{props.me.roles.filter(item => item.name === "ROLE_ADMIN").length > 0 ?*/}
            {/*    <>*/}
                    <div className="d-flex justify-content-end">
                        <Button type="primary" onClick={showModal} className="ml-2">Создать категорию</Button>
                        <Button type="primary" onClick={showModalProduct} className="ml-2">Создать товар</Button>
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

            <Modal title="Создание товара" destroyOnClose={true} visible={props.isModalVisibleProduct} onOk={handleOkProduct}
                   onCancel={showModalProduct} cancelText='Отменить' okText='Сохранить' confirmLoading={props.isLoading}>
                {props.isModalVisibleProduct ?
                    <AvForm
                        ref={(c) => formProduct = c}
                        model={props.selectedProduct}
                        onValidSubmit={props.saveProduct}
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
                            label="Название товара"
                            name="name"
                            required
                            type="text"
                        />
                        <AvField
                            label="Информация о товаре"
                            name="description"
                            required
                            type="textarea"
                        />
                        <AvField
                            label="Категория товара"
                            name="category"
                            required
                            type="select"
                        >
                            <option>Выберите</option>
                            {props.categories.map(item => (
                                <option value={item.id}>{item.name}</option>
                            ))}
                        </AvField>
                        <AvField
                            label="Количество товара"
                            name="count"
                            required
                            type="number"
                        />
                        <AvField
                            label="Цена товара (RUB)"
                            name="price"
                            required
                            type="number"
                        />
                        <AvField
                            label="Цена товара (BTC)"
                            name="btc"
                            required
                            type="number"
                        />
                        <AvField
                            label="Тип товара"
                            name="type"
                            required
                            type="select"
                        >
                            <option>Выберите</option>
                            <option value="MEN">Мужской</option>
                            <option value="WOMEN">Женский</option>
                        </AvField>

                    </AvForm> : ""
                }
            </Modal>

            <Modal title="Удаление категории" destroyOnClose={true} visible={props.isDeleteModalShow}
                   onOk={props.deleteCategory} onCancel={() => props.updateState({isDeleteModalShow: false})}
                   cancelText='Отменить' okText="Удалить" confirmLoading={props.isLoading}>
                <h3>Хотите ли вы удалить?</h3>
            </Modal>

            <Modal title="Удаление товара" destroyOnClose={true} visible={props.isDeleteModalShowProduct}
                   onOk={props.deleteProduct} onCancel={() => props.updateState({isDeleteModalShowProduct: false})}
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
                        <Input type="text" className="w-25 mb-3" placeholder="Поиск" prefix={<SearchOutlined/>}
                               onChange={searchProduct}/>
                        <Table columns={columnsProduct} dataSource={props.searchProducts}/>
                    </TabPane>
                </Tabs>
            </div>
        </div>
    );
};


const mapStateToProps = (state) => {
    return {
        isModalVisible: state.store.isModalVisible,
        isModalVisibleProduct: state.store.isModalVisibleProduct,
        isLoading: state.store.isLoading,
        categories: state.store.categories,
        products: state.store.products,
        searchProducts: state.store.searchProducts,
        searchCategories: state.store.searchCategories,
        isDeleteModalShow: state.store.isDeleteModalShow,
        isDeleteModalShowProduct: state.store.isDeleteModalShowProduct,
        selectedCategory: state.store.selectedCategory,
        selectedProduct: state.store.selectedProduct,
        selectedId: state.store.selectedId,
        selectedIdProduct: state.store.selectedIdProduct,
        photo: state.store.photo,
        me: state.auth.me,
    }
}

export default connect(mapStateToProps, {
    save,
    saveProduct,
    updateState,
    getCategories,
    getProducts,
    deleteCategory,
    deleteProduct,
    uploadPhoto
})(AdminStore);