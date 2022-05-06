import React, {useState} from "react";
import axios from "axios";
import AuthService from "../../services/auth.service";
import {Button, Form, FormGroup, Modal} from "react-bootstrap";
import {Input, Label} from "reactstrap";

const CreateExpenseModal = (show, handleClose, categories) => {
    const [expense, setExpense] = useState({expenseDate: '', description: '', merchant: '', amount: 0.00});
    const [categoryName, setCategoryName] = useState('');


    const onCreateChange = (event) => {
        event.preventDefault();
        const value = event.target.value;
        const name = event.target.name;
        let expenseChange = {...expense};
        expenseChange[name] = value;
        setExpense(expenseChange);
    }

    const createExpense = (event) => {
        event.preventDefault();
        const username = AuthService.getCurrentUser();
        const token = AuthService.getToken();
        const requestHeader = {
            headers : {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            }
        }
        //const url = `${baseURL}/${username}/expense`;
        const url = `${username}/expense`;

        const requestBody = {
            "expenseDate": expense.expenseDate,
            "description": expense.description,
            "amount": expense.amount,
            "categoryName": categoryName,
            "merchant": expense.merchant
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
                <Form onSubmit={createExpense}>
                    <Modal.Header closeButton>
                        <Modal.Title>New Expense</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <FormGroup className={"mb-3"} controlId={"expenseDate"}>
                            <Label htmlFor={"expenseDate"}>Expense Date</Label>
                            <Input type={"date"} name={"expenseDate"} id={"expenseDate"}
                                   defaultValue={expense.expenseDate} required={true} onChange={onCreateChange} />
                        </FormGroup>
                        <FormGroup className={"mb-3"} controlId={"description"}>
                            <Label htmlFor={"description"}>Description</Label>
                            <Input type={"text"} name={"description"} id={"description"}
                                   defaultValue={expense.description} onChange={onCreateChange} />
                        </FormGroup>
                        <FormGroup className={"mb-3"} controlId={"merchant"}>
                            <Label htmlFor={"merchant"}>Merchant</Label>
                            <Input type={"text"} name={"merchant"} id={"merchant"}
                                   defaultValue={expense.merchant} onChange={onCreateChange} />
                        </FormGroup>
                        <FormGroup className={"mb-3"} controlId={"amount"}>
                            <Label htmlFor={"amount"}>Amount</Label>
                            <Input type={"number"} name={"amount"} id={"amount"}
                                   defaultValue={expense.amount} required={true} onChange={onCreateChange} />
                        </FormGroup>
                        {/*<FormGroup className={"mb-3"} controlId={"categoryName"}>
                            <Label htmlFor={"categoryName"}>Category</Label>
                            <Input type={"text"} name={"categoryName"} id={"categoryName"}
                                   defaultValue={expense.categoryName} required={true} onChange={onCreateChange} />
                        </FormGroup>*/}
                        <FormGroup className={"mb-3"} controlId={"categoryName"}>
                            <Label htmlFor={"categoryName"}>Category</Label>
                            <Form.Control as={"select"} className={"rounded-0 shadow"} id={"categoryName"}
                                          required={true} onChange={event => {
                                              setCategoryName(event.target.value)
                            }}>
                                <option key={"blank"}> </option>
                                {categories.map(opt => (
                                    <option key={opt.categoryName}>{opt.categoryName}</option>
                                ))}
                            </Form.Control>
                        </FormGroup>
                        <div className={"d-flex justify-content-end"}>
                            <Button type={"submit"} variant={"outline-success"}>Create Expense</Button>
                        </div>
                    </Modal.Body>

                </Form>

            </Modal>
        </div>
    )


}

export default CreateExpenseModal;