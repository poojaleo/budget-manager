import React from "react";
import AppNavbar from "./AppNavbar";

class Home extends React.Component {
    render() {
        return (
            <div>
                <AppNavbar/>
                <h2 style={{display: "flex", justifyContent: "center", alignItems: "center", height: "70vh"}}>
                    Welcome to your expense management application</h2>
            </div>
        )
    }
}

export default Home;