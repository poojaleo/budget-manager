import React from "react";
import AppNavbar from "./AppNavbar";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import '../App.css';
import {Button, Container, Form, FormGroup} from "reactstrap";
import {Link} from "react-router-dom";

class Expenses extends React.Component {
    state = {
        date: new Date(),
        isLoading: true,
        categories : [],
        expenses: []
    }

    // It will make a call to API as soon as component is mounted to the page
    async componentDidMount() {
        const response = await fetch('/api/category')
        const body = await response.json();
        this.setState({categories : body, isLoading : false});
    }

    handleFormChange() {

    }

    handleFormSubmit() {

    }

    render() {
        const title = <h3>Add Expense</h3>
        const categories = this.state.categories;
        const isLoading = this.state.isLoading;

        if(isLoading)
            return (<div>
                        <AppNavbar></AppNavbar>
                            <h5>Loading....</h5></div>)

        let categoryList = categories.map(category =>
            <option id={category.id}>
                {category.categoryName}
            </option>
        )

        return (
            <div>
                <AppNavbar></AppNavbar>
                <Container>
                    {title}
                    <Form onSubmit={this.handleFormSubmit}>
                        <FormGroup>
                            <label for="title">Title</label>
                            <input type="text" name="title" id="title" onChange={this.handleFormChange} autoComplete={"name"}/>
                        </FormGroup>
                        <FormGroup>
                            <label for="category">Category</label>
                            <select>
                                {categoryList}
                            </select>
                        </FormGroup>
                        <FormGroup>
                            <label for="expenseDate">Expense Date</label>
                            <DatePicker selected={this.state.date} onChange={this.handleFormChange}></DatePicker>

                        </FormGroup>
                        <div className={"row"}>
                            <FormGroup className={"col-md-4 mb-3"}>
                                <label for="description">Description</label>
                                <input type="text" name="description" id="description" onChange={this.handleFormChange}/>
                            </FormGroup>
                        </div>

                        <FormGroup>
                            <Button color="primary" type={"submit"}>Save
                            </Button>{' '}
                            <Button color={"secondary"} tag={Link} to={"/"}>Cancel</Button>{' '}
                        </FormGroup>
                    </Form>
                </Container>
            </div>
        )
    }
}

export default Expenses;