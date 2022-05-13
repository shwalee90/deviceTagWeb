import React, {Component} from 'react'
import {Link , withRouter  } from 'react-router-dom'
import AuthenticationService from './AuthenticationService.js'
import axios from "axios";
class TagInfoComponent extends Component {

        constructor(props) {



           super(props)

           this.state = {
                    equipid : this.props.equipid,
                  }

         }


             componentDidMount() {
                this._getListData()

              }

              _getListData = async function() {
                const data_list = await axios(`/auth/tagInfo/${this.state.equipid}`, {
                  method : 'GET',
                  headers: {
                             "Content-Type": `application/json`,
                             "token" : localStorage.getItem('token')
                                                                 }
                })


                this.setState({ data : data_list })



              }






    render() {






        return (
            <>
                <h1>TAG</h1>
            </>
        )
    }





}


export default TagInfoComponent