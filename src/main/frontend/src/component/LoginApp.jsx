import React, {Component} from 'react'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import AuthenticatedRoute from './AuthenticatedRoute.jsx'
import LoginComponent from './LoginComponent.jsx'
import ErrorComponent from './ErrorComponent.jsx'
import HeaderComponent from './HeaderComponent.jsx'
import FooterComponent from './FooterComponent.jsx'
import WelcomeComponent from './WelcomeComponent.jsx'
import MemberNewComponent from './MemberNewComponent.jsx'
import TestComponent from './TestComponent.jsx'
import { withRouter } from 'react-router'

class LoginApp extends Component {
    render() {
        const HeaderWithRouter = withRouter(HeaderComponent);

        return (
            <div className="TodoApp">
                <Router>
                    <div>
                        <HeaderWithRouter/>
                        <Switch>
                             <Route exact path="/"  component={WelcomeComponent}/>
                             <Route exact path="/login" component={LoginComponent}/>
                             <Route exact path="/members/new"  component={MemberNewComponent}/>
                             <AuthenticatedRoute path="/welcome/:name" component={WelcomeComponent}/>
                             <AuthenticatedRoute path="/test" component={TestComponent}/>
                             <Route component={ErrorComponent}/>

                        </Switch>
                        <FooterComponent/>
                    </div>
                </Router>
            </div>
        )
    }
}

export default LoginApp


