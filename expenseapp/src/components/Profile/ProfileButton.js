import {useNavigate} from "react-router-dom";
import {Button} from "reactstrap";

const ProfileButton = () => {
    const navigate = useNavigate();

    const routeToProfilePage = () => {
        let path = '/profile';
        navigate(path);
    }

    return (
        <div>
            <Button type={"submit"} color={"info"} onClick={routeToProfilePage}>Update Profile</Button>
        </div>
    )
}

export default ProfileButton;