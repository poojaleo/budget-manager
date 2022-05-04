import {useNavigate} from "react-router-dom";
import {Button} from "reactstrap";

const LoginButton = () => {
    const navigate = useNavigate();

    const routeToLoginPage = () => {
        let path = '/login';
        navigate(path);
    }

    return (
        <div>
            <Button type={"submit"} onClick={routeToLoginPage}>Login</Button>
        </div>
    )
}

export default LoginButton;