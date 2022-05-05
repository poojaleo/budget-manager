import React, {useEffect, useState} from "react";
import {Route, Routes, Switch} from "react-router-dom";
import Home from "./components/Profile/home";
import Login from "./components/Profile/login";
import Signup from "./components/Profile/signup";
import Profile from "./components/Profile/profile";
import Category from "./components/Category/Category";
import AuthService from "./services/auth.service";
import 'bootstrap/dist/css/bootstrap.min.css';
import Expense from "./components/Expense/Expense";

function App() {

    const [isAuthentication, setAuthentication] = useState(null);
    const [isTokenSet, setToken] = useState(AuthService.getToken);

    useEffect(() => {
        tokenAuthState();
    })

    const tokenAuthState = () => {
        const token = AuthService.getToken();

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
                {isTokenSet && (
                    <>
                        <Route path={"/"} element={<Home/>} />
                        <Route path={"/home"} element={<Home />} />
                        <Route path={"/login"} element={<Login authenticate = {() => tokenAuthState()} />} />
                        <Route path={"/signup"} element={<Signup />} />
                        <Route path={"/profile"} element={<Profile />} />
                        <Route path={"/categories"} element={<Category />} />
                        <Route path={"/expenses"} element={<Expense />} />
                    </>
                )}

                {!isTokenSet && (
                    <>
                        <Route path={"/"} element={<Home/>} />
                        <Route path={"/home"} element={<Home />} />
                        <Route path={"/login"} element={<Login authenticate = {() => tokenAuthState()} />} />
                        <Route path={"/signup"} element={<Signup />} />
                        <Route path={"/profile"} element={<Home />} />
                        <Route path={"/categories"} element={<Home />} />
                        <Route path={"/expenses"} element={<Home />} />
                    </>
                )}
            </Routes>

        </div>
    )

}

export default App;