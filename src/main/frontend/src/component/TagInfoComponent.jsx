import React, {Component} from 'react'
import TagInputModal from './Modals/TagInputModal';
import axios from "axios";
class TagInfoComponent extends Component {

        constructor(props) {

           super(props)
           this.state = {
                    equipid : 0,
                    data :[],
                    modalOpen :false,
                    memory_data : [],
                  }
         }


             componentDidMount() {
                this._getListData();
                this._getMemoryList();

              }


             componentDidUpdate(prevProps){
                 if(prevProps.equipid !== this.props.equipid){
                      this._getListData();
                      this._getMemoryList();
                     }
                   }


             _getMemoryList = async function() {
              const memoryList = await axios(`/auth/memoryList/${this.props.equipid}`, {
                method : 'GET',
                headers: {
                           "Content-Type": `application/json`,
                           "token" : localStorage.getItem('token')
                                                               }
              })

                    console.log(memoryList);
                    this.setState({ memory_data : memoryList })
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

           let list = this.state.data.data;

        return (
            <>
                <h1>TAG</h1>
                    <button onClick={ this.openModal }> 모달팝업</button>
                    <TagInputModal open={ this.state.modalOpen } close={ this.closeModal }
                     memory_data = {this.state.memory_data} id= {this.props.equipid} title="insert Tag">
                     </TagInputModal>
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