import React, {useState} from "react";
import {Button, Form, FormControl, FormGroup, FormLabel, Modal} from "react-bootstrap";
import axios from "axios";
import AuthService from "../../services/auth.service";
import {Input, Label} from "reactstrap";

const CreateCategoryModal = (show, handleClose) => {
    const [selectedCategory, setSelectedCategory] = useState({categoryName: '', categoryBudget: ''});
    const baseURL = "http://exp-tracker-alb-1157495979.us-west-2.elb.amazonaws.com/api";

    const onCreateChange = (event) => {
        event.preventDefault();
        const value = event.target.value;
        const name = event.target.name;
        let categoryChange = {...selectedCategory};
        categoryChange[name] = value;
        setSelectedCategory(categoryChange);
    }

    const createCategory = (event) => {
        event.preventDefault();
        const catName = selectedCategory.categoryName;
        const catBudget = selectedCategory.categoryBudget;
        const username = AuthService.getCurrentUser();
        const token = AuthService.getToken();

        const requestHeader = {
            headers : {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            }
        }

        const url = `${baseURL}/${username}/category`;

        const requestBody = {
            "categoryName": catName,
            "categoryBudget": catBudget
        }

        axios.post(url, requestBody, requestHeader).then(response => {
            console.log(response.data);
            handleClose();
            window.location.reload();
        }).catch(error => {
            console.log(error.message);
        })
    }


    return (
        <div>
            <Modal show={show} onHide={handleClose}>
                <Form onSubmit={createCategory}>
                    <Modal.Header closeButton>
                        <Modal.Title>New Category</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <FormGroup className={"mb-3"} controlId={"name"}>
                            <Label htmlFor={"categoryName"}>Category Name</Label>
                            <Input type={"text"} name={"categoryName"} id={"categoryName"}
                                   defaultValue={selectedCategory.categoryName} required={true} onChange={onCreateChange} />
                        </FormGroup>
                        <FormGroup className={"mb-3"} controlId={"budget"}>
                            <Label htmlFor={"categoryBudget"}>Category Budget</Label>
                            <Input type={"number"} name={"categoryBudget"} id={"categoryBudget"}
                                   defaultValue={selectedCategory.categoryBudget} required={true} onChange={onCreateChange} />
                        </FormGroup>
                        <div className={"d-flex justify-content-end"}>
                            <Button type={"submit"} variant={"outline-success"}>Create Category</Button>
                        </div>
                    </Modal.Body>

                </Form>

            </Modal>
        </div>
    )
}

export default CreateCategoryModal;