import React, {useState} from "react";
import {Form, Button, FormGroup, Input, Label, FormFeedback, FormText} from "reactstrap";
import {useNavigate} from "react-router-dom";
import authService from "../../services/auth.service";
import axios from 'axios';
import "./login.css";
import 'bootstrap/dist/css/bootstrap.min.css';

//const baseURL = "http://localhost:8080/api/auth";

const Login = (props) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [validPassword, setValidPassword] = useState(false);
    const navigate = useNavigate();

    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    }

    const handlePasswordChange = (event) => {
        validatePassword(event);
        setPasswordValue(event);
    }

    const validatePassword = (event) => {
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$/;

        if(passwordRegex.test(event.target.value)) {
            setValidPassword(true)
        } else {
            setValidPassword(false);
        }

    }

    const setPasswordValue = (event) => {
        setPassword(event.target.value);
    }

    const handleFormSubmit = (event) => {
        event.preventDefault();
        const requestBody = {
            "username": username,
            "password": password
        }

        axios.post('/auth/signin', requestBody).then(response => {
            console.log(response.data);
            authService.setUserSession(username, response.data.jwtToken);
            props.authenticate();
            navigate('/profile');
        }).catch(error => {
           // console.log('Error: ' + error.toJSON())
            console.log(error.message);
        })
    }

    return (

           <div>
               <div className={"signin"}>
                   <h4>Login</h4>
                   <Form className={"form"} onSubmit={handleFormSubmit}>
                       <FormGroup>
                           <Label htmlFor={"username"}>Username</Label>
                           <Input type={"text"} name={"username"} id={"username"} value={username}
                           onChange={handleUsernameChange}/>
                       </FormGroup>
                       <FormGroup>
                           <Label htmlFor={"password"}>Password</Label>
                           <Input type={"password"} name={"password"} id={"password"} value={password}
                           valid={validPassword}
                           invalid={validPassword}
                           onChange={handlePasswordChange}
                           />
                           <FormFeedback>
                               Uh oh! Looks like there is an issue with your password.
                           </FormFeedback>
                           <FormFeedback valid>
                               That's a tasty looking password you've got there.
                           </FormFeedback>
                           <FormText>Your password needs to be blah blah.</FormText>
                       </FormGroup>
                       <Button>Login</Button>
                   </Form>
           </div>

        </div>

    )

}

export default Login;