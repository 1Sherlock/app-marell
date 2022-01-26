/**
 * Created by Sherlock on 13.01.2022.
 */
import React, {useState} from 'react';

import {Layout, Menu, Breadcrumb} from 'antd';
import {
    DesktopOutlined,
    PieChartOutlined,
    FileOutlined,
    TeamOutlined,
    UserOutlined,
    AppstoreOutlined,
    ApartmentOutlined
} from '@ant-design/icons';
import "./index.scss";
import {Switch, Route, Link} from "react-router-dom";
import Dashboard from "../../pages/Dashboard";
import Users from "../../pages/Users";
import AdminStore from "../../pages/Store";
import Partners from "../../pages/Partners";

const {Header, Content, Footer, Sider} = Layout;
const {SubMenu} = Menu;

const AdminLayout = (props) => {
    console.log(props.history.location.pathname.split("/"))

    const [collapsed, setCollapsed] = useState(false);

    return (
        <Layout style={{minHeight: '100vh'}}>
            <Sider collapsible collapsed={collapsed} onCollapse={() => setCollapsed(!collapsed)}>
                <div className="layout-logo">
                    <a href="#" target="_blank"><img src="/assets/icons/logo.svg" alt="logo.svg"/></a>
                </div>
                <Menu theme="dark" defaultSelectedKeys={[props.history.location.pathname]} mode="inline">
                    <Menu.Item key="/admin/dashboard" icon={<PieChartOutlined/>}>
                        <Link to="/admin/dashboard" className="text-decoration-none">Дашбоард</Link>
                    </Menu.Item>
                    <Menu.Item key="/admin/users" icon={<TeamOutlined/>}>
                        <Link to="/admin/users" className="text-decoration-none">Пользователи</Link>
                    </Menu.Item>
                    <Menu.Item key="/admin/store" icon={<AppstoreOutlined/>}>
                        <Link to="/admin/store" className="text-decoration-none">Хранилише</Link>
                    </Menu.Item>
                    <Menu.Item key="/admin/partners" icon={<ApartmentOutlined />}>
                        <Link to="/admin/partners" className="text-decoration-none">Партнеры</Link>
                    </Menu.Item>
                </Menu>
            </Sider>
            <Layout className="site-layout">
                <Header className="site-layout-background" style={{padding: 0}}/>
                <Content style={{margin: '0 16px'}}>
                    <Breadcrumb style={{margin: '16px 0'}}>
                        {props.history.location.pathname.split("/").filter(item => item.length > 0).map(item => (
                            <Breadcrumb.Item>{item}</Breadcrumb.Item>
                        ))}
                    </Breadcrumb>
                    <div className="site-layout-background" style={{padding: 24, minHeight: 360}}>
                        <Switch>
                            <Route path="/admin/dashboard" exact component={Dashboard}/>
                            <Route path="/admin/users" exact component={Users}/>
                            <Route path="/admin/store" exact component={AdminStore}/>
                            <Route path="/admin/partners" exact component={Partners}/>
                        </Switch>
                    </div>
                </Content>
                <Footer style={{textAlign: 'center'}}>Marell Admin ©2022 Created by <a href="https://cosmos-soft.uz"
                                                                                       target="_blank">Cosmos
                    Soft</a></Footer>
            </Layout>
        </Layout>
    );
};

export default AdminLayout;