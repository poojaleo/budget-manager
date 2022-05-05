import {currencyFormatter, dateFormatter} from "../utils/utils";
import {Button, Modal, Stack, Table, Form, FormGroup} from "react-bootstrap";
import React, {useState} from "react";
import AuthService from "../../services/auth.service";
import axios from "axios";
import {Input, Label} from "reactstrap";


const ViewExpenseModal = (show, handleClose, expenses, categoryName) => {
    const [showUpdateForm, setShowUpdateForm] = useState(false);
    const [exp, setExp] = useState({});

    const baseURL = "http://exp-tracker-alb-1157495979.us-west-2.elb.amazonaws.com/api";

    const generateRows = expenses?.map(exp => {
        console.log(exp);
        console.log(new Date(exp.expenseDate).toISOString());
        return (
            <Stack direction={"horizontal"} gap={2} key={exp.expenseId}>
                <div className={"fs-5"}>{exp.description}</div>
                <div className="fs-6">{currencyFormatter.format(exp.amount)}</div>
                <div className="fs-6">{new Date(exp.expenseDate).toISOString().substring(0,10)}</div>
            </Stack>
        )
    })

    const deleteExpense = (expenseId) => {

        const username = AuthService.getCurrentUser();
        const token = AuthService.getToken();
        console.log("Deleting expense....");

        const requestHeader = {
            headers : {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            }
        }

        const url = `${baseURL}/${username}/expense/${expenseId}`;

        axios.delete(url, requestHeader).then(response => {
            console.log(response.data);
            handleClose();
            window.location.reload();
        }).catch(error => {
            console.log(error.message);
        })
    }

    const updateExpense = (exp) => {
        setShowUpdateForm(true);
        setExp(exp);
    }

    let count = 0;

   const rowsTable = expenses?.map(exp => {
      count += 1;
       return (
           <tr>
               <td>{count}</td>
               <td>{exp.description}</td>
               <td>{currencyFormatter.format(exp.amount)}</td>
               <td>{new Date(exp.expenseDate).toISOString().substring(0,10)}</td>
               <td><Button variant={"info"} size={"sm"} onClick={() => updateExpense(exp)}>Update Expense</Button></td>
               <td><Button variant={"outline-danger"} size={"sm"} onClick={() => deleteExpense(exp.expenseId)}>Delete Expense</Button></td>
           </tr>
       )
   })

    const updateSubmit = (expense) => {
        const username = AuthService.getCurrentUser();
        const token = AuthService.getToken();
        const requestHeader = {
            headers : {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            }
        }

        const requestBody = {
            "expenseDate": expense.expenseDate,
            "description": expense.description,
            "amount": expense.amount,
            "categoryName": expense.categoryName,
            "merchant": expense.merchant
        }

        const url = `/${username}/expense/${expense.expenseId}`;

        axios.put(url, requestBody, requestHeader).then(response => {
            console.log(response.data);
            handleClose();
            window.location.reload();
        }).catch(error => {
            console.log(error.message);
        })


    }

    const updateForm = (expense) => {
       if(showUpdateForm) {
           return (
               <Form onSubmit={() => updateSubmit(expense)}>
                   <FormGroup className={"mb-3"} controlId={"description"}>
                       <Label htmlFor={"description"}>Description</Label>
                       <Input type={"text"} name={"description"} id={"description"}
                              defaultValue={expense.description} onChange={(event) => {
                                  expense.description = event.target.value
                       }}/>
                   </FormGroup>
                   <FormGroup>
                       <Label htmlFor={"expenseDate"}>Expense Date</Label>
                       <Input type={"date"} name={"expenseDate"} id={"expenseDate"}
                              defaultValue={expense.expenseDate} required={true} onChange={(event) => {
                                expense.expenseDate = event.target.value
                       }} />
                   </FormGroup>
                   <FormGroup className={"mb-3"} controlId={"amount"}>
                       <Label htmlFor={"amount"}>Amount</Label>
                       <Input type={"number"} name={"amount"} id={"amount"}
                              defaultValue={expense.amount} required={true} onChange={(event) => {
                           expense.amount = event.target.value
                       }} />
                   </FormGroup>
                   <div className={"d-flex justify-content-end"}>
                       <Button type={"submit"} variant={"outline-success"}>Update Expense</Button>
                   </div>
               </Form>
           )
       }
    }


    return (
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>
                    <Stack direction={"horizontal"} gap={2}>
                        <div>Expenses: {categoryName}</div>

                    </Stack>
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {/*<Stack direction={"vertical"} gap={2}>
                    {generateRows}
                </Stack>*/}
                <Table bordered hover>
                    <thead>
                        <th>#</th>
                        <th>Description</th>
                        <th>Amount</th>
                        <th>Date</th>
                        <th>Action</th>
                        <th>Action</th>
                    </thead>
                    <tbody>
                    {rowsTable}
                    </tbody>
                </Table>
                {updateForm(exp)}
            </Modal.Body>
        </Modal>
    )
}

export default ViewExpenseModal;