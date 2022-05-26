import React, {Component} from 'react'
import AuthenticationService from './AuthenticationService.js'
import axios from "axios";


class LoginComponent extends Component {

    constructor(props) {
        super(props)

         console.log('cons');


        this.state = {
            username: localStorage.getItem("authenticatedUser") || '',
            password: '',
            token: localStorage.getItem("token") || '',
            hasLoginFailed: false,
            showSuccessMessage: false
        }
        this.handleChange = this.handleChange.bind(this)
        this.loginClicked = this.loginClicked.bind(this)
    }

    componentDidUpdate(prevProps){

        console.log('update');
        if(prevProps.location.pathname !== this.props.location.pathname){
            console.log(true);
            this.setState({loading : true});
        }


    }



    handleChange(event) {
        this.setState(
            {
                [event.target.name]
                  :event.target.value
            }
        )
    }



    loginClicked() {
            let data = {username : this.state.username,
                        password : this.state.password};

             axios.post("/login" , JSON.stringify(data),{
                headers: {
                                  "Content-Type": `application/json`,
                                }})
            .then(response => {
                         console.log(response)
                         AuthenticationService.registerSuccessfulLoginForJwt(this.state.username,response.data.result.accessToken)
                         this.props.history.push(`/welcome`)
                     }).catch( () =>{
                this.setState({showSuccessMessage:false})
                this.setState({hasLoginFailed:true})
            })
        }




    render() {
        return (
            <div>
                <h1>Login</h1>
                <div className="container">
                    {this.state.hasLoginFailed && <div className="alert alert-warning">Invalid Credentials</div>}
                    {this.state.showSuccessMessage && <div>Login Sucessful</div>}
                    User Name: <input type="text" name="username" value={this.state.username} onChange={this.handleChange}/>
                    Password: <input type="password" name="password" value={this.state.password}  onChange={this.handleChange}/>
                    <button className="btn btn-success" onClick={this.loginClicked}>Login</button>
                </div>
            </div>
        )
    }
}

export default LoginComponent