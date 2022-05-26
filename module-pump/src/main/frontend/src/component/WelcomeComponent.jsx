import React, {Component} from 'react'
import {Link} from 'react-router-dom'
import AuthenticationService from './AuthenticationService.js'
import axios from "axios";

class WelcomeComponent extends Component {

    constructor(props) {
        super(props)
        this.retrieveWelcomeMessage = this.retrieveWelcomeMessage.bind(this)
        this.state = {
            welcomeMessage : ''
        }
        this.handleSuccessfulResponse = this.handleSuccessfulResponse.bind(this)
        this.handleError = this.handleError.bind(this)








    }

    render() {
        return (
            <>
                <h1>Welcome!</h1>
                <div className="container">
                    Welcome {this.props.match.params.name}.
                </div>
                <div className="container">
                    <button onClick={this.modTestClicked}
                        className="btn btn-success">modTest</button>
                </div>
                <div className="container">
                    {this.state.welcomeMessage}
                </div>

                <div className="container">
                    Check if axiosInterceptors is working well!<br></br>
                    <button onClick={this.checkHeader}
                        className="btn btn-success">checkHeader</button>
                </div>


            </>
        )
    }



        modTestClicked() {
                 axios.get("/modTest" )
            }




    retrieveWelcomeMessage() {
        AuthenticationService.executeHelloService()
        .then( response => this.handleSuccessfulResponse(response) )
        .catch( error => this.handleError(error) )
    }

    handleSuccessfulResponse(response) {
        console.log(response)
        this.setState({welcomeMessage: response.data})
    }

    handleError(error) {
        console.log(error.response)
        let errorMessage = '';

        if(error.message)
            errorMessage += error.message

        if(error.response && error.response.data) {
            errorMessage += error.response.data.message
        }

        this.setState({welcomeMessage: errorMessage})
    }

}


export default WelcomeComponent