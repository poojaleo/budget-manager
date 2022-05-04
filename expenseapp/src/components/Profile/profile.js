import React, {useState, useEffect} from "react";
import {Form, Button, FormGroup, Input, Label, FormFeedback, FormText, Container} from "reactstrap";
import AuthService from "../../services/auth.service";
import axios from "axios";
import CategoryButton from "../Category/CategoryButton";
import ExpenseButton from "../Expense/ExpenseButton";
import "./profile.css";


const Profile = (props) => {
    const [username, setUsername] = useState('');
    const [token, setToken] = useState('');
    const [email, setEmailAddress] = useState('');
    const [fullName, setFullName] = useState('');
    const [monthlyBudget, setMonthlyBudget] = useState('');
    const [monthlyIncome, setMonthlyIncome] = useState('');

    useEffect(() => {
        tokenAuthState();
    },[]);

    const tokenAuthState = () => {
        const token = AuthService.getToken();
        const username = AuthService.getCurrentUser();

        setToken(token);
        setUsername(username);

        const requestHeader = {
            headers : {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            }
        }

        const url = `/${username}`;


        axios.get(url, requestHeader).then(response => {
            console.log(response.data);
            setEmailAddress(response.data.emailAddress);
            setFullName(response.data.fullName);
            setMonthlyIncome(response.data.monthlyIncome);
            setMonthlyBudget(response.data.monthlyBudget);
            AuthService.setTotalBudget(response.data.monthlyBudget);
        }).catch(error => {
            // console.log('Error: ' + error.toJSON())
            console.log(error.message);
        })

    }

    const handleFormSubmit = (event) => {
        event.preventDefault();

        const requestHeader = {
            headers : {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            }
        }

        const url = `/${username}`;

        const requestBody = {
            "emailAddress": email,
            "fullName": fullName,
            "monthlyIncome": monthlyIncome,
            "monthlyBudget": monthlyBudget
        }

        axios.put(url, requestBody, requestHeader).then(response => {
            console.log(response.data);
        }).catch(error => {
            console.log(error.message);
        })

    }


    const handleFullNameChange = (event) => {
        event.preventDefault();
        setFullName(event.target.value);
    }

    const handleIncomeChange = (event) => {
        event.preventDefault();
        setMonthlyIncome(event.target.value);
    }

    const handleBudgetChange = (event) => {
        event.preventDefault();
        setMonthlyBudget(event.target.value);
    }




    return (
        <Container className="mt-5 mx-auto">
            <div className={"profileHeader"}>
                <h2>Welcome to Expense Tracker App</h2>
                <div className={"buttons"}>
                   <div className={"manage-buttons"}>
                       <CategoryButton />
                   </div>
                    <div className={"manage-buttons"}>
                        <ExpenseButton />
                    </div>
                </div>
            </div>
            <div className={"px-4"}>
                <h4>Profile Page</h4>
                <Form className={"form"} onSubmit={handleFormSubmit}>
                    <FormGroup className={"col-xl-4"}>
                        <Label htmlFor={"username"}>Username</Label>
                        <Input type={"text"} name={"username"} id={"username"} value={username} readOnly={true}/>
                    </FormGroup>
                    <FormGroup className={"col-xl-4"}>
                        <Label htmlFor={"email"}>Email Address</Label>
                        <Input type={"email"} name={"email"} id={"email"} value={email} readOnly={true}/>
                    </FormGroup>
                    <FormGroup className={"col-xl-4"}>
                        <Label htmlFor={"fullName"}>Full Name</Label>
                        <Input type={"text"} name={"fullName"} id={"fullName"} defaultValue={fullName}
                               onChange={handleFullNameChange} />
                    </FormGroup>
                    <FormGroup className={"col-xl-4"}>
                        <Label htmlFor={"income"}>Monthly Income</Label>
                        <Input type={"number"} name={"income"} id={"income"} defaultValue={monthlyIncome}
                               onChange={handleIncomeChange} />
                    </FormGroup>
                    <FormGroup className={"col-xl-4"}>
                        <Label htmlFor={"budget"}>Monthly Budget</Label>
                        <Input type={"number"} name={"budget"} id={"budget"} defaultValue={monthlyBudget}
                               onChange={event => setMonthlyBudget(event.target.value)} />
                    </FormGroup>
                    <Button type={"submit"}>Update Profile</Button>
                </Form>
            </div>

        </Container>
    )
}

export default Profile;