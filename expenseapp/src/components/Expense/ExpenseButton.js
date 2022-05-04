import {useNavigate} from "react-router-dom";
import {Button} from "reactstrap";
const ExpenseButton = () => {
    const navigate = useNavigate();

    const routeToExpensePage = () => {
        let path = '/expenses';
        navigate(path);
    }

    return (
        <div>
            <Button type={"submit"} color={"info"} onClick={routeToExpensePage}>Manage Expenses</Button>
        </div>
    )
}

export default ExpenseButton;