
//const API_URL = "http://localhost:8080/api/auth/";

class AuthService {
    getCurrentUser() {
        const user = sessionStorage.getItem('user');
        if(user === 'undefined' || !user) {
            return null;
        } else {
            return user;
        }
    }

    getToken() {
        return sessionStorage.getItem('jwtToken');
    }

    setUserSession(username, token) {
        sessionStorage.setItem('user', username);
        sessionStorage.setItem('jwtToken', token);
    }

    resetUserSession() {
        sessionStorage.removeItem('user');
        sessionStorage.removeItem('jwtToken');
    }

    /*login(username, password) {

        const requestBody = {
            username: username,
            password: password
        }

        axios.post('/login', requestBody).then((response) => {
            setUserSession(response.data.username, response.data.authToken, response.data.newUser);
            console.log("newUser: " + response.data.newUser);
            props.authenticate();
            navigate('/portfolio');
        }).catch((error) => {
            console.log('Error ' + error.toJSON())
            console.log(error.response)
            setMessage(error.response.data.errorMessage.split('] ')[1]);
        })
        /!*const userResponse = await fetch(`/users/${name}?password=${pass}`);
        const userBody = await userResponse.json();

        console.log(userBody);

        if (userBody.user === undefined) {
            alert("Invalid Username or Password. Please try again.")
        } else {
            //routeChange();
        }*!/

        /!*return axios
            .post(API_URL + "signin", {
                username,
                password
            })
            .then(response => {
                if (response.data.jwtToken) {
                    localStorage.setItem("user", JSON.stringify(response.data));
                }
                return response.data;
            });*!/
    }

    logout() {
        localStorage.removeItem("user");
    }

    register(username, email, password) {
        return axios.post(API_URL + "signup", {
            username,
            email,
            password
        });
    }*/

}
export default new AuthService();



