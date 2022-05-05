import {useNavigate} from "react-router-dom";
import {Button} from "reactstrap";

const SignupButton = () => {
    const navigate = useNavigate();

    const routeToSignupPage = () => {
        let path = '/signup';
        navigate(path);
    }

    return (
        <div>
            <Button color={"primary"} type={"submit"} onClick={routeToSignupPage}>Signup</Button>
        </div>
    )
}

export default SignupButton;