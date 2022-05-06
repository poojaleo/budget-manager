import React from "react";
import "./HomeNavbar.css";
import LoginButton from "./LoginButton";
import SignupButton from "./SignupButton";

class HomeNavbar extends React.Component {
    render() {
        return (
            <div className={"d-flex flex-row me-2 p-3 justify-content-end"}>
                <div className={"me-2 p-2"}>
                    <LoginButton />
                </div>
               <div className={"me-2 p-2"}>
                   <SignupButton />
               </div>
            </div>

        )
    }
}

export default HomeNavbar;