import React, {Component} from 'react'
import {Link} from 'react-router-dom'
import AuthenticationService from './AuthenticationService.js'
import axios from "axios";
import '../css/HeaderComponent.css';



class HeaderComponent extends Component {
    render() {
        const isUserLoggedIn = AuthenticationService.isUserLoggedIn();

        console.log("=====Headeromponent=====");
        console.log(isUserLoggedIn);

        // Add a request interceptor
                axios.interceptors.request.use(function (config) {
                    // Do something before request is sent

                    console.log("=====interceptor=====");
                    config.headers['token'] = localStorage.getItem('token');
                    return config;
                  }, function (error) {
                    // Do something with request error
                    return Promise.reject(error);
                  });





        if(localStorage.getItem('token') != null){

            console.log("default : " + localStorage.getItem('token'));
            axios.defaults.headers.common['token'] = localStorage.getItem('token');


        }else{
            axios.defaults.headers.common['token'] = "";
        }







        return (
            <header>
                <nav className="navbar navbar-expand-md navbar-dark bg-dark">

                <ul>
                    <li className="navbar-nav">
                        {isUserLoggedIn && <li><Link className="nav-link" to="/welcome">Home</Link></li>}
                    </li>

                    <li className="navbar-nav">
                        {isUserLoggedIn && <li><Link className="nav-link" to="/auth/pump/1">pump home</Link></li>}
                    </li>


                    <li className="navbar-nav navbar-collapse justify-content-end">
                        {!isUserLoggedIn && <li><Link className="nav-link" to="/login">Login</Link></li>}
                        {<li><Link className="nav-link" to="/members/new">Join</Link></li>}
                        {isUserLoggedIn && <li><Link className="nav-link" to="/logout" onClick={AuthenticationService.logout}>Logout</Link></li>}

                    </li>
                </ul>
                </nav>
            </header>
        )
    }






    }






export default HeaderComponent