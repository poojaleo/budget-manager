import {useNavigate} from "react-router-dom";
import {Button} from "reactstrap";
import AuthService from "../../services/auth.service";

const SignoutButton = () => {
    const navigate = useNavigate();

    const routeToHomePage = () => {
        let path = '/home';
        AuthService.resetUserSession();
        navigate(path);
    }

    return (
        <div>
            <Button color={"outline-secondary"} type={"submit"} onClick={routeToHomePage}>Sign Out</Button>
        </div>
    )
}

export default SignoutButton;