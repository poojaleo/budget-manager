import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import LoginButton from "../NavBar/LoginButton";
import {Button, Form, FormFeedback, FormGroup, FormText, Input, Label} from "reactstrap";
import SignupButton from "../NavBar/SignupButton";

const Signup = (props) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const [validPassword, setValidPassword] = useState(false);
    const [validEmail, setValidEmail] = useState(false);
    const [signupSuccessful, setSignup] = useState(false);
    const navigate = useNavigate();

    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    }

    const handlePasswordChange = (event) => {
        validatePassword(event);
        setPasswordValue(event);
    }

    const validatePassword = (event) => {
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

        if(passwordRegex.test(event.target.value)) {
            setValidPassword(true);
        } else {
            setValidPassword(false);
        }

    }

    const setPasswordValue = (event) => {
        setPassword(event.target.value);
    }

    const handleEmailChange = (event) => {
        validateEmail(event);
        setEmailValue(event);
    }

    const validateEmail = (event) => {
        const emailRegex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

        if(emailRegex.test(event.target.value)) {
            setValidEmail(true);
        } else {
            setValidEmail(false);
        }
    }

    const setEmailValue = (event) => {
        setEmail(event.target.value);
    }


    const successful = () => {

        return (
            <div>
                {signupSuccessful ? (
                    <div>
                        <h3>{username} successfully registered!!</h3>
                        <LoginButton />
                    </div>
                    ) :
                    (
                        <h3>Please try again!!</h3>
                    )}

            </div>
        )
    }



    const handleFormSubmit = (event) => {
        event.preventDefault();
        const requestBody = {
            "username": username,
            "password": password,
            "emailAddress": email
        }

        axios.post('/auth/signup', requestBody).then(response => {
            console.log(response.data);
            setSignup(true);
        }).catch(error => {
            // console.log('Error: ' + error.toJSON())
            setSignup(false);
            console.log(error.message);
        })
    }

    return (
        <div>
            <div className={"signin"}>
                <h4>Register</h4>
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
                               invalid={!validPassword}
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
                    <FormGroup>
                        <Label htmlFor={"email"}>Email</Label>
                        <Input type={"email"} name={"email"} id={"email"} value={email}
                               valid={validEmail}
                               invalid={!validEmail}
                               onChange={handleEmailChange}
                        />
                        <FormFeedback>
                            Uh oh! Looks like there is an issue with your Email!
                        </FormFeedback>
                        <FormFeedback valid>
                            That's a tasty looking email you've got there.
                        </FormFeedback>
                    </FormGroup>
                    <Button>Signup</Button>
                </Form>
                <div className={"noAccount"}>
                    <h6>Already have an account. <LoginButton />
                    </h6>
                </div>
            </div>
            <div>
            {signupSuccessful ? (
                    <div>
                        <h4>{username} successfully registered!!</h4>
                        <LoginButton />
                    </div>
                ) :
                (
                    <h3></h3>
                )}

        </div>


        </div>
    )
}

export default Signup;