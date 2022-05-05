import React from "react";
import HomeNavbar from "../NavBar/HomeNavbar";
import './home.css';
import accountImage from './accounts.png';
import {Container} from "react-bootstrap";


class Home extends React.Component {

    render() {
        return (
            <Container className={"home"}>
                <div className={"aboutus"}>
                    <div className={"d-flex flex-row justify-content-around"}>
                        <h1>TRACK BUDGET</h1>
                        <HomeNavbar />
                    </div>


                    <div className={"info"}>
                        <div className={"d-flex flex-row infosection"}>
                            <div>
                                <h4>
                                    Simple way to manage personal finances</h4>
                                <p>Track Budget breaks down your expenses and creates visualizations based on categories such as Food, Shopping, Gift etc.
                                    to track your expenses</p>
                                <p> It also helps you to improve your spending habits with custom goals that keep you going. </p>
                            </div>
                            <div className={"homeImage"}>
                                <img src={accountImage} alt={"home page icon"}/>
                            </div>
                        </div>
                    </div>
                </div>
            </Container>
        )
    }
}

export default Home;