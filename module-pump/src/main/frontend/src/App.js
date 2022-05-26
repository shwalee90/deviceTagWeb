import logo from './logo.svg';
import './App.css';
import LoginApp from './component/LoginApp'
import React, {Component} from 'react';


class App extends Component {
  render() {
    return (
      <div className="App">
         <LoginApp/>
      </div>
    );
  }
}
export default App;