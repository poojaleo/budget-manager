import React from "react";
import AppNavbar from "./AppNavbar";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import '../App.css';
import {Button, Container, Form, FormGroup, Table} from "reactstrap";
import {Link} from "react-router-dom";
import Moment from "react-moment";

class Expenses extends React.Component {
    emptyItem = {
        id : '110',
        expenseDate: new Date(),
        description: '',
        amount: '',
        category: {
            id: 1,
            categoryName: "Travel"
        },
        user: {
            id: 10,
            username: "",
            emailAddress: ""
        }
    }

    constructor(props) {
        super(props);
        this.state = {
            date: new Date(),
            isLoading: true,
            categories : [],
            item : this.emptyItem,
            expenses : []
        }
        this.handleFormSubmit = this.handleFormSubmit.bind(this);
        this.handleFormChange = this.handleFormChange.bind(this);
        this.handleDateChange = this.handleDateChange.bind(this);
    }


    // It will make a call to API as soon as component is mounted to the page
    async componentDidMount() {
        const categoryResponse = await fetch('/api/category')
        const categoryBody = await categoryResponse.json();
        this.setState({categories : categoryBody, isLoading : false});

        const expenseResponse = await fetch('/api/expense')
        const expenseBody = await expenseResponse.json();
        this.setState({expenses : expenseBody, isLoading : false});
    }

    async handleFormChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let itemChange = {...this.state.item};
        itemChange[name] = value;
        this.setState({item : itemChange});
        console.log(this.state.item);
    }

    async handleDateChange(date) {
        let itemChange = {...this.state.item};
        itemChange["expenseDate"] = date;
        this.setState({item : itemChange});
        console.log(this.state.item);
    }

    async handleFormSubmit(event) {
        event.preventDefault();
        const item = this.state.item;
        await fetch(`/api/expense` , {
            method : 'POST',
            headers : {
                'Accept' : 'application/json',
                'Content-Type': 'application/json'
            },
            body : JSON.stringify(item)
        });


        this.props.history.push("/expenses");
    }

    async removeExpense(id) {
        await fetch(`/api/expense/${id}` , {
            method: 'DELETE',
            headers: {
                'Accept' : 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
                let updatedExpenses = [...this.state.expenses].filter(i => i.id !== id);
                this.setState({expenses : updatedExpenses});
            }
        )

    }

    render() {
        const title = <h3>Add Expense</h3>
        const categories = this.state.categories;
        const isLoading = this.state.isLoading;
        const expenses = this.state.expenses;

        if(isLoading)
            return (<div>
                        <AppNavbar></AppNavbar>
                            <h5>Loading....</h5></div>)

        let categoryList = categories.map(category =>
            <option id={category.id} key={category.id}>
                {category.categoryName}
            </option>
        )

        let rows = expenses.map(expense =>
            <tr key={expense.id}>
                <td>{expense.description}</td>
                <td>{expense.amount}</td>
                <td>{expense.category.categoryName}</td>
                <td><Moment date={expense.expenseDate} format={"YYYY/MM/DD"} ></Moment> </td>
                <td><Button size={"sm"} color={"danger"} onClick={() => this.removeExpense(expense.id)}>Delete</Button></td>
            </tr>
        )

        return (
            <div>
                <AppNavbar></AppNavbar>
                <Container>
                    {title}
                    <Form onSubmit={this.handleFormSubmit}>
                        <FormGroup>
                            <label htmlFor="description">Title</label>
                            <input type="text" name="description" id="description" onChange={this.handleFormChange} autoComplete={"name"}/>
                        </FormGroup>
                        <FormGroup>
                            <label htmlFor="category">Category</label>
                            <select>
                                {categoryList}
                            </select>
                        </FormGroup>
                        <FormGroup>
                            <label htmlFor="expenseDate">Expense Date</label>
                            <DatePicker selected={this.state.item.expenseDate} onChange={this.handleDateChange}></DatePicker>

                        </FormGroup>
                        <div className={"row"}>
                            <FormGroup className={"col-md-4 mb-3"}>
                                <label htmlFor="amount">Amount</label>
                                <input type="text" name="amount" id="amount" onChange={this.handleFormChange}/>
                            </FormGroup>
                        </div>

                        <FormGroup>
                            <Button color="primary" type={"submit"}>Save
                            </Button>{' '}
                            <Button color={"secondary"} tag={Link} to={"/"}>Cancel</Button>{' '}
                        </FormGroup>
                    </Form>
                </Container>
                {' '}
                <Container>
                   <h3>Expense List</h3>
                    <Table className={"mt-4"}>
                        <thead>
                            <tr>
                                <th width={"30%"}>Expense</th>
                                <th width={"15%"}>Amount</th>
                                <th width={"15%"}>Category</th>
                                <th width={"30%"}>Expense Date</th>
                                <th width={"15%"}>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {rows}
                        </tbody>

                    </Table>
                </Container>
            </div>
        )
    }
}

export default Expenses;