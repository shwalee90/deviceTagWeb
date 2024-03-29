import React, {Component} from 'react'
import '../../css/Modal.css';
import axios from "axios";
import uuid from "react-uuid";

class TagModifyModal extends Component {


        constructor(props) {
            super(props)

            this.state = {
                writeValue : '',
                rst_msg :[],
                errCheck : '',
            }

            this.handleChange = this.handleChange.bind(this)
            this.addWriteClicked = this.addWriteClicked.bind(this)
            this.deleteTag = this.deleteTag.bind(this)

        }

         handleChange(event) {
             this.setState(
                 {
                     [event.target.name]
                       :event.target.value
                 }
             )
         }

         addWriteClicked() {
                 let data = {
                             writeValue : this.state.writeValue,
                             address : this.props.displayaddress,
                             equipid : this.props.equipid,
                             };
                    axios.post("/auth/writeTagValue" , JSON.stringify(data),{
                     headers: {
                                       "Content-Type": `application/json`,
                                       "token" : localStorage.getItem('token'),
                                       }})
                     .then(response => {
                                  console.log(response)
                                  this.setState({ rst_msg : response.data.result ,
                                                  errCheck : response.data.result[0].code ,
                                                  writeValue : '' ,
                                   })
                             })
                     .catch(function(error){
                        if(error.response){
                            console.log(error.response);
                            this.setState({ rst_msg : error.response.data.result })
                        }

                     });

         }

          deleteTag() {
                           let data = {
                                       tagid : this.props.tagid,

                                       };

                           axios.post("/auth/deleteTag" , JSON.stringify(data),{
                           headers: {
                                              "Content-Type": `application/json`,
                                              "token" : localStorage.getItem('token'),
                                              }})
                           .then(this.props.close)
                           .catch(err => console.log(err))
                           .then(() => {
                                    // 항상 실행
                                    this.props.refreshTag(this.props.equipid , this.props.postPerPage);
                                })
                   }


        render() {
          // 열기, 닫기, 모달 헤더 텍스트를 부모로부터 받아옴
          const { open, close, header ,equipid , tagid , tagaccess } = this.props;

          let rst_msg = this.state.rst_msg;


          return (
            <div className={open ? 'openModal modal' : 'modal'}>
              {open ? (
                <section>
                  <header>
                    {header}
                  </header>
                  <main>
                     <div className="modalForm">

                        {tagaccess  != "READ" ?
                            <>
                             <li> WriteValue : <input type="text" name = "writeValue" value={this.state.writeValue} onChange={this.handleChange}/></li>
                             <button className="btn btn-success" onClick={this.addWriteClicked}>WRITE</button>
                            </>
                         : null}
                         {this.state.errCheck === "WRITE SUCCESS" ?
                          rst_msg.map((el) => {
                          return (<li class="rstMsg_success">{el.code}</li>) }) :
                          rst_msg.map((el) => {
                          return (<li class="rstMsg" key={uuid()}>에러필드 : {el.field} 에러코드 :{el.code}</li>) }) }
                        <li> TAG_ID : {this.props.tagid}</li>
                        <li> EQUIP_ID : {this.props.equipid} </li>
                        <li> TAG_ACCESS : {this.props.tagaccess} </li>
                        <li> DISPLAY_ADDRESS : {this.props.displayaddress} </li>
                        <button className="btn btn-success" onClick={this.deleteTag}>DELETE</button>
                     </div>
                  </main>

                  <footer>
                    <button className="close" onClick={close}>
                      close
                    </button>
                  </footer>
                </section>
              ) : null}
            </div>
          );
        }
}


export default TagModifyModal