import React, {useEffect, useState} from "react";
import {Route, Routes, Switch} from "react-router-dom";
import Home from "./components/Profile/home";
import Login from "./components/Profile/login";
import Signup from "./components/Profile/signup";
import Profile from "./components/Profile/profile";
import AuthService from "./services/auth.service";

function App() {

    const [isAuthentication, setAuthentication] = useState(null);
    const [isTokenSet, setToken] = useState(AuthService.getToken);

    useEffect(() => {
        tokenAuthState();
    })

    const tokenAuthState = () => {
        const token = AuthService.getToken();
        const requestBody = {
            "username": AuthService.getCurrentUser(),
            "jwtToken": token
        }

        if(token == undefined || token == null) {
            setAuthentication(false);
            setToken(token);
        } else {
            setToken(token);
            setAuthentication(true);
        }
    }


    return (
        <div>
            <Routes>
                <Route path={"/"} element={<Home/>} />
                <Route path={"/home"} element={<Home />} />
                <Route path={"/login"} element={<Login authenticate = {() => tokenAuthState()} />} />
                <Route path={"/signup"} element={<Signup />} />
                <Route path={"/profile"} element={<Profile />} />
            </Routes>

        </div>
    )

}

export default App;