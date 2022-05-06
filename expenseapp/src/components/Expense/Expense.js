import React, {useState, useEffect} from "react";
import AuthService from "../../services/auth.service";
import axios from "axios";
import {Container, Stack} from "react-bootstrap";
import ExpenseCard from "./ExpenseCard";
import ProfileButton from "../Profile/ProfileButton";
import CategoryButton from "../Category/CategoryButton";
import {Button} from "reactstrap";
import CreateExpenseModal from "./CreateExpenseModal";
import ViewExpenseModal from "./ViewExpenseModal";
import SignoutButton from "../NavBar/SignoutButton";

const Expense = (props) => {
    const [username, setUsername] = useState('');
    const [allExpenses, setAllExpenses] = useState([]);
    const [budget, setBudget] = useState('');
    const [allCategories, setAllCategories] = useState([]);
    const [expenseGroupByCategory, setExpenseGroupByCategory] = useState({});
    const [createExpenseModal, setCreateExpenseModal] = useState(false)
    const [viewExpenseModal, setViewExpenseModal] = useState(false);
    const [viewCategoryName, setViewCategoryName] = useState(false);
    const [dropdownCategories, setDropdownCategories] = useState([]);

    const baseURL = "http://exp-tracker-alb-1157495979.us-west-2.elb.amazonaws.com/api";

    useEffect(() => {
        getAllExpenses();
        getAllCategories();
    },[]);

    const getAllExpenses = () => {
        const token = AuthService.getToken();
        const username = AuthService.getCurrentUser();
        const budget = AuthService.getTotalBudget();
        setUsername(username);
        setBudget(budget);

        const requestHeader = {
            headers : {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            }
        }

        const url = `${baseURL}/${username}/expense`;

        axios.get(url, requestHeader).then(response => {
            console.log(response.data);
            setAllExpenses(response.data);
            return response.data
        }).catch(error => {
            console.log(error.message);
        }).then(response => response.reduce((acc,d) => {
            if(Object.keys(acc).includes(d.categoryName)) return acc;
            acc[d.categoryName] = response.filter(name => name.categoryName === d.categoryName);
            return acc;
        }, {})).then(result => setExpenseGroupByCategory(result));

    }

    const getAllCategories = () => {
        const token = AuthService.getToken();
        const username = AuthService.getCurrentUser();

        const requestHeader = {
            headers : {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            }
        }

        const url = `${baseURL}/${username}/category`;

        axios.get(url, requestHeader).then(response => {
            console.log(response.data);
            setAllCategories(response.data);
            return response.data;
        }).catch(error => {
            console.log(error.message);
        })
    }

    const openAddExpenseModal = (event, catName) => {
        event.preventDefault();
        const catSelected = allCategories.filter(cat => cat.categoryName === catName);
        setDropdownCategories(catSelected);
        setCreateExpenseModal(true);
    }

    const openAddExpenseModalAllCategories = () => {
        setDropdownCategories(allCategories);
        setCreateExpenseModal(true);
    }

    const openViewExpenseModal = (event, catName) => {
        event.preventDefault();
        setViewCategoryName(catName);
        setViewExpenseModal(true);
    }

    const eCards =  Object.keys(expenseGroupByCategory).map(key => {
        const catName = key;
        const catTotalBudget = allCategories.filter(cat =>  {
            return cat.categoryName === catName
        });
        //TODO Loading
        if(catTotalBudget[0] === undefined || catTotalBudget == null) {
            return (
                <div>
                    <p>Loading.....</p>
                </div>
            )
        }
        const categoryTotalBudget = catTotalBudget[0]["categoryBudget"];
        const expenses = expenseGroupByCategory[key].reduce((acc,obj) => {
            return acc + obj.amount
        }, 0);

        return (
            <div>
                {ExpenseCard(catName,expenses,categoryTotalBudget,
                    (event) => openAddExpenseModal(event, catName), (event) => openViewExpenseModal(event, catName), true )}
            </div>
        )

    })

    const totalExpenseCard = () => {
        let sum = allExpenses.reduce((acc,obj) => {
            return acc + obj.amount
        }, 0);
        return (
            <div>
                {ExpenseCard("All Expenses", sum, AuthService.getTotalBudget(),
                    (event) => event.preventDefault(), (event) => event.preventDefault(), false )}
            </div>
        )
    }


    return (
        <Container className={"pt-4"}>
            <Stack direction={"horizontal"} gap={2} className={"mb-4"}>
                <Stack direction={"vertical"}>
                    <h2 className={"me-auto"}>Expenses</h2>
                    <h6>Hi {username}, Below is an overview of your expenses.</h6>
                    <div>
                        <Button color={"info"} onClick={openAddExpenseModalAllCategories}>Add Expense</Button>
                    </div>
                </Stack>
                <Stack direction={"vertical"} gap={2}>
                    <Stack direction={"horizontal"} gap={2} className={"ms-auto"}>
                        <ProfileButton />
                        <SignoutButton />
                    </Stack>
                    <Stack className={"ms-auto"}>
                        <CategoryButton />
                    </Stack>
                </Stack>
            </Stack>
            <div style={{display:"grid", gridTemplateColumns: "repeat(auto-fill, minmax(500px,1fr))", gap: "1rem",
                alignItems: "flex-start"}}>
                {eCards}

            </div>
            <div className={"mt-4"}>
                {totalExpenseCard()}
            </div>
            {budget === "null" && (
                <div className={"float-end pt-3"} >
                    <p >Looks like you do not have any budget defined. Update your budget from the profile page. </p>
                </div>

            )}
            {CreateExpenseModal(createExpenseModal, () => setCreateExpenseModal(false), dropdownCategories)}
            {ViewExpenseModal(viewExpenseModal, () => setViewExpenseModal(false), expenseGroupByCategory[viewCategoryName], viewCategoryName)}
        </Container>
    )
}

export default Expense;