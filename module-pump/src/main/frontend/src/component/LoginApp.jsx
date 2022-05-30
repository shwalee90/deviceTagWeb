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
import PumpHomeComponent from './PumpHomeComponent.jsx'
import EquipInfoComponent from './EquipInfoComponent.jsx'
import TagInfoComponent from './TagInfoComponent.jsx'

class LoginApp extends Component {
    render() {
        const HeaderWithRouter = withRouter(HeaderComponent);

        return (
            <div className="TodoApp">
                <Router>
                    <div>
                        <HeaderWithRouter/>
                        <Switch>
                             <Route exact path="/tagWeb"  component={WelcomeComponent}/>
                             <Route exact path="/tagWeb/login" component={LoginComponent}/>
                             <Route exact path="/tagWeb/members/new"  component={MemberNewComponent}/>
                             <AuthenticatedRoute path="/tagWeb/welcome" component={WelcomeComponent}/>
                             <AuthenticatedRoute path="/tagWeb/test" component={TestComponent}/>
                             <Route path="/tagWeb/auth/pump/:equipid" component={PumpHomeComponent}
                             />
                             <Route path="/tagWeb/auth/pump" component={PumpHomeComponent}
                             />
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


