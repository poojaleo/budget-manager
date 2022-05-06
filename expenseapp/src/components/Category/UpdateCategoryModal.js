import React from "react";
import AuthService from "../../services/auth.service";
import axios from "axios";
import {Button, Form, FormGroup, Modal} from "react-bootstrap";
import {Input, Label} from "reactstrap";

const UpdateCategoryModal = (show, handleClose, catName, catBudget) => {

    const baseURL = "http://exp-tracker-alb-1157495979.us-west-2.elb.amazonaws.com/api";

    const updateCategory = (event) => {
        event.preventDefault();
        const username = AuthService.getCurrentUser();
        const token = AuthService.getToken();
        console.log("Updating category....");
        const requestHeader = {
            headers : {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            }
        }

        const catGName = catName
        const catGBudget = catBudget.toString();

        const url = `${baseURL}/${username}/category/${catGName}`;

        const requestBody = {
            "budget": catGBudget
        }

        axios.put(url, requestBody, requestHeader).then(response => {
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
                <Form onSubmit={updateCategory}>
                    <Modal.Header closeButton>
                        <Modal.Title>Update Category</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <FormGroup className={"mb-3"} controlId={"name"}>
                            <Label htmlFor={"categoryName"}>Category Name</Label>
                            <Input type={"text"} name={"categoryName"} id={"categoryName"}
                                   value={catName} required={true} readOnly={true} />
                        </FormGroup>
                        <FormGroup className={"mb-3"} controlId={"budget"}>
                            <Label htmlFor={"categoryBudget"}>Category Budget</Label>
                            <Input type={"number"} name={"categoryBudget"} id={"categoryBudget"}
                                   defaultValue={catBudget} required={true} onChange={(event) => catBudget = event.target.value} />
                        </FormGroup>
                        <div className={"d-flex justify-content-end"}>
                            <Button type={"submit"} variant={"outline-success"}>Update Category</Button>
                        </div>
                    </Modal.Body>

                </Form>

            </Modal>
        </div>
    )
}

export default UpdateCategoryModal;