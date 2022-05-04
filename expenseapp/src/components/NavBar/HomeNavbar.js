import React from "react";
import {Navbar, Container, Nav, NavLink} from "reactstrap";
import "./HomeNavbar.css";
import LoginButton from "./LoginButton";
import SignupButton from "./SignupButton";

class HomeNavbar extends React.Component {
    render() {
        return (
            <div>
                <LoginButton />
                <SignupButton />
            </div>

        )
    }
}

export default HomeNavbar;