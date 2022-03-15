import React from "react";
import AppNavbar from "./AppNavbar";

class Category extends React.Component {

    state = {
        isLoading : true,
        Categories : []
    }

    async componentDidMount() {
        const response = await fetch('/api/category/')
        const body = await response.json();
        this.setState({Categories : body, isLoading : false });
    }

    render() {
        const Categories = this.state.Categories;
        const isLoading = this.state.isLoading;

        if(isLoading)
            return (<div>Loading....</div>)

        return (
            <div>
                <AppNavbar/>
                <h2>Categories</h2>
                {
                    Categories.map(category =>
                        <div id={category.id}>
                            {category.categoryName}
                        </div>
                    )
                }
            </div>
        )
    }
}

export default Category;