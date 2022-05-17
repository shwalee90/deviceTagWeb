import React, {Component} from 'react'
import {Link , withRouter  } from 'react-router-dom'
import AuthenticationService from './AuthenticationService.js'
import Modal from './Modals/Modal';
import axios from "axios";
class TagInfoComponent extends Component {

        constructor(props) {

           super(props)
           this.state = {
                    equipid : 0,
                    data :[],
                    modalOpen :false,
                  }
         }


             componentDidMount() {
                this._getListData()
              }


             componentDidUpdate(prevProps){
                 if(prevProps.equipid !== this.props.equipid){
                      this._getListData();

                     }
                   }



              _getListData = async function() {
                const data_list = await axios(`/auth/tagInfo/${this.props.equipid}`, {
                  method : 'GET',
                  headers: {
                             "Content-Type": `application/json`,
                             "token" : localStorage.getItem('token')
                                                                 }
                })


                this.setState({ data : data_list })
                console.log(data_list);
              }

            openModal = () => {
                this.setState({ modalOpen: true })
            }
            closeModal = () => {
                this.setState({ modalOpen: false })
            }


    render() {

           let id =  this.props.equipid;
           let list = this.state.data.data;

        return (
            <>
                <h1>TAG</h1>
                    <button onClick={ this.openModal }> 모달팝업</button>
                    <Modal open={ this.state.modalOpen } close={ this.closeModal } title="insert Tag">
                                    </Modal>
                <table>
                    <thead>
                        <tr>
                            <th>TAG NAME</th>
                            <th>DESCRIPTION</th>
                            <th>DATA TYPE</th>
                            <th>I/O TYPE</th>
                            <th>DISPLAYADDRESS</th>
                        </tr>
                    </thead>
                    <tbody>
                        {list ? list.map( (el) => {
                                                      return(
                                                        <tr  key={el.tagname}>
                                                          <td> {el.tagname} </td>
                                                          <td> {el.description} </td>
                                                          <td> {el.datatype} </td>
                                                          <td> {el.tagaccess} </td>
                                                          <td> {el.displayaddress} </td>
                                                        </tr>
                                                      )
                                                    })
                                                      : null}
                    </tbody>
                </table>
            </>
        )
    }


}

export default TagInfoComponent