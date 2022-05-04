import React, {useState} from "react";
import {Button, Card, ProgressBar, Stack} from "react-bootstrap";
import {currencyFormatter} from "../utils/utils";
import UpdateCategoryModal from "./UpdateCategoryModal";


const CategoryCard = (categoryName, categoryBudget, totalBudget, onUpdateCategoryClick, onDeleteCategoryClick, showButton) => {
    const getProgressBarVariant = (amount, max) => {
        const ratio = amount/max;
        if(ratio < 0.5)
            return "primary"
        if(ratio < 0.75)
            return "warning"
        else
            return "danger"
    }


    return (
        <Card>
            <Card.Body>
                <Card.Title className={"d-flex justify-content-between align-items-baseline fw-normal mb-3"}>
                    <div className={"me-2"}>{categoryName}</div>
                    <div className={"d-flex align-items-baseline"}>{currencyFormatter.format(categoryBudget)}
                        <span className={"text-muted fs-6 ms-1"}> / {currencyFormatter.format(totalBudget)} </span></div>
                </Card.Title>
                <ProgressBar className={"rounder-pill"} variant={getProgressBarVariant(categoryBudget, totalBudget)}
                min={0} now={categoryBudget} max={totalBudget}/>
                {showButton && (
                    <Stack direction={"horizontal"} gap={2} className={"mt-4"}>
                        <Button variant={"info"} className={"ms-auto"} onClick={onUpdateCategoryClick}>Update Category</Button>
                        <Button variant={"outline-danger"} onClick={onDeleteCategoryClick}>Delete Category</Button>
                    </Stack>
                )}

            </Card.Body>
        </Card>
    )
}


export default CategoryCard;
