import React from 'react';
import {Route, BrowserRouter as Router, Routes} from "react-router-dom";
import Home from "./Components/Home";
import Category from './Components/catgeory';
import Expenses from "./Components/Expenses";
import "./App.css";

class App extends React.Component {
  render() {
    return (
        <Router className="App">
            <Routes>
                <Route path='/' element={<Home/>} />
                <Route path='/home' element={<Home/>} />
                <Route path='/category' exact={true} element={<Category/>} />
                <Route path='/expenses' exact={true} element={<Expenses/>} />
            </Routes>
        </Router>
    )
  }
}

export default App;
