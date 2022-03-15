import React from "react";
import {Nav, Navbar, NavbarBrand, NavbarToggler, Collapse, NavItem, NavLink} from "reactstrap";

class AppNavbar extends React.Component {
    render() {
        return (
            <div>
                <Navbar color="dark"  expand="lg" full dark>
                    <NavbarBrand href="/">
                        Expense Tracker Application
                    </NavbarBrand>
                    <NavbarToggler onClick={function noRefCheck(){}} />
                    <Collapse navbar>
                    <Nav className="ms-auto" navbar>
                            <NavItem>
                                <NavLink href="/">
                                    Home
                                </NavLink>
                            </NavItem>
                            <NavItem>
                                <NavLink href="/category">
                                    Categories
                                </NavLink>
                            </NavItem>
                            <NavItem>
                                <NavLink href="/expenses">
                                    Expenses
                                </NavLink>
                            </NavItem>
                        </Nav>
                    </Collapse>
                </Navbar>
            </div>
        )
    }
}

export default AppNavbar;