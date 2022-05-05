import {Button, Card, ProgressBar, Stack} from "react-bootstrap";
import {currencyFormatter} from "../utils/utils";

const ExpenseCard = (categoryName, expenseAdd, categoryBudget, onAddExpenseClick, onViewAllExpenseClick, showButton) => {
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
                    <div className={"d-flex align-items-baseline"}>{currencyFormatter.format(expenseAdd)}
                        <span className={"text-muted fs-6 ms-1"}> / {currencyFormatter.format(categoryBudget)} </span></div>
                </Card.Title>
                <ProgressBar className={"rounder-pill"} variant={getProgressBarVariant(expenseAdd, categoryBudget)}
                             min={0} now={expenseAdd} max={categoryBudget}/>
                {showButton && (
                    <Stack direction={"horizontal"} gap={2} className={"mt-4"}>
                        <Button variant={"info"} className={"ms-auto"} onClick={onAddExpenseClick}>Add Expense</Button>
                        <Button variant={"outline-secondary"} onClick={onViewAllExpenseClick}>View All expenses</Button>
                    </Stack>
                )}

            </Card.Body>
        </Card>
    )
}

export default ExpenseCard;