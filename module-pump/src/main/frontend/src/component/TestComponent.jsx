import React, {Component} from 'react'
import {Link} from 'react-router-dom'
import AuthenticationService from './AuthenticationService.js'
import axios from "axios";


class TestComponent extends Component {

      constructor(props) {

           super(props)
           this.state = {
             data : '',
           }
         }

          componentDidMount() {
            this._getListData()

          }

          _getListData = async function() {
            const data = await axios('/tagWeb/test', {
              method : 'GET',
              headers: {
                         "Content-Type": `application/json`,
                                                             }
            })


            this.setState({ data : data })



          }



    render() {
        return (
            <>
                <h1>Test Page</h1>
                <div className="container">
                    {this.state.data}
                </div>
            </>
        )
    }
}

export default TestComponent