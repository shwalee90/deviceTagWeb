import React, {Component} from 'react'
import TagInputModal from './Modals/TagInputModal';
import TagWriteModal from './Modals/TagWriteModal';
import axios from "axios";
import Paging from './Pagination/Paging';
import '../css/Pagination.css';
class TagInfoComponent extends Component {

        constructor(props) {

           super(props)
           this.state = {
                    equipid : 0,
                    data :[],
                    modalOpen :false,
                    memory_data : [],
                    currentTime : '',
                    realTimeVal : [],
                    rstMsg : '',
                    currentPage : 1,
                    totalPosts : '',
                    postPerPage : 10,
                  }
           this.handleClick = this.handleClick.bind(this);
         }

         handleClick(pageNum) {

             this.handlePageChange(pageNum);

           }

         componentDidMount() {
            this._getTotalTag();
            this.handlePageChange(this.state.currentPage);
            this._getMemoryList();
            this.launchInterval();
          }

          componentDidUpdate(prevProps){
              if(prevProps.equipid !== this.props.equipid){
                   this._getTotalTag();
                   this.handlePageChange();
                   this._getMemoryList();

                  }
           }

          _getTotalTag(){
              axios.get(`/auth/tagCount/${this.props.equipid}`, {
                                               headers: {
                                                                 "Content-Type": `application/json`,
                                                                 "token" : localStorage.getItem('token'),
                                                                 }})
                                               .then(response => {
                                                             console.log(response);
                                                             this.setState({ totalPosts : response.data.tagCount })
                                                       })
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
                                    this.setState({ data : response })
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
             const data_list = await axios(`/auth/tagInfo/${this.props.equipid}`,
             {
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

            handlePageChange = async function(pageNum) {
              const pageList = await axios(`/auth/tagInfo/${this.props.equipid}?page=${pageNum-1}&size=${this.state.postPerPage}`,
              {
                method : 'GET',
                headers: {
                           "Content-Type": `application/json`,
                           "token" : localStorage.getItem('token')
                                                               }
              })

                 console.log(pageList);
                 this.setState({ data : pageList,
                                currentPage : pageNum ,
                  })


              }

    render() {

           let list = this.state.data.data;
           const currentPage = this.state.currentPage;
           const pageSize = this.state.pageSize;


        return (
            <>
                <h1>TAG</h1>
                    <button onClick={ this.openModal }> INSERT TAG</button>
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
                            <th>LOAD TIME</th>
                            <th>WRITE</th>
                        </tr>
                    </thead>
                    <tbody>
                        { list ? list.map( (el) => {
                                                      return(
                                                        <tr key={el.tagname}>
                                                          <td> {el.tagname} </td>
                                                          <td> {el.description} </td>
                                                          <td> {el.datatype} </td>
                                                          <td> {el.tagaccess} </td>
                                                          <td> {el.displayaddress} </td>
                                                          <td> {el.rtValue ? el.rtValue : 0}</td>
                                                          <td> {el.time ? el.time : null} </td>

                                                          {el.tagaccess === "READ/WRITE" ?
                                                          <td><button onClick={ this.openModal }> WRITE </button>
                                                                              <TagWriteModal open={ this.state.modalOpen } close={ this.closeModal }
                                                                               displayaddress = {el.displayaddress} id= {this.props.equipid} title="write Tag">
                                                                               </TagWriteModal>
                                                          </td> : null
                                                          }

                                                        </tr>
                                                      )
                                                    })
                                                      : <li> {this.props.equipid} 장비에 테그가 없습니다.</li>}
                    </tbody>
                </table>

                <Paging totalCount = {this.state.totalPosts} page={this.state.currentPage}
                 postPerPage={this.state.postPerPage} pageRangeDisplayed={5}
                 handlePageChange={this.handleClick}/>

            </>
        );
    }

}

export default TagInfoComponent