import React, {useState} from "react";
import {Form, Button, FormGroup, Input, Label, FormFeedback, FormText} from "reactstrap";
import {useNavigate} from "react-router-dom";
import authService from "../../services/auth.service";
import axios from 'axios';
import "./login.css";
import SignupButton from "../NavBar/SignupButton";

//const baseURL = "http://exp-tracker-alb-1157495979.us-west-2.elb.amazonaws.com/api";

const Login = (props) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [validPassword, setValidPassword] = useState(false);
    const [message, setMessage] = useState('');
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

        const requestHeader = {
            headers : {
                "Access-Control-Allow-Headers" : "Content-Type",
                "Access-Control-Allow-Origin": "https://main.d1hpmgnet94wqd.amplifyapp.com",
                "Access-Control-Allow-Methods": "OPTIONS,POST,GET,PUT,DELETE"
            }
        }


        //const url = `${baseURL}/auth/signin`;
        const url = `auth/signin`;

        axios.post(url, requestBody, requestHeader).then(response => {
            console.log(response.data);
            authService.setUserSession(username, response.data.jwtToken);
            props.authenticate();
            navigate('/profile');
        }).catch(error => {
           // console.log('Error: ' + error.toJSON())
            console.log(error.message);
            setMessage("Looks like there is an issue with your username or password. " +
                "Please enter valid credentials");
        })
    }

    return (

           <div>
               <div className={"signin"}>
                   <h4>Welcome Back!</h4>
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
                           <FormText>Your password needs to be minimum 8 characters, 1 uppercase, 1 lowercase and 1 special character.</FormText>
                       </FormGroup>
                       <Button>Login</Button>
                   </Form>
                   <div>
                       {message}
                   </div>
                   <div className={"noAccount"}>
                       <h6>Do not have an account. <SignupButton />
                       </h6>
                   </div>
           </div>

        </div>

    )

}

export default Login;