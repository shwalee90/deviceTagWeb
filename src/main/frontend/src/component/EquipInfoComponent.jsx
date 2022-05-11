import React, {Component} from 'react'
import {Link} from 'react-router-dom'
import AuthenticationService from './AuthenticationService.js'
import axios from "axios";


class EquipInfoComponent extends Component {


    constructor(props) {

    console.log("==EquipInfoComponent==")

       super(props)
       this.state = {
         data : [],
       }
     }

      componentDidMount() {
        this._getListData()

      }

      _getListData = async function() {
        const data_list = await axios('/auth/equip', {
          method : 'GET',
          headers: {
                     "Content-Type": `application/json`,
                     "token" : localStorage.getItem('token')
                                                         }
        })

        console.log(data_list);

        this.setState({ data : data_list })

      }





    render() {

        const { data } = this.props;

        console.log(data.data);

        return (
            <>
                <h1>Equip</h1>
                <div>
                 {data.data ? data.data.map( (el, key) => {
                            return(
                              <div className='list_grid list_data' key={key}>
                                <div> {el.equip_alias} </div>
                              </div>
                            )
                          })
                            : null}
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


export default EquipInfoComponent