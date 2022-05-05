import {useNavigate} from "react-router-dom";
import {Button} from "reactstrap";

const CategoryButton = () => {
    const navigate = useNavigate();

    const routeToCategoryPage = () => {
        let path = '/categories';
        navigate(path);
    }

    return (
        <div>
            <Button type={"submit"} color={"info"} onClick={routeToCategoryPage}>Manage Categories</Button>
        </div>
    )
}

export default CategoryButton;