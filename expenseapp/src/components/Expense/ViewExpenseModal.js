import {currencyFormatter, dateFormatter} from "../utils/utils";
import {Button, Modal, Stack, Table} from "react-bootstrap";
import React, {useState} from "react";
import AuthService from "../../services/auth.service";
import axios from "axios";


const ViewExpenseModal = (show, handleClose, expenses, categoryName) => {

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

        const url = `/${username}/expense/${expenseId}`;

        axios.delete(url, requestHeader).then(response => {
            console.log(response.data);
            handleClose();
            window.location.reload();
        }).catch(error => {
            console.log(error.message);
        })
    }

    const updateExpense = (event) => {
        event.preventDefault();
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
               <td><Button variant={"info"} size={"sm"} onClick={() => updateExpense}>Update Expense</Button></td>
               <td><Button variant={"outline-danger"} size={"sm"} onClick={() => deleteExpense(exp.expenseId)}>Delete Expense</Button></td>
           </tr>
       )
   })


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

            </Modal.Body>
        </Modal>
    )
}

export default ViewExpenseModal;