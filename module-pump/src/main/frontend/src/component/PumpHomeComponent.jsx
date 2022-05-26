import React, {Component} from 'react'
import {Link , useParams, useNavigate} from 'react-router-dom'
import AuthenticationService from './AuthenticationService.js'
import '../css/PumpHomeComponent.css';
import EquipInfoComponent from './EquipInfoComponent.jsx'
import TagInfoComponent from './TagInfoComponent.jsx'


class PumpHomeComponent extends Component {


    constructor(props) {
        super(props)
                   this.state = {
                            equipid : 0,
                          }
    }



        render() {

            let pam = this.props.match;
             console.log( "render : "+ pam.params.equipid);
            return (
                <>
                    <div>
                          <div class="left">
                            <EquipInfoComponent data="[]"/>
                          </div>
                          <div class="right">
                            <TagInfoComponent equipid={pam.params.equipid}/>
                          </div>
                    </div>
                </>
            )
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


export default PumpHomeComponent