import React from "react";
import AppNavbar from "./AppNavbar";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Table} from "reactstrap";
import {Tab} from "react-bootstrap";

class Category extends React.Component {

    state = {
        isLoading : true,
        Categories : []
    }

    async componentDidMount() {
        const response = await fetch('/api/category/')
        const body = await response.json();
        this.setState({Categories : body, isLoading : false});
    }

    render() {
        const Categories = this.state.Categories;
        const isLoading = this.state.isLoading;
        let count = 0;


        if(isLoading)
            return (<div>Loading....</div>)

        let totalBudget = Categories.reduce((a,b) => {
            return a + b.categoryBudget
        },0);

        let rows = Categories.map(category =>
            <tr>
                <th scope="row">{count++}</th>
                <td>{category.categoryName}</td>
                <td>{category.categoryBudget}</td>
            </tr>
        )

        return (
            <div>
                <AppNavbar/>
                <h2 className={"mx-1 my-3"}>Categories</h2>

                <Table hover size="" className={"mx-5 my-2 p-5"}>
                    <thead>
                        <tr>
                        <th width={"15%"}>#</th>
                        <th width={"25%"}>Category</th>
                        <th >Budget</th>
                        </tr>
                    </thead>
                    <tbody>
                        {rows}
                    </tbody>
                </Table>
                <Table hover className={"mx-5 my-2 p-5"}>
                    <thead>
                        <tr>
                            <th width={"15%"}></th>
                            <th width={"25%"}>Total</th>
                            <th>{totalBudget}</th>
                        </tr>
                    </thead>
                </Table>
            </div>
        )
    }
}

export default Category;