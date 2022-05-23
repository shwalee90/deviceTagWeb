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
                    currentTime : '',
                    realTimeVal : '',
                    rstMsg : '',
                  }
         }


             componentDidMount() {
                this._getListData();
                this._getMemoryList();

                this.launchInterval();


              }


             componentDidUpdate(prevProps){
                 if(prevProps.equipid !== this.props.equipid){
                      this._getListData();
                      this._getMemoryList();

                     }

                   }



             launchInterval() {
                 this.interval = setInterval(() => {

                      console.log("check data");
                      console.log(this.state.data.data);

                     this._getRtValue();
                     this.setState({
                     currentTime: new Date().toLocaleString()
                   });
                 }, 10000);
               }


             componentWillUnmount(){
                    console.log("###################################unmount")
                    clearInterval(this.interval);
                }



             _getRtValue() {
                                axios.post(`/auth/realTimeTagValue/${this.props.equipid}`
                                , JSON.stringify(this.state.data.data),{
                                 headers: {
                                                   "Content-Type": `application/json`,
                                                   "token" : localStorage.getItem('token'),
                                                   }})
                                 .then(response => {
                                               console.log(response);
                                               this.setState({ realTimeVal : response })
                                         })
                                 .catch(response =>{
                                 })


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
           let realTimeVal = this.state.realTimeVal.data;

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
                            <th>VALUE</th>
                        </tr>
                    </thead>
                    <tbody>
                        { list  ? list.map( (el) => {
                                                      return(
                                                        <tr  key={el.tagname}>
                                                          <td> {el.tagname} </td>
                                                          <td> {el.description} </td>
                                                          <td> {el.datatype} </td>
                                                          <td> {el.tagaccess} </td>
                                                          <td> {el.displayaddress} </td>
                                                          { realTimeVal ?  (<td> {this.state.realTimeVal} </td>)
                                                          : (<td> 0 </td>)  }
                                                        </tr>
                                                      )
                                                    })
                                                      : <li> {this.props.equipid} 장비에 테그가 없습니다.</li>}
                    </tbody>
                </table>
            </>
        )
    }


}

export default TagInfoComponent