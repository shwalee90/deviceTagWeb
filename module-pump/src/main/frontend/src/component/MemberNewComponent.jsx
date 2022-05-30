import React, {Component} from 'react'
import AuthenticationService from './AuthenticationService.js'
import axios from "axios";


class MemberNewComponent extends Component {

    constructor(props) {
        super(props)

        console.log('cons');


        this.state = {
            username: '',
            password: '',
            hasLoginFailed: false,
            showSuccessMessage: false
        }

        this.handleChange = this.handleChange.bind(this)
        this.newMemberClicked = this.newMemberClicked.bind(this)



    }

    handleChange(event) {
        this.setState(
            {
                [event.target.name]
                  :event.target.value
            }
        )
    }



    newMemberClicked() {
            let data = {username : this.state.username,
                        password : this.state.password};

             axios.post("/tagWeb/members/new" , JSON.stringify(data),{
                headers: {
                                  "Content-Type": `application/json`,
                                }})
            .then(response => {
                      this.props.history.push(`/tagWeb/welcome`)
                     }).catch( () =>{
                this.setState({showSuccessMessage:false})
                this.setState({hasLoginFailed:true})
            })
        }




    render() {
        return (
            <div>
                <h1>New Member</h1>
                <div className="joinForm">
                    User Name: <input type="text" name="username" value={this.state.username} onChange={this.handleChange}/>
                    Password: <input type="password" name="password" value={this.state.password}  onChange={this.handleChange}/>
                    <button className="btn btn-success" onClick={this.newMemberClicked}>Join</button>
                </div>
            </div>
        )
    }
}

export default MemberNewComponent