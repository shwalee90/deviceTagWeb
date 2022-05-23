import React, {Component} from 'react'
import '../../css/Modal.css';
import axios from "axios";
import uuid from "react-uuid";

class TagInputModal extends Component {


        constructor(props) {
            super(props)

            this.state = {
                tagName: '',
                address: '' ,
                description: '',
                dataType : 'Boolean' ,
                access : 'READ' ,
                memoryName : 'C',
                memory_data : [],
                rst_msg :[],
                errCheck : '',
            }

            this.handleChange = this.handleChange.bind(this)
            this.addTagClicked = this.addTagClicked.bind(this)

        }





         handleChange(event) {
             this.setState(
                 {
                     [event.target.name]
                       :event.target.value
                 }
             )
         }


         addTagClicked() {
                 let data = {
                             tagName : this.state.tagName,
                             address : this.state.address,
                             description :  this.state.description,
                             dataType :  this.state.dataType,
                             access :  this.state.access,
                             memoryName : this.state.memoryName,
                             id : this.props.id,

                             };
                    axios.post("/auth/insertTag" , JSON.stringify(data),{
                     headers: {
                                       "Content-Type": `application/json`,
                                       "token" : localStorage.getItem('token'),
                                       }})
                     .then(response => {
                                 console.log(response)
                                  this.setState({ rst_msg : response.data.result ,
                                                  errCheck : response.data.result[0].code ,
                                   })
                             })
                     .catch(function(error){
                        if(error.response){
                            console.log(error.response);
                              this.setState({ rst_msg : error.response.data.result })
                        }

                     });

         }

        render() {
          // 열기, 닫기, 모달 헤더 텍스트를 부모로부터 받아옴
          const { open, close, header } = this.props;

          let memoryList = this.props.memory_data.data;

          let rst_msg = this.state.rst_msg;

           console.log("@@@@@@@@@@@@"+this.state.errCheck);

          return (
            <div className={open ? 'openModal modal' : 'modal'}>
              {open ? (
                <section>
                  <header>
                    {header}
                  </header>
                  <main>
                     <div className="modalForm">
                        <li> TAG Name: <input type="text" name = "tagName" value={this.state.tagName} onChange={this.handleChange}/></li>
                        <li> MEMORY TYPE :
                                          <select key={uuid()} name="memoryName" value={this.state.memoryName} onChange={this.handleChange}>
                                          {memoryList ? memoryList.map((el) => {
                                            return ( <option value={el.memoryDeviceName} >{el.memoryDeviceName}</option> )
                                           })
                                           : null }
                                          </select> </li>

                        <li> ADDRESS: <input type="text" name="address" value={this.state.address}  onChange={this.handleChange}/></li>
                        <li> DESCRIPTION: <input type="text" name = "description" value={this.state.description} onChange={this.handleChange}/></li>
                        <li> DATA TYPE: <select name = "dataType"  key={uuid()}  value={this.state.dataType} onChange={this.handleChange}>
                                            <option value="Boolean" >Boolean</option>
                                            <option value="Short" >Short</option>
                                            <option value="Word" >Word</option>
                                            <option value="BCD16" >BCD16</option>
                                            <option value="Integer" >Integer</option>
                                            <option value="DWord" >DWord</option>
                                            <option value="BCD32" >BCD32</option>
                                            <option value="Float" >Float</option>
                                            <option value="Double" >Double</option>
                                            <option value="String">String</option>
                                        </select>            </li>
                        <li> ACCESS :  <select name="access" key={uuid()}  value={this.state.access}  onChange={this.handleChange}>
                                            <option value="READ">READ</option>
                                            <option value="READ/WRITE">READ/WRITE</option>
                                        </select></li>
                         <button className="btn btn-success" onClick={this.addTagClicked}>ADD</button>

                         {this.state.errCheck == "INSERT SUCCESS" ?
                          rst_msg.map((el) => {
                          return (<li class="rstMsg_success">{el.code}</li>) }) :
                          rst_msg.map((el) => {
                          return (<li class="rstMsg" key={uuid()}>에러필드 : {el.field} 에러코드 :{el.code}</li>) }) }

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




export default TagInputModal