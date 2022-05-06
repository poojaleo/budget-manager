
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

    setTotalBudget(budget) {
        sessionStorage.setItem('budget', budget);
    }

    getTotalBudget() {
        return sessionStorage.getItem('budget');
    }

    setAllCategories(allCategories) {
        sessionStorage.setItem('allCategories', allCategories);
    }

    getCategoryBudget(categoryName) {
        const all = sessionStorage.getItem("allCategories");
        console.log(all);
        return all.filter(e => {
            return e.categoryName === categoryName
        });
    }

    resetUserSession() {
        sessionStorage.removeItem('user');
        sessionStorage.removeItem('jwtToken');
    }
    

}
export default new AuthService();



