import React, {Component} from 'react'
import {Link , useParams, useNavigate} from 'react-router-dom'
import AuthenticationService from '../AuthenticationService.js'
import '../../css/Modal.css';
import axios from "axios";

class Modal extends Component {


        constructor(props) {
            super(props)

            this.state = {
                username: '',
                password: '',
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
                 let data = {username : this.state.username,
                             password : this.state.password};
                    axios.post("/members/new" , JSON.stringify(data),{
                     headers: {
                                       "Content-Type": `application/json`,
                                       }})
               .then(response => {
                           this.props.history.push(`/welcome`)
                          }).catch( () =>{
                     this.setState({showSuccessMessage:false})
                     this.setState({hasLoginFailed:true})
                 })
         }

        render() {
          // 열기, 닫기, 모달 헤더 텍스트를 부모로부터 받아옴
          const { open, close, header } = this.props;




          return (
            <div className={open ? 'openModal modal' : 'modal'}>
              {open ? (
                <section>
                  <header>
                    {header}
                  </header>
                  <main>
                     <div className="modalForm">
                         User Name: <input type="text" name="username" value={this.state.username} onChange={this.handleChange}/>
                         Password: <input type="password" name="password" value={this.state.password}  onChange={this.handleChange}/>
                         <button className="btn btn-success" onClick={this.newMemberClicked}>Add</button>
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

export default Modal