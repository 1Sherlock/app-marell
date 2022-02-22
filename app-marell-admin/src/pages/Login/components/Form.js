import { Form, Input, Button } from 'antd';
import { LockOutlined, PhoneOutlined} from '@ant-design/icons';
import {connect} from "react-redux";
import {login} from "../../../redux/actions/authAction";

const LoginForm = (props) => {
    return (
        <Form
            name="normal_login"
            className="login-form"
            initialValues={{ remember: true }}
            onFinish={(v) => props.login(v, props.history)}
            size="large"
        >
            <Form.Item
                name="phoneNumber"
                rules={[{ required: true, message: 'Пожалуйста, введите номер телефона!' }]}
            >
                <Input prefix={<PhoneOutlined className="site-form-item-icon" />} defaultValue="+998" placeholder="Номер телефона" />
            </Form.Item>
            <Form.Item
                name="password"
                rules={[{ required: true, message: 'Пожалуйста, введите пароль!' }]}
            >
                <Input
                    prefix={<LockOutlined className="site-form-item-icon" />}
                    type="password"
                    placeholder="Пароль"
                />
            </Form.Item>

            <Form.Item className="mb-0">
                <Button block type="primary" htmlType="submit" className="login-form-button">
                    Войти
                </Button>
            </Form.Item>
        </Form>
    );
};
