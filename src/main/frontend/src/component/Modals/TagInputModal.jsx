import React, {Component} from 'react'
import '../../css/Modal.css';
import axios from "axios";

class TagInputModal extends Component {


        constructor(props) {
            super(props)

            this.state = {
                tagName: '',
                address: '' ,
                description: '',
                dataType : '' ,
                access : '' ,
                rstMsg : '' ,
                memoryName : '',
                memory_data : [],
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
                                 this.setState({rstMsg : response})
                             })

         }

        render() {
          // 열기, 닫기, 모달 헤더 텍스트를 부모로부터 받아옴
          const { open, close, header } = this.props;

          let memoryList = this.props.memory_data.data;
          let rstMsg = this.state.rstMsg;
          console.log(memoryList);
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
                                                                        <select name="memoryName"  onChange={this.handleChange}>
                                                                        {memoryList ? memoryList.map((el) => {
                                                                          return ( <option value={el.memory_device_name}>{el.memory_device_name}</option> )
                                                                         })
                                                                         : null }
                                                                        </select> </li>

                        <li> ADDRESS: <input type="text" name="address" value={this.state.address}  onChange={this.handleChange}/></li>
                        <li> DESCRIPTION: <input type="text" name = "description" value={this.state.description} onChange={this.handleChange}/></li>
                        <li> DATA TYPE: <select name = "dataType" onChange={this.handleChange}>
                                            <option value="Boolean">Boolean</option>
                                            <option value="Short">Short</option>
                                            <option value="Word">Word</option>
                                            <option value="BCD16">BCD16</option>
                                            <option value="Integer">Integer</option>
                                            <option value="DWord">DWord</option>
                                            <option value="BCD32">BCD32</option>
                                            <option value="Float">Float</option>
                                            <option value="Double">Double</option>
                                            <option value="String">String</option>
                                        </select>            </li>
                        <li> ACCESS :  <select name="access"  onChange={this.handleChange}>
                                            <option value="READ">READ</option>
                                            <option value="READ/WRITE">READ/WRITE</option>
                                        </select></li>
                         <button className="btn btn-success" onClick={this.addTagClicked}>Add</button>
                     </div>
                        {
                            rstMsg ?(
                                <div>
                                   {rstMsg}
                                </div>
                            ) : null
                        }
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