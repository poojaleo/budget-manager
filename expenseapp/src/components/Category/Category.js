import React, {useState, useEffect} from "react";
import AuthService from "../../services/auth.service";
import axios from "axios";
import {Button, Container} from "reactstrap";
import {Stack} from "react-bootstrap";
import "./Category.css";
import ProfileButton from "../Profile/ProfileButton";
import ExpenseButton from "../Expense/ExpenseButton";
import CategoryCard from "./CategoryCard";
import CreateCategoryModal from "./CreateCategoryModal";
import UpdateCategoryModal from "./UpdateCategoryModal";
import SignoutButton from "../NavBar/SignoutButton";

const Category = (props) => {
    const [username, setUsername] = useState('');
    const [token, setToken] = useState('');
    const [allCategories, setAllCategories] = useState([]);
    const [selectedCategoryName, setSelectedCategoryName] = useState("");
    const [selectedCategoryBudget, setSelectedCategoryBudget] = useState("");
    const [budget, setBudget] = useState('');
    const [createModal, setCreateModal] = useState(false);
    const [updateModal, setUpdateModal]  = useState(false);

    //const baseURL = "http://exp-tracker-alb-1157495979.us-west-2.elb.amazonaws.com/api";

    useEffect(() => {
        getAllCategories();
    },[]);

    const getAllCategories = () => {
        const token = AuthService.getToken();
        const username = AuthService.getCurrentUser();
        const budget = AuthService.getTotalBudget();
        setUsername(username);
        setToken(token);
        setBudget(budget);

        const requestHeader = {
            headers : {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            }
        }

        //const url = `${baseURL}/${username}/category`;
        const url = `${username}/category`;

        axios.get(url, requestHeader).then(response => {
            console.log(response.data);
            setAllCategories(response.data);
            return response.data;
        }).catch(error => {
            console.log(error.message);
        }).then(data => {
            AuthService.setAllCategories(data);
        })
    }

    const deleteCategory = (name) => {
        const requestHeader = {
            headers : {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            }
        }
        const url = `${username}/category/${name}`;
       // const url = `${baseURL}/${username}/category/${name}`;

        axios.delete(url, requestHeader).then(response => {
            console.log(response.data);
            window.location.reload();
        }).catch(error => {
            console.log(error.message);
        })

    }

    const openUpdateCategoryModal = (name, budget) => {

        setSelectedCategoryName(name);
        setSelectedCategoryBudget(budget);

        setUpdateModal(true)

    }

    const categoryCards = allCategories?.map(category => {
        return (
            <div>

               {CategoryCard(category.categoryName, category.categoryBudget, budget,
                   () => openUpdateCategoryModal(category.categoryName, category.categoryBudget),
                   () => deleteCategory(category.categoryName), true)}
            </div>
        )
    })

    const totalCatBudget = allCategories?.reduce( (acc, obj) => {
        return acc + obj.categoryBudget
    }, 0);

    const totalCatCard = () => {
        return (
            <div>
                {CategoryCard("All Categories", totalCatBudget, budget,null, null, false)}
            </div>
        )
    }




    return (
        <Container className={"pt-4"}>
            <Stack direction={"horizontal"} gap={2} className={"mb-4"}>
                <Stack direction={"vertical"} gap={2}>
                    <h2 className={"me-auto"}>Categories</h2>
                    <h6>Hi {username}, Below is an overview of your categories.</h6>
                    <div>
                        <Button color={"info"} onClick={() => setCreateModal(true)}>Create Category</Button>
                    </div>
                </Stack>
                <Stack direction={"vertical"} gap={2} >
                    <Stack direction={"horizontal"} gap={2} className={"ms-auto"}>
                        <ProfileButton />
                        <SignoutButton />
                    </Stack>
                    <Stack className={"ms-auto"}>
                        <ExpenseButton />
                    </Stack>
                </Stack>
                <div>
                </div>
            </Stack>
            <div style={{display:"grid", gridTemplateColumns: "repeat(auto-fill, minmax(500px,1fr))", gap: "1rem",
                alignItems: "flex-start"}}>
                {categoryCards}
            </div>
            <div className={"mt-4"}>
                {totalCatCard()}
            </div>
            {budget === "null" && (
                <div className={"float-end pt-3"} >
                    <p >Looks like you do not have any budget defined. Update your budget from the profile page. </p>
                </div>

            )}

            {CreateCategoryModal(createModal, () => setCreateModal(false))}
            {UpdateCategoryModal(updateModal, ()=> setUpdateModal(false),
                selectedCategoryName, selectedCategoryBudget)}
        </Container>
    )
}

export default Category;