import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import authService from "../../services/auth.service";

const Signup = (props) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const navigate = useNavigate();
    let form = "";

    const required = value => {
        if (!value) {
            return (
                <div className="alert alert-danger" role="alert">
                    This field is required!
                </div>
            );
        }
    };

    const successful = (username) => {
        return (
            <div>
                <h3>{username} successfully registered!!</h3>
            </div>
        )
    }

    const handleFormSubmit = (event) => {
        event.preventDefault();
        form.validateAll();
        const requestBody = {
            "username": username,
            "password": password,
            "email": email
        }

        axios.post('/auth/signup', requestBody).then(response => {
            console.log(response.data);
            successful(response.data.username);
            navigate('/login');
        }).catch(error => {
            // console.log('Error: ' + error.toJSON())
            console.log(error.message);
        })
    }

    return (
        <div>

        </div>
    )
}

export default Signup;