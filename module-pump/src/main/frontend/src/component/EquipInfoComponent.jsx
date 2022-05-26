import React, {Component} from 'react'
import {Link} from 'react-router-dom'
import AuthenticationService from './AuthenticationService.js'
import axios from "axios";


class EquipInfoComponent extends Component {


    constructor(props) {

       super(props)
       this.state = {
         data : [],
       }
     }

      componentDidMount() {
        this._getListData()

      }

      _getListData = async function() {
        const data_list = await axios('/auth/equip', {
          method : 'GET',
          headers: {
                     "Content-Type": `application/json`,
                     "token" : localStorage.getItem('token')
                                                         }
        })


        this.setState({ data : data_list })



      }




    render() {

        const { data } = this.props;

        let list = this.state.data.data;


       //list  이 없는 경우도 있다.
       //if(list == null){
       //     li_list = null;
       // }else{
       //     li_list = list.map((el,key) => (<li> <Link to='/auth/pump/{el.equip_id}'>{el.equip_alias} </Link></li>));
       // }



        return (
            <>
                <h1>Equip</h1>
                <div>
                   {list ? list.map( (el) => {
                              return(
                                <ul  key={el.equip_id}>
                                  <li> <Link to={`/auth/pump/${el.equipid}`}> {el.equip_alias} </Link></li>
                                </ul>
                              )
                            })
                              : null}
                </div>
            </>
        )
    }








}


export default EquipInfoComponent